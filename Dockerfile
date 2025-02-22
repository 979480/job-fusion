FROM openjdk:17-jdk-slim
WORKDIR /app

# Copy project files
COPY . .

# Maven build karein
RUN ./mvnw clean package -DskipTests

# JAR copy karein
COPY target/*.jar app.jar

# Expose port
EXPOSE 8080

# Run Spring Boot application
CMD ["java", "-jar", "app.jar"]
