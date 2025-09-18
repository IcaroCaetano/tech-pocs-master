# Stage 1: Build
FROM eclipse-temurin:23-jdk AS build



# Install Gradle
RUN apt-get update && apt-get install -y wget unzip && \
    # Updates the package index.
    wget https://services.gradle.org/distributions/gradle-8.5-bin.zip && \
    # Installs required system tools: wget and unzip.
    unzip gradle-8.5-bin.zip && \
    # Unzips Gradle into /opt/gradle.
    mv gradle-8.5 /opt/gradle && \
    # Removes the downloaded .zip to reduce image size.
    rm gradle-8.5-bin.zip && \
    # Cleans up apt cache to keep the image smaller.
    apt-get clean

#Sets the environment variable GRADLE_HOME to point to the Gradle installation.
ENV GRADLE_HOME=/opt/gradle
# Extends the PATH so the gradle command is available globally.
ENV PATH=$PATH:$GRADLE_HOME/bin

WORKDIR /app

# Copy all project files
COPY . .

# Build the application using installed Gradle
RUN gradle clean build -x test --no-daemon

# Stage 2: Runtime
FROM eclipse-temurin:23-jre AS runtime
# Sets the working directory inside the container to /app.
WORKDIR /opt/app

# Copy the built jar from the build stage
COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080
HEALTHCHECK --interval=10s --timeout=3s --retries=5 CMD curl -f http://localhost:8080/actuator/health || exit 1
ENTRYPOINT ["java", "-XX:+UseZGC", "-XX:MaxRAMPercentage=75", "-jar", "app.jar"]
