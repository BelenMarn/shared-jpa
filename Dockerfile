FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY target/SimpleShared-0.0.1-SNAPSHOT.jar app.jar
COPY wait-for-it.sh .

RUN chmod +x wait-for-it.sh

CMD ["java", "-jar", "app.jar"]