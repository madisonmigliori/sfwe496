# Step 1: Build the project with Maven
FROM maven:3.8.6-amazoncorretto-17 AS build

WORKDIR /configserver

# Copy the pom.xml to download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the source code to the container
COPY src /configserver/src

# Package the application into a JAR
RUN mvn clean package

# Step 2: Create the runtime image
FROM amazoncorretto:17

WORKDIR /configserver

# Copy the JAR file from the build stage
COPY --from=build /configserver/target/configserver-0.0.1-SNAPSHOT.jar /configserver/config-server.jar

# Expose the port for the Config Server
EXPOSE 8071

# Set the entrypoint and make sure to use the correct path for the JAR file
ENTRYPOINT ["java", "-jar", "/configserver/config-server.jar"]
