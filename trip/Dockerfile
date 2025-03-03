
FROM maven:3.8.6-amazoncorretto-17 AS build

WORKDIR /trip

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src /trip/src

RUN mvn clean package

FROM amazoncorretto:17

WORKDIR /trip

COPY --from=build /trip/target/trip-0.0.1-SNAPSHOT.jar /trip/trip-service.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/trip/trip-service.jar"]
