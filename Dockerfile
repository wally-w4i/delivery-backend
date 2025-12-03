# Stage 1: Build the application
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean install -DskipTests

# Stage 2: Create the production image
FROM openjdk:17.0.1-jdk-slim
WORKDIR /app
COPY --from=build /app/target/delivery-0.0.1-SNAPSHOT.jar .
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "delivery-0.0.1-SNAPSHOT.jar"]
