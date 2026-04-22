# 💊 Pharmacy Web App — Backend

A RESTful API backend for an online pharmacy management web application, built with **Spring Boot 3** and **MongoDB**.

---

## 🏗️ Tech Architecture

```
┌─────────────────────────────────────────────────────────────┐
│                        Client (Frontend)                    │
└────────────────────────────┬────────────────────────────────┘
                             │ HTTP / REST API
┌────────────────────────────▼────────────────────────────────┐
│                    Spring Boot 3 Application                │
│                                                             │
│  ┌─────────────┐   ┌─────────────┐   ┌──────────────────┐  │
│  │  Controller │──▶│   Service   │──▶│   Repository     │  │
│  │  (REST API) │   │  (Business) │   │  (Spring Data)   │  │
│  └─────────────┘   └─────────────┘   └────────┬─────────┘  │
│                                               │             │
│  ┌──────────────────────────────┐             │             │
│  │     Spring Security + JWT    │             │             │
│  │  Auth Filter → UserDetails   │             │             │
│  └──────────────────────────────┘             │             │
└───────────────────────────────────────────────┼─────────────┘
                                                │
          ┌─────────────────┐    ┌──────────────▼──────────┐
          │   Cloudinary    │    │         MongoDB         │
          │  (Image Store)  │    │       (Database)        │
          └─────────────────┘    └─────────────────────────┘
```

---

## 🛠️ Technology Stack

| Layer | Technology |
|---|---|
| Language | Java 21 |
| Framework | Spring Boot 3.5.x |
| Database | MongoDB (Spring Data MongoDB) |
| Security | Spring Security + JWT (jjwt 0.12.3) |
| Image Upload | Cloudinary HTTP5 |
| API Docs | Springdoc OpenAPI (Swagger UI 2.3.0) |
| HTTP Client | OkHttp 4.12 + Apache HttpClient 4.5 |
| JSON | Gson 2.10.1 + Jackson Databind |
| Environment Config | spring-dotenv |
| Build Tool | Maven (Maven Wrapper) |
| Containerization | Docker (multi-stage build) |
| Code Generation | Lombok |

---

## 🔐 Security Architecture

Authentication and authorization are handled via **JWT (JSON Web Token)** integrated with Spring Security.

```
Incoming Request
       │
       ▼
JwtAuthenticationFilter
       │  → Extract token from Authorization header
       │  → Validate token signature & expiry
       │  → Load UserDetails from MongoDB
       ▼
SecurityContextHolder  ←── Authenticated principal stored here
       │
       ▼
Controller  ←── Protected endpoints (role-based access control)
```

- Tokens signed using **HMAC-SHA256 (HS256)**
- **Stateless** — no server-side session storage
- Role-based access control applied at the controller level

---

## 🗄️ Data Layer

- **Database**: MongoDB (document-based NoSQL)
- **ORM**: Spring Data MongoDB (`MongoRepository`)
- **Pattern**: Classic layered architecture — Controller → Service → Repository
- Documents are mapped as Java POJOs annotated with `@Document`

---

## 📁 Project Structure

```
src/main/java/com/example/pharmacy_webapp/
├── config/           # Security config, CORS, Cloudinary, OpenAPI beans
├── controller/       # REST Controllers — API endpoints
├── service/          # Business logic layer
├── repository/       # Spring Data MongoDB interfaces
├── model/            # MongoDB document models (@Document)
├── dto/              # Request / Response Data Transfer Objects
├── security/         # JWT utility, auth filter, UserDetailsService
└── PharmacyWebappApplication.java
```

---

## 🐳 Docker — Multi-Stage Build

```dockerfile
# Stage 1: Build
FROM maven:3.9-eclipse-temurin-21 AS build
# Downloads dependencies and compiles the JAR

# Stage 2: Run
FROM eclipse-temurin:21-jre-alpine
# Lightweight JRE-only image — smaller and more secure
# JVM tuned for memory-constrained environments (e.g. Render)
ENV JAVA_OPTS="-Xmx400m -Xms256m -XX:+UseG1GC"
```

Benefits: smaller final image, faster deployment, no build tools in production container.

---
## 🚀 Getting Started

### Run with Maven

```bash
git clone https://github.com/KhanhDang21/pharmacy_webapp_backend.git
cd pharmacy_webapp_backend

./mvnw spring-boot:run
```

### Run with Docker

```bash
docker build -t pharmacy-backend .
docker run -p 8080:8080 --env-file .env pharmacy-backend
```

Server starts at: `http://localhost:8080`

Swagger UI: `http://localhost:8080/swagger-ui/index.html`

---

## 📋 Requirements

- Java 21+
- MongoDB (local or Atlas)
- Docker (optional)

---

## 👤 Author

**KhanhDang21** — [GitHub](https://github.com/KhanhDang21)