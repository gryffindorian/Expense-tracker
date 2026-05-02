# 💸 Expense Tracker Backend

A secure, production-ready RESTful backend for tracking personal expenses — built with **Spring Boot**, **Spring Security (JWT)**, **PostgreSQL**, and **Docker**.

---

## 🛠 Tech Stack

| Layer | Technology |
|---|---|
| Backend | Spring Boot, Spring Security, Spring Data JPA |
| Auth | JWT (JSON Web Tokens) |
| Database | PostgreSQL |
| ORM | Hibernate / JPA |
| Containerization | Docker, Docker Compose |
| Config | Environment Variables (.env) |

---

## ✨ Features

- 🔐 **User Registration & Login** with JWT-based authentication
- 🛡 **Stateless security** via custom `JwtAuthFilter` in Spring Security filter chain
- 📋 **Expense CRUD** — Create, Read, Update, Delete expenses per user
- 📄 **Pagination & Filtering** for efficient data retrieval
- 🐳 **Fully Dockerized** — spin up the entire app with one command
- 🔑 **Environment-based secrets** — no hardcoded credentials

---

## 🏗 Architecture

```
Client
  └── Controller (REST endpoints)
        └── Service (business logic)
              └── Repository (JPA/Hibernate)
                    └── PostgreSQL
```

- **DTOs** used for clean request/response separation
- **Controller → Service → Repository** layered structure
- JWT validated on every request via custom filter before hitting controllers

---

## 🚀 Getting Started

### Prerequisites
- Docker & Docker Compose installed
- Java 17+ (only if running without Docker)

### Run with Docker

```bash
# Clone the repo
git clone https://github.com/gryffindorian/expense-tracker.git
cd expense-tracker

# Create your .env file
cp .env.example .env
# Edit .env and set your DB_PASSWORD

# Start the app
docker-compose up --build
```

App runs at `http://localhost:8080`

### Environment Variables

Create a `.env` file in the root directory:

```env
DB_PASSWORD=your_password_here
```

---

## 📡 API Endpoints

> Use **Postman** to test — browser only supports GET requests.

### Auth
| Method | Endpoint | Description |
|---|---|---|
| POST | `/auth/register` | Register a new user |
| POST | `/auth/login` | Login and receive JWT token |

### Expenses *(requires Authorization header)*
| Method | Endpoint | Description |
|---|---|---|
| GET | `/expenses` | Get all expenses (paginated) |
| POST | `/expenses` | Create a new expense |
| PUT | `/expenses/{id}` | Update an expense |
| DELETE | `/expenses/{id}` | Delete an expense |

**Authorization header format:**
```
Authorization: Bearer <your_jwt_token>
```

---

## 🐳 Docker Setup

```yaml
# docker-compose.yml spins up:
# - Spring Boot app (port 8080)
# - PostgreSQL database (port 5432)
```

```bash
# Start
docker-compose up --build

# Stop
docker-compose down
```

## 🔒 Security Implementation

- JWT token generated on login, validated on every subsequent request
- Custom `JwtAuthFilter` plugged into Spring Security filter chain
- Stateless session management — no server-side sessions
- Passwords stored as BCrypt hashes
- Secrets managed via `.env` (never hardcoded)

---

## Future Improvements

- [ ] Next frontend
- [ ] Deploy on AWS / Render
- [ ] CI/CD pipeline (GitHub Actions)

---

## 📚 Learnings

- Stateless JWT auth vs session-based auth
- Spring Security filter chain customization
- Docker multi-service orchestration with Compose
- Environment variable management and secret hygiene
- REST API design with pagination and proper HTTP semantics

---

## 👩‍💻 Author

**Ananya Jain**
[LinkedIn](https://linkedin.com/in/ananya-jain-763a63220) · [GitHub](https://github.com/gryffindorian)

<img width="1415" height="668" alt="Screenshot 2026-04-23 012608" src="https://github.com/user-attachments/assets/0c41507b-a37e-4313-9f9d-4e52b9b2968d" />

