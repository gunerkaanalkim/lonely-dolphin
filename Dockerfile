FROM amazoncorretto:11-alpine-jdk AS WEATHER_API_BUILD_IMAGE
ENV APP_HOME=/usr/app
WORKDIR $APP_HOME
COPY build.gradle settings.gradle gradlew $APP_HOME
COPY gradle $APP_HOME/gradle
RUN ./gradlew build || return 0
COPY ./src ./src
RUN ./gradlew build

FROM amazoncorretto:11-alpine-jdk
ENV ARTIFACT_NAME=/weather-api-*-SNAPSHOT.jar
ENV APP_HOME=/usr/app
WORKDIR $APP_HOME
COPY --from=WEATHER_API_BUILD_IMAGE $APP_HOME/build/libs/$ARTIFACT_NAME weather-api.jar
ENTRYPOINT ["java","-jar","./weather-api.jar"]