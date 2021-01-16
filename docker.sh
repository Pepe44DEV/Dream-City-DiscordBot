mvn clean package
mv target/DreamCityDCK-1.0-SNAPSHOT-jar-with-dependencies.jar bot.jar
docker build -t tallerik/dreamcity-bot:latest .
docker push tallerik/dreamcity-bot:latest