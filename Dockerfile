# 1. Use OpenJDK 17 as base image
FROM openjdk:17-jdk-slim

# 2. Set working directory inside container
WORKDIR /app

# 3. Copy JAR file from "target" folder to container
COPY target/*.jar app.jar

# 4. Expose port 8080 (same as Spring Boot default port)
EXPOSE 8080

# 5. Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
