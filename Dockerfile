FROM openjdk:17

EXPOSE 8080

RUN mkdir /security-jwt

COPY target/Spring-Security-JWT-0.0.1-SNAPSHOT.jar /security-jwt

ENTRYPOINT ["java","-jar","/security-jwt"]