FROM openjdk:17.0.2-jdk-slim-buster
ARG JAR_FILE=vacation-pay-calculator/target/vacation-pay-calculator-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]