FROM openjdk:8u212-jre-alpine

RUN mkdir /bot
WORKDIR /bot
ADD target/DreamCityDCK-1.0-SNAPSHOT-jar-with-dependencies.jar /

ENTRYPOINT ["java", "-jar", "DreamCityDCK-1.0-SNAPSHOT-jar-with-dependencies.jar"]