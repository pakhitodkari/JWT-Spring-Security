FROM openjdk:17

ADD target/Spring-Security-JWT-0.0.1-SNAPSHOT.jar security-jwt.jar

ENTRYPOINT ["java","-jar","/security-jwt.jar"]