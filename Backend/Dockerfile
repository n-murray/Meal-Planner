FROM maven:3.8-jdk-8 AS Builder
COPY . /app
WORKDIR /app
RUN mvn clean install -DskipTests

FROM openjdk:8-jdk-alpine
COPY --from=Builder /app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
