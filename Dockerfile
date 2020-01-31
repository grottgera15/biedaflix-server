FROM openjdk:8 AS TEMP_BUILD_IMAGE
ENV APP_HOME=/usr/app/
RUN mkdir -p /usr/app/
WORKDIR $APP_HOME
COPY build.gradle settings.gradle gradlew $APP_HOME
COPY gradle $APP_HOME/gradle
RUN ./gradlew build || return 0
COPY . .
RUN ./gradlew build

FROM openjdk:8
ENV ARTIFACT_NAME=biedaflix-server.jar
ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME
COPY --from=TEMP_BUILD_IMAGE $APP_HOME/build/libs/$ARTIFACT_NAME .
EXPOSE 8081

RUN apt-get update
RUN apt-get -y install ffmpeg
RUN apt-get -y install rename
RUN apt-get -y install tree
RUN tree

CMD java -jar ./$ARTIFACT_NAME