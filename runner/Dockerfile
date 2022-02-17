FROM openjdk:17

VOLUME /tmp

ARG JAR_FILE=target/runner-0.0.1-SNAPSHOT.jar

ADD ${JAR_FILE} spring-runner.jar

ENTRYPOINT ["java", "-jar", "/spring-runner.jar"]
