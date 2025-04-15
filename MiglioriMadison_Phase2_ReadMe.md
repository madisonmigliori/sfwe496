# Phase 2: Cloud-Native Security Demo

## 🔐 Keycloak Realm
- Realm: sfwe496
- Clients: user-service, trip-service
- Roles: USER, ADMIN
- Public registration: enabled
- Password reset: available

## 🚀 Services
- Gateway: http://localhost:8072
- User-Service: http://localhost:8081
- Trip-Service: http://localhost:8083
- Keycloak: http://localhost:8084
- Eureka: http://localhost:8761
- Config: http://localhost:8071
## 🔑 Endpoints
- [POST] /auth/login → Get JWT token
- [GET] /users (protected)
- [POST] /register → Register new user
- [POST] /reset-password → Password reset ( email)

## 📦 Run Locally
docker-compose up --build
