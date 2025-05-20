FROM openjdk:17-jdk-slim AS build

WORKDIR /app

COPY pom.xml .

COPY . .

RUN mvn clean package -Dmaven.test.skip=true -B

FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=build /app/target/*.jar ms-price.jar

EXPOSE 8080

CMD ["java", "-jar", "ms-price.jar"]