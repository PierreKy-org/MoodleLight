FROM openjdk:17


VOLUME /tmp

ARG JAR_FILE=target/security-1.0-SNAPSHOT.jar

ADD ${JAR_FILE} security.jar

ENTRYPOINT ["java", "-jar", "/security.jar"]
