FROM openjdk:17-oracle
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} /app.jar
ENTRYPOINT ["java","-Dspring.data.mongodb.host=crypto_mongo_db", "-Dserver.port=8080", "-jar", "app.jar"]
