FROM openjdk:8-jdk-alpine
MAINTAINER randulaD
COPY target/spring-boot-coding-test 0.0.1-SNAPSHOT.jar spring-boot-docker.jar/
ENTRYPOINT ["java","-jar","/spring-boot-docker.jar"]