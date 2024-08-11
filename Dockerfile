# Step 1: Build Stage
FROM gradle:8.2-jdk17 AS build

# Set the working directory
WORKDIR /app

# Install dos2unix to handle potential Windows line endings
RUN apt-get update && apt-get install -y dos2unix

# Copy the Gradle wrapper and build files
COPY gradlew /app/gradlew
COPY gradle /app/gradle
COPY build.gradle /app/
COPY settings.gradle /app/

# Convert gradlew to Unix format and set permissions
RUN dos2unix /app/gradlew && chmod +x /app/gradlew

# Verify gradlew is present and executable
RUN ls -la /app

# Download dependencies (using --no-daemon to prevent issues in CI/CD environments)
RUN /app/gradlew --no-daemon dependencies

# Copy the source code
COPY src /app/src

# Build the project (excluding tests)
RUN /app/gradlew build -x test --no-daemon

# Step 2: Runtime Stage
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /app/build/libs/sl-0.0.1-SNAPSHOT.jar /app/sl.jar

# Expose the port that the application will run on
EXPOSE 8080

# Run the JAR file
ENTRYPOINT ["java", "-jar", "/app/sl.jar"]
