FROM openjdk:17


VOLUME /tmp


ARG JAR_FILE=target/spring-security-jwt-0.0.1-SNAPSHOT.jar

ADD ${JAR_FILE} spring-jwt.jar

ENTRYPOINT ["java", "-jar", "/spring-jwt.jar"]
