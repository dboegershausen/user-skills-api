FROM openjdk:17.0.2-jdk-slim

COPY ./target/user-skills-application.jar /app/

ENTRYPOINT exec java -jar /app/user-skills-application.jar

EXPOSE 8081