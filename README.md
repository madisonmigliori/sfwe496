# Voy Microservices Project
## Services
1. **Config Server** (port: 8888)
2. **Trip Service** (port: 8081)
3. **User Service** (port: 8082)

## Running the Project
```sh
docker-compose up --build


✅ Canonical Data Model & Microservices
Trip Service: Manages trip itineraries (CRUD operations for trips).
User Service: Handles user preferences (CRUD for user settings).
Database: Use a relational database (likely PostgreSQL, given cloud-native best practices).
Configuration Server: Stores dev and prod profiles for environment-specific configurations.

✅ Tech Stack
Backend: Spring Boot for microservices.
Database: PostgreSQL for local testing.
Containerization: Docker for microservices and database.
Configuration Management: Spring Cloud Config Server.

✅ Deliverables
Video Presentation: Explaining the project, data model, REST API, Docker deployment.
GitHub Repository: Code for microservices and config server.
Postman Workspace: API testing collection.
README: Deployment instructions.