# Voy Microservices Project
Overview
This project implements a cloud-native system using a microservices architecture to manage trips and user preferences. The system consists of three primary microservices:

Config Server (port: 8888) – Manages environment-specific configurations (dev/prod).
Trip Service (port: 8081) – Handles trip itineraries (CRUD operations for trips).
User Service (port: 8082) – Manages user preferences and settings.
Tech Stack
✅ Backend: Spring Boot (Microservices)
✅ Database: PostgreSQL (Local & Cloud Deployment)
✅ Containerization: Docker & Docker Compose
✅ Configuration Management: Spring Cloud Config Server
✅ API Testing: Postman

### Canonical Data Model & Microservices
Trip Service: CRUD operations for trip itineraries.
User Service: CRUD operations for user data.
Configuration Server: Centralized configuration management.
Database: Uses a relational database (PostgreSQL) for storing structured data.
Running the Project
Ensure you have Docker and Docker Compose installed. Then, run:


`docker-compose up --build`
This command will:

Build and start all microservices in Docker containers.
Set up the database and config server.

Deliverables
📌 Video Presentation: Covers project overview, data model, REST API demo, and Docker deployment.
📌 GitHub Repository: Contains the microservices and config server source code.
📌 Postman Workspace: API testing collection for endpoints.
📌 README: Detailed deployment and usage instructions.