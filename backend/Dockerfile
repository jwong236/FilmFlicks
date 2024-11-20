# Step 1: Build the Spring Boot Application
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Step 2: Run the Application
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/FilmFlicks.jar /app/your-backend.jar
COPY src/main/resources/application-secret.properties /app/config/application-secret.properties
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/your-backend.jar"]
