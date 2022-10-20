FROM amazoncorretto:11-alpine-jdk
MAINTAINER kaanalkim
COPY build/libs/weather-api-*-SNAPSHOT.jar weather-api.jar
ENTRYPOINT ["java","-jar","/weather-api.jar"]