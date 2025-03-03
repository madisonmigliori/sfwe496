
FROM maven:3.8.6-openjdk-11 AS build

WORKDIR /configserver

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src /configserver/src

RUN mvn clean package -DskipTests

FROM openjdk:11-jre-slim

WORKDIR /configserver

COPY --from=build /configserver/target/config-server.jar /configserver/config-server.jar

EXPOSE 8888

ENTRYPOINT ["java", "-jar", "/app/config-server.jar"]
