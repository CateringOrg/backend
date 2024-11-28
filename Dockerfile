FROM openjdk:21-jdk-slim as builder

WORKDIR /app

COPY build.gradle settings.gradle ./
COPY gradle/wrapper gradle/wrapper
COPY src ./src
COPY gradlew gradlew

RUN chmod +x gradlew

RUN ./gradlew build --no-daemon -x test

FROM openjdk:21-jdk-slim

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
