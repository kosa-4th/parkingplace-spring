FROM amazoncorrectto:21-alpine-jdk
ARG JAR_FILE=target/*.jar
ARG PRFILES
ARG ENV
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=${PROFILES}", "-Dserver.env=${ENV}", "-jar", "app.jar"]
