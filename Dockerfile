FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/project-name.jar /app/tms-backend.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "tms-backend.jar"]