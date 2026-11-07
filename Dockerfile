
# Stage 1: Build the application
FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /app

# Copy Maven configuration
COPY pom.xml .

# Download dependencies
RUN mvn dependency:go-offline

# Copy source code
COPY src ./src

# Build executable JAR
RUN mvn clean package -DskipTests


# Stage 2: Run the application
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copy executable JAR from build stage
COPY --from=build /app/target/*.jar app.jar

# Application port
EXPOSE 8080

# Start Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
