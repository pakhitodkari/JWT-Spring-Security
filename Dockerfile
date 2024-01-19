FROM openjdk:17

COPY target/Spring-Security-JWT-0.0.1-SNAPSHOT.jar security-jwt

ENTRYPOINT ["java","-jar","/security-jwt"]