FROM openjdk:17

VOLUME /tmp

ARG JAR_FILE=target/runner-1.0-SNAPSHOT.jar

ADD ${JAR_FILE} runner.jar

ENTRYPOINT ["java", "-jar", "/runner.jar"]
