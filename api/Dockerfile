FROM openjdk:17

VOLUME /tmp

EXPOSE 8080

ARG JAR_FILE=target/api-1.0-SNAPSHOT.jar

ADD ${JAR_FILE} api.jar

ENTRYPOINT ["java", "-jar", "/api.jar"]
