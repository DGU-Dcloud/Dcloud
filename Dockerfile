# Use an official Gradle image to build the application
FROM gradle:7.3.3-jdk17 AS build

# Set the working directory to /home/gradle/project
WORKDIR /docker_projects/jenkins_1/var/jenkins_home/workspace/originDcloud/Dcloud

# Copy the Gradle project files
COPY . .

# Build the Spring Boot application
RUN ./gradlew build

# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-alpine

# Set the working directory to /app
WORKDIR /app

# Copy the JAR file from the build image
COPY --from=build /docker_projects/jenkins_1/var/jenkins_home/workspace/originDcloud/Dcloud/build/libs/*.jar app.jar

# Expose port 8080
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
