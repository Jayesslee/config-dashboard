FROM openjdk:21-jdk-slim

# Copy jar to image.
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]

EXPOSE 8082