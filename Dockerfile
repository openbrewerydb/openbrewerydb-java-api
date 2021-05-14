FROM adoptopenjdk/openjdk16:alpine-slim
RUN mkdir /opt/app
COPY build/libs/openbrewerydb-java-api-1.0-SNAPSHOT-all.jar /opt/app
CMD ["java", "-jar", "/opt/app/openbrewerydb-java-api-1.0-SNAPSHOT-all.jar"]
