# Step 1: Build the project with Maven
FROM maven:3.8.6-amazoncorretto-17 AS build

WORKDIR /gatewayserver

# Copy the pom.xml to download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the source code to the container
COPY src /gatewayserver/src

# Package the application into a JAR
RUN mvn clean package

# Step 2: Create the runtime image
FROM amazoncorretto:17

WORKDIR /gatewayserver

# Copy the JAR file from the build stage
COPY --from=build /gatewayserver/target/gatewayserver-0.0.1-SNAPSHOT.jar /gatewayserver/gateway-server.jar

# Expose the port for the Gateway Server
EXPOSE 8072

# Set the entrypoint and make sure to use the correct path for the JAR file
ENTRYPOINT ["java", "-jar", "/gatewayserver/gateway-server.jar"]
