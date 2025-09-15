# **Movie Recommendation System**

## **Overview**
This project implements a **movie recommendation system** using **The Movies Dataset** from Kaggle. The system suggests movies based on **content similarity** and **popularity-based filtering**.

The system is built with **Python**

---

## **Features**
- **TF-IDF Matrix**: Used to analyze movie similarity based on **plot descriptions** and **director/actor features**.
- **Content-Based Filtering**: Recommends movies based on **text similarity**.
- **Popularity-Based Filtering**: Suggests movies based on **view count and rating**.
- **Precomputed Models**: Saves and loads **TF-IDF matrices** for faster execution.
- **Optimized Data Processing**: Uses **preprocessed movie metadata** to improve performance.
- **Retraining Option**: Allows users to **update the model** when new data is available.

---

## **Technologies & Libraries Used**
### **Programming Language**
- **Python 3.8+** - Main language for data processing and machine learning.

### **Data Processing**
- **pandas** - Data manipulation and cleaning.
- **numpy** - Numerical computations.

### **Machine Learning & Recommendation**
- **scikit-learn** - TF-IDF vectorization and similarity calculation.

### **File Handling & Model Storage**
- **joblib** - Saves and loads precomputed TF-IDF matrices.
- **os** - Manages file paths and directory creation.

---

## **Installation & Setup**
### **1 - Create a virtual environment:**
```bash
python3 -m venv venv
source venv/bin/activate
```

### **2 - Install Dependencies**
```bash
pip install -r requirements.txt
```

### **3 - Download Dataset Manually**
Since Kaggle requires authentication, follow these steps:

1. **Go to the Kaggle dataset page**:  
   [The Movies Dataset](https://www.kaggle.com/datasets/rounakbanik/the-movies-dataset)

2. **Click on "Download"** and save the `.zip` file to your computer.

3. **Extract the contents** into the `data/` folder in this project.

---

## **How TF-IDF Works**
The **TF-IDF (Term Frequency - Inverse Document Frequency) matrix** is used to convert text data into numerical features for **content-based recommendations**.

### **What is TF-IDF?**
TF-IDF measures **the importance of words** in a document relative to all other documents in the dataset.

### **1. Term Frequency (TF)**
TF represents how frequently a word appears in a document.  
The formula is:

```math
TF(t) = \frac{\text{Number of times term } t \text{ appears in a document}}{\text{Total number of terms in the document}}
```

### **2. Inverse Document Frequency (IDF)**
IDF evaluates the importance of a word by decreasing the weight of frequently used words.

```math
IDF(t) = \log \left(\frac{\text{Total number of documents}}{\text{Number of documents containing term } t}\right)
```

### **3. Final TF-IDF Score**
The final TF-IDF value is calculated by multiplying **TF** and **IDF**:

```math
\text{TF-IDF}(t) = TF(t) \times IDF(t)
```

### **Why is TF-IDF Useful in Movie Recommendations?**
- **Movie Descriptions** → Helps find movies with similar **plot summaries**.
- **Directors & Actors** → Recommends movies with similar **people involved**.
- **Better than Simple Word Counts** → Reduces noise from **frequent but unimportant words**.

---

## **Usage**
### **Run the recommendation system**
```bash
cd src
python main.py
```