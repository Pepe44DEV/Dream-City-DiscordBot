FROM openjdk:8u212-jre-alpine

RUN mkdir /bot
WORKDIR /bot
ADD bot.jar /

ENTRYPOINT ["java", "-jar", "bot.jar"]