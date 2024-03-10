# iMedia24 Coding challenge
# Overview
This is a Kotlin spring boot application that exposes an API for handling Products.
# Prerequisites
Docker installed on your system
# Getting Started
1. Clone this repository to your local machine
2. Navigate to root directory
3. Build the Docker image using the following command:
> docker build -t imedia24-coding-challenge .
4. Run the Docker container using the following command:
> docker run -p 8080:8080 imedia24-coding-challenge
* The application should now be accessible at http://localhost:8080
# additional information
You can consult the API endpoints as json using this [link](http://localhost:8080/v2/api-docs) or as UI using this [link](http://localhost:8080/swagger-ui/) 

### Reference Documentation
For further reference, please consider the following sections:

* [Official Gradle documentation](https://docs.gradle.org)
* [Official Kotlin documentation](https://kotlinlang.org/docs/home.html)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.4.3/gradle-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.4.3/gradle-plugin/reference/html/#build-image)
* [Flyway database migration tool](https://flywaydb.org/documentation/)

### Additional Links
These additional references should also help you:

* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)

