FROM gradle:7.6-jdk8-jammy AS build

COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

FROM openjdk:8-jre-slim

EXPOSE 8080

RUN mkdir - p /app

COPY --from=build /home/gradle/src/build/libs/*.jar /app/chessRating.jar

ENV SPRING_PROFILES_ACTIVE=prod

ENTRYPOINT ["java","-jar","-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}","/app/chessRating.jar"]