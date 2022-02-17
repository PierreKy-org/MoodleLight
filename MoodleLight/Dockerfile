FROM openjdk:17


VOLUME /tmp

EXPOSE 8080

ARG JAR_FILE=target/spring-boot-security-postgresql-0.0.1-SNAPSHOT.jar

ADD ${JAR_FILE} spring-services.jar

ENTRYPOINT ["java", "-jar", "/spring-services.jar"]
