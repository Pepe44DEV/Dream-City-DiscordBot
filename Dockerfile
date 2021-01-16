FROM openjdk:8u212-jre-alpine

ENV token=DISCORD_TOKEN
ENV DBUSER=DATABASE_USER
ENV DBPW=DATABASE_PW

RUN mkdir /bot
WORKDIR /bot
ADD bot.jar /bot

RUN echo "#!/bin/bash \n java -jar bot.jar ${token} ${DBUSER} ${DBPW}" > entrypoint.sh
RUN chmod +x entrypoint.sh
ENTRYPOINT ["sh entrypoint.sh"]