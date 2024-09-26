FROM openjdk:17-jdk-alpine

MAINTAINER "ronnychamba96.gmail.com"

WORKDIR /app

COPY target/*.jar /app/ms-app.jar

# Comando para ejecutar el archivo JAR
ENTRYPOINT ["java", "-jar", "/app/ms-app.jar"]
