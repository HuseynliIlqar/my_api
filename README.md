# Earthquake API

REST API for global earthquake data — Java 17 + Spring Boot 3.

## Description

Backend REST API providing earthquake data with full CRUD operations, JWT authentication, Redis caching, and Swagger documentation. 1200+ earthquake records are seeded automatically on startup.

## Tech Stack

- Java 17, Spring Boot 3.2
- SQLite (embedded database, seeded on startup)
- Redis (cache, 5 min TTL)
- JWT (token-based authentication)
- SpringDoc OpenAPI (Swagger UI)
- Docker + Docker Compose

## Installation & Usage

**Run with Docker (recommended):**
```bash
docker-compose up --build
```

App: http://localhost:8080  
Swagger UI: http://localhost:8080/swagger-ui.html

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

## Authentication Flow

1. `POST /api/auth/register` with `{ username, email, password }`
2. `POST /api/auth/login` → receive `token`
3. Add header: `Authorization: Bearer <token>` for write operations

## Deploy to DigitalOcean (Droplet)

```bash
# 1. Create Ubuntu 22.04 Droplet on DigitalOcean, then SSH in:
ssh root@<your-droplet-ip>

# 2. Install Docker
curl -fsSL https://get.docker.com | sh

# 3. Clone the repo
git clone https://github.com/HuseynliIlqar/my_api.git
cd my_api

# 4. Set your secret
cp .env.example .env
nano .env   # set JWT_SECRET to a long random string

# 5. Run
docker compose up -d --build
```

App will be available at `http://<your-droplet-ip>:8080`  
Swagger UI: `http://<your-droplet-ip>:8080/swagger-ui.html`

## Postman Collection

[Link](#) ← add your Postman collection link here

## Live URL

[https://your-app.onrender.com](#) ← add after deploy

### The Core Team


<span><i>Made at <a href='https://qwasar.io'>Qwasar SV -- Software Engineering School</a></i></span>
<span><img alt='Qwasar SV -- Software Engineering School's Logo' src='https://storage.googleapis.com/qwasar-public/qwasar-logo_50x50.png' width='20px' /></span>
