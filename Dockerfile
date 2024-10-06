
FROM openjdk:8-jre-slim

WORKDIR /app

EXPOSE 8090

COPY build/libs/*.jar /app/chessRating.jar

ENV SPRING_PROFILES_ACTIVE=prod

ENTRYPOINT ["java","-jar","-Dspring.profiles.active=${SPRING_PROFILES_ACTIVE}","/app/chessRating.jar"]