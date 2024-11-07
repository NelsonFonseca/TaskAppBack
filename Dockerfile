FROM openjdk:17-jdk-slim

WORKDIR /app

EXPOSE 9005
COPY target/task-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]
