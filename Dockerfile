FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY target/*.jar app.jar

EXPOSE 8081
ENV SERVER_PORT=8082
ENV SERVER_ADDRESS=0.0.0.0
ENTRYPOINT ["java", "-Dserver.address=0.0.0.0", "-jar", "app.jar"]