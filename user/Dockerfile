
FROM maven:3.8.6-amazoncorretto-17 AS build

WORKDIR /user

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src /user/src
RUN mvn clean package

FROM amazoncorretto:17

WORKDIR /user


COPY --from=build /user/target/user-0.0.1-SNAPSHOT.jar /user/user-preference.jar


EXPOSE 8081


ENTRYPOINT ["java", "-jar", "/user/user-preference.jar"]
