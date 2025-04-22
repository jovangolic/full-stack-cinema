FROM openjdk:17-jdk-buster

WORKDIR /app

COPY target/backend-c2-0.0.1-SNAPSHOT.jar /app/backend-c2.jar

ENTRYPOINT ["java", "-jar", "/app/backend-c2.jar"]