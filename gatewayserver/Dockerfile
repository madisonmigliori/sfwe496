
FROM maven:3.8.6-amazoncorretto-17 AS build

WORKDIR /gatewayserver
COPY pom.xml .
RUN mvn dependency:go-offline


COPY src /gatewayserver/src


RUN mvn clean package


FROM amazoncorretto:17

WORKDIR /gatewayserver

COPY --from=build /gatewayserver/target/gatewayserver-0.0.1-SNAPSHOT.jar /gatewayserver/gateway-server.jar

EXPOSE 8072

ENTRYPOINT ["java", "-jar", "/gatewayserver/gateway-server.jar"]
