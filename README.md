# Earthquake API

REST API for global earthquake data — Java 17 + Spring Boot 3.

## Task

Build a backend REST API around a topic with more than 1000 rows of data. The API must support user authentication with JWT tokens, full CRUD operations, pagination, Redis caching, and Swagger documentation. The project must be hosted in the cloud.

## Description

Backend REST API providing earthquake data with full CRUD operations, JWT authentication, Redis caching, and Swagger documentation. 1200+ earthquake records are seeded automatically on startup.

## Installation

```bash
# 1. Clone the repo
git clone https://github.com/HuseynliIlqar/my_api.git
cd my_api

# 2. Set your secret
cp .env.example .env
nano .env   # set JWT_SECRET to a long random string

# 3. Run with Docker
docker compose up -d --build
```

## Usage

App: `http://localhost:8080`  
Swagger UI: `http://localhost:8080/swagger-ui.html`

**Register and login:**
```bash
# Register
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","email":"admin@example.com","password":"password123"}'

# Login — copy the token from the response
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"password123"}'
```

**Use the API:**
```bash
# Get earthquakes (public)
curl http://localhost:8080/api/earthquakes?page=0&size=20

# Create (requires token)
curl -X POST http://localhost:8080/api/earthquakes \
  -H "Authorization: Bearer <token>" \
  -H "Content-Type: application/json" \
  -d '{"magnitude":6.2,"place":"10km N of Baku","depth":12.5,"latitude":40.51,"longitude":49.93}'
```

## API Endpoints

### Authentication
| Method | Endpoint | Auth | Description |
|--------|----------|------|-------------|
| POST | /api/auth/register | No | Register new user |
| POST | /api/auth/login | No | Login, receive JWT token |

### Earthquakes
| Method | Endpoint | Auth | Description |
|--------|----------|------|-------------|
| GET | /api/earthquakes | No | Paginated list (max 20/page) |
| GET | /api/earthquakes/{id} | No | Get by ID |
| POST | /api/earthquakes | JWT | Create record |
| PUT | /api/earthquakes/{id} | JWT | Update record |
| DELETE | /api/earthquakes/{id} | JWT | Delete record |

**Query params for GET /api/earthquakes:**
- `page` — page number (default: 0)
- `size` — page size, max 20 (default: 20)
- `minMag` — minimum magnitude (e.g. 5.0)
- `place` — partial place name (e.g. Japan)

## Tech Stack

- Java 17, Spring Boot 3.2
- SQLite (embedded database, seeded on startup)
- Redis (cache, 5 min TTL)
- JWT (token-based authentication)
- SpringDoc OpenAPI (Swagger UI)
- Docker + Docker Compose

## Live URL

http://209.38.217.227:8080/swagger-ui.html

## Postman Collection

[Link](#) ← add your Postman collection link here

### The Core Team


<span><i>Made at <a href='https://qwasar.io'>Qwasar SV -- Software Engineering School</a></i></span>
<span><img alt='Qwasar SV -- Software Engineering School's Logo' src='https://storage.googleapis.com/qwasar-public/qwasar-logo_50x50.png' width='20px' /></span>
