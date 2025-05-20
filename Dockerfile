FROM openjdk:17-jdk-slim AS build

RUN apt-get update && \
    apt-get install -y --no-install-recommends wget ca-certificates && \
    rm -rf /var/lib/apt/lists/*

ENV MAVEN_HOME /usr/share/maven
ENV MAVEN_VERSION 3.9.9
ENV PATH $MAVEN_HOME/bin:$PATH

RUN wget https://archive.apache.org/dist/maven/maven-3/${MAVEN_VERSION}/binaries/apache-maven-${MAVEN_VERSION}-bin.tar.gz -P /tmp && \
    tar -xvf /tmp/apache-maven-${MAVEN_VERSION}-bin.tar.gz -C /usr/share && \
    mv /usr/share/apache-maven-${MAVEN_VERSION} /usr/share/maven && \
    rm /tmp/apache-maven-${MAVEN_VERSION}-bin.tar.gz

WORKDIR /app

COPY pom.xml .

COPY . .

RUN mvn clean package -Dmaven.test.skip=true -B

FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=build /app/target/*.jar ms-price.jar

EXPOSE 8080

CMD ["java", "-jar", "ms-price.jar"]