# Use an OpenJDK base image with JDK 17
FROM openjdk:17-jdk-slim as build

# Set the working directory in the container
WORKDIR /app

# Copy the pre-built jar file into the container
COPY /build/libs/shop-0.0.1-SNAPSHOT.jar shop.jar

# Expose the port the application runs on
EXPOSE 8080

# Command to run the application
CMD ["java", "-jar", "shop.jar"]
