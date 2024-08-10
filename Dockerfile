# Step 1: Build Stage
FROM gradle:8.2-jdk17 AS build

# Set the working directory
WORKDIR /app

# Copy the Gradle wrapper and build files
COPY gradle /app/gradle
COPY gradlew /app/
COPY build.gradle /app/
COPY settings.gradle /app/
COPY src /app/src

# Set permissions for gradlew
RUN chmod +x gradlew

# Download dependencies and build the project
RUN ./gradlew build -x test --no-daemon

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
