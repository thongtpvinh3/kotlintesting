FROM openjdk:8-jdk-alpine as build
WORKDIR /app
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
RUN ./mvnw dependency:go-offline -B
COPY src src
RUN ./mvnw package -DskipTests
FROM openjdk:8-jre-alpine
WORKDIR /app
ARG DEPENDENCY=/app/target/voi-1.0
COPY --from=build /app/target/voi-1.0.jar /app
ENTRYPOINT ["java","-jar","voi-1.0.jar"]
