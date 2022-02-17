FROM openjdk:17
VOLUME /tmp
ARG JAR_FILE=target/runner-1.0-SNAPSHOT.jar
ADD ${JAR_FILE} runner.jar
ADD ${JAR_FILE} python-service-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/runner.jar"]