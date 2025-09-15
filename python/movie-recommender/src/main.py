import ast
import os
import warnings

import joblib
import pandas as pd
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import cosine_similarity

warnings.filterwarnings("ignore")

SAVE_DIRECTORY = "../saved_models"
PROCESSED_FILE = os.path.join(SAVE_DIRECTORY, "processed_movies.csv")

if not os.path.exists(SAVE_DIRECTORY):
    os.makedirs(SAVE_DIRECTORY)


#############################################
# Loading and Preparing Data #
#############################################

def load_movies_metadata():
    """Loads and cleans the movies_metadata.csv file."""
    movies = pd.read_csv('../data/movies_metadata.csv', low_memory=False)  # Load dataset
    movies = movies[pd.to_numeric(movies['id'], errors='coerce').notnull()]  # Remove rows with invalid IDs
    movies['id'] = movies['id'].astype(int)  # Convert ID column to integers
    movies['overview'] = movies['overview'].fillna('')  # Replace missing descriptions with an empty string
    movies.rename(columns={'title': 'movieName'}, inplace=True)  # Rename column for consistency
    return movies[['id', 'movieName', 'overview', 'vote_average']]  # Return only relevant columns


def load_links():
    """Loads links.csv to map MovieLens movieId to TMDB id."""
    links = pd.read_csv('../data/links.csv', usecols=['movieId', 'tmdbId'])  # Load only required columns
    links['tmdbId'] = pd.to_numeric(links['tmdbId'], errors='coerce').dropna().astype(int)  # Convert IDs to integers
    return links


def load_ratings():
    """Loads ratings.csv and maps MovieLens movieId to TMDB id."""
    ratings = pd.read_csv('../data/ratings.csv')  # Load ratings dataset
    links = load_links()  # Load movie ID mappings
    ratings = ratings.merge(links, on='movieId', how='left').dropna(subset=['tmdbId'])  # Merge datasets
    ratings['tmdbId'] = ratings['tmdbId'].astype(int)  # Convert IDs to integers
    ratings.rename(columns={'tmdbId': 'id'}, inplace=True)  # Rename column for consistency
    return ratings


def compute_movie_views(ratings):
    """Counts the number of times each movie was rated."""
    return ratings.groupby('id')['rating'].count().reset_index().rename(columns={'rating': 'views'})


############################################
# Processing Credits Information #
############################################

def load_credits():
    """Loads credits.csv containing cast and crew information."""
    credits = pd.read_csv('../data/credits.csv', usecols=['id', 'cast', 'crew'])  # Load only required columns
    credits['id'] = pd.to_numeric(credits['id'], errors='coerce').dropna().astype(int)  # Convert IDs to integers
    return credits


def extract_director(crew_str):
    """Extracts the director's name from the 'crew' JSON field."""
    try:
        crew = ast.literal_eval(crew_str)  # Convert string to a list of dictionaries
        for member in crew:  # Iterate through crew members
            if member.get('job') == 'Director':  # If job title is 'Director'
                return member.get('name', '')  # Return the director's name
    except:
        return ''  # Return empty string if parsing fails
    return ''


def extract_actors(cast_str):
    """Extracts the top three actors from the 'cast' JSON field and ensures they are separated by commas."""
    try:
        cast = ast.literal_eval(cast_str)  # Convert string to a list of dictionaries
        actors = [member.get('name', '') for member in cast[:3]]  # Extract names of top 3 actors
        return ", ".join(actors)  # Join names with commas
    except:
        return ''  # Return empty string if parsing fails


def process_movie_features(retrain=False):
    """
    Loads or processes movie data and saves the processed dataset.
    - If processed data exists, load it to avoid recomputation.
    - Otherwise, process and save it for future use.
    """
    if os.path.exists(PROCESSED_FILE) and not retrain:
        print(f"Loading preprocessed movie features from {PROCESSED_FILE}...")
        return pd.read_csv(PROCESSED_FILE)

    print("Processing movie features...")
    movies = load_movies_metadata()  # Load movie metadata
    credits = load_credits()  # Load movie credits
    movies = movies.merge(credits, on='id', how='left')  # Merge both datasets
    movies['director'] = movies['crew'].apply(extract_director)  # Extract director names
    movies['actors'] = movies['cast'].apply(extract_actors)  # Extract actor names
    movies['combined_features'] = movies[['director', 'actors']].fillna('').agg(' '.join,
                                                                                axis=1)  # Create a text feature

    # Save processed features to CSV for faster future loads
    movies.to_csv(PROCESSED_FILE, index=False)
    print(f"Processed movie features saved to {PROCESSED_FILE}.")

    return movies


def build_tfidf_matrix(movies, feature_column, model_name, retrain=False):
    """
    Builds or loads a precomputed TF-IDF matrix.

    **What is a TF-IDF matrix?**
    - It converts text into numerical values so that machine learning models can process it.
    - The matrix represents the importance of each word in the given dataset.
    - Words that appear frequently in many documents (e.g., "the", "is") are given **lower weights**.
    - Words that appear **rarely** but are important in a document are given **higher weights**.

    **Parameters:**
    - movies: Pandas DataFrame containing the movie dataset.
    - feature_column: Column to be converted into TF-IDF representation ('combined_features' or 'overview').
    - model_name: Name used to save the computed TF-IDF matrix.
    - retrain: If True, recalculates the TF-IDF matrix even if a saved version exists.

    **Returns:**
    - A TF-IDF matrix representing the given text feature.
    """

    filename = os.path.join(SAVE_DIRECTORY, f"{model_name}.pkl")  # Define save path for the TF-IDF model

    # If a precomputed TF-IDF matrix exists and retrain is False, load the saved model
    if os.path.exists(filename) and not retrain:
        print(f"Loading saved TF-IDF matrix from {filename}...")
        return joblib.load(filename)  # Load precomputed matrix

    print(f"Computing TF-IDF matrix for {feature_column}...")

    # Create a TF-IDF vectorizer that ignores common English words (stop words)
    tfidf = TfidfVectorizer(stop_words='english')

    # Convert the selected feature column (e.g., 'combined_features' or 'overview') into a TF-IDF matrix
    tfidf_matrix = tfidf.fit_transform(movies[feature_column].fillna(''))

    # Save the TF-IDF matrix to avoid recomputation in the future
    joblib.dump(tfidf_matrix, filename)

    print(f"TF-IDF matrix saved as {filename}.")

    return tfidf_matrix  # Return the computed TF-IDF matrix


##################################
# Recommendation Methods #
##################################

def get_recommendations(movie_title, movies, tfidf_matrix, top_n=10):
    """Returns the top_n most similar movies to the given movie title, including director and actors."""

    # Create an index lookup where movie titles are mapped to their corresponding dataframe indices.
    indices = pd.Series(movies.index, index=movies['movieName']).drop_duplicates()

    # Search for movie titles that match the input movie_title (case-insensitive).
    matches = indices.index.str.contains(movie_title, case=False, na=False)

    # If no matches are found, notify the user and return an empty list.
    if not matches.any():
        print(f"Movie '{movie_title}' not found. Try another title.")
        return []

    idx = indices[matches].iloc[0]  # Get the index of the first matched movie.

    # Compute similarity scores between the selected movie and all other movies using cosine similarity.
    cosine_sim = cosine_similarity(tfidf_matrix[idx], tfidf_matrix).flatten()

    # Get the top N most similar movies, excluding the input movie itself (hence, skipping index 0).
    sim_scores = sorted(enumerate(cosine_sim), key=lambda x: x[1], reverse=True)[1:top_n + 1]

    recs = []  # List to store recommended movies.
    for i, score in sim_scores:  # Iterate through the top matches.
        movie_data = movies.iloc[i]  # Retrieve movie details from the dataset.

        recs.append({
            'movieName': movie_data['movieName'],  # Store the movie title.
            'director': movie_data['director'],  # Store the movie director.
            'actors': str(movie_data['actors']),  # Ensure actors are a string (comma-separated)
            'imdbScore': float(movie_data['vote_average']),  # Store the movie's IMDb score.
        })

    return recs  # Return the list of recommended movies.


def get_popular_movies(movies, views_df, top_n=10):
    """Returns the top_n most popular movies based on views and rating."""

    movies = movies.merge(views_df, on='id', how='left')  # Merge movies data with views data using 'id' as key.
    movies['views'] = movies['views'].fillna(0)  # Replace missing values in 'views' with 0 to avoid NaN issues.
    popular_movies = movies.sort_values(by=['views', 'vote_average'], ascending=False).head(
        top_n)  # Sort movies by number of views (popularity) and then by rating (vote_average) in descending order.
    return popular_movies[
        ['movieName', 'views', 'vote_average']]  # Return only relevant columns: name, views, and rating.


###############
# Execution #
###############

def main():
    print("Initializing system...")
    movies = process_movie_features()
    views_df = compute_movie_views(load_ratings())

    # Automatically train if needed
    tfidf_matrix_credits = build_tfidf_matrix(movies, 'combined_features', 'tfidf_credits')
    tfidf_matrix_overview = build_tfidf_matrix(movies, 'overview', 'tfidf_overview')

    while True:
        print("\nChoose the recommendation type:")
        print("1 - Based on Directors and Actors")
        print("2 - Based on Movie Description")
        print("3 - Based on Popularity and Ratings")
        print("4 - Retrain Models")
        print("5 - Exit")
        choice = input("Enter the number of your choice: ").strip()

        if choice == "3":
            print("\nTop 10 Most Popular Movies:")
            popular_movies = get_popular_movies(movies, views_df)
            print(popular_movies.to_string(index=False))
            continue
        elif choice == "4":
            print("\nRetraining models...")
            movies = process_movie_features(retrain=True)
            views_df = compute_movie_views(load_ratings())
            tfidf_matrix_credits = build_tfidf_matrix(movies, 'combined_features', 'tfidf_credits', retrain=True)
            tfidf_matrix_overview = build_tfidf_matrix(movies, 'overview', 'tfidf_overview', retrain=True)
            continue
        elif choice == "5":
            print("Exiting the recommendation system. Goodbye!")
            break

        movie_title = input("\nEnter a movie title for recommendations: ").strip()
        tfidf_matrix = tfidf_matrix_credits if choice == "1" else tfidf_matrix_overview
        recs = get_recommendations(movie_title, movies, tfidf_matrix, top_n=10)

        if recs:
            print(f"\nRecommended movies for '{movie_title}':")
            for r in recs:
                print(r)


if __name__ == '__main__':
    main()
