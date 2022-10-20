FROM amazoncorretto:11-alpine-jdk
MAINTAINER kaanalkim
COPY build/libs/weather-api-0.0.1-SNAPSHOT.jar weather-api-0.0.1.jar
ENTRYPOINT ["java","-jar","/weather-api-0.0.1.jar"]