# Build stage
FROM maven:3.8.6-openjdk-8 AS build
WORKDIR /app

# Copy only the pom.xml and dependencies first to leverage Docker cache
COPY pom.xml .
COPY src ./src

# Run Maven to build the application
RUN mvn clean package -DskipTests

# Package stage
FROM openjdk:8
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/target/watchshop-api.jar /app/watchshop-api.jar

# Expose the port that the application will run on
EXPOSE 8084

# Define the command to run the application
ENTRYPOINT ["java", "-jar", "/app/watchshop-api.jar"]
