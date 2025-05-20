FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/*.jar ms-price.jar

EXPOSE 8080

CMD ["java", "-jar", "ms-price.jar"]