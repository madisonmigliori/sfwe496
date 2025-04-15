# Step 1: Build the project with Maven
FROM maven:3.8.6-amazoncorretto-17 AS build

WORKDIR /eurekaserver

# Copy the pom.xml to download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the source code to the container
COPY src /eurekaserver/src

# Package the application into a JAR
RUN mvn clean package

# Step 2: Create the runtime image
FROM amazoncorretto:17

WORKDIR /eurekaserver

# Copy the JAR file from the build stage
COPY --from=build /eurekaserver/target/eurekaserver-0.0.1-SNAPSHOT.jar /eurekaserver/eureka-server.jar

# Expose the port for the Config Server
EXPOSE 8761

# Set the entrypoint and make sure to use the correct path for the JAR file
ENTRYPOINT ["java", "-jar", "/eurekaserver/eureka-server.jar"]
