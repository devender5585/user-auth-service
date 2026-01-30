# User Authentication Service

A secure, RESTful backend application for user authentication and authorization,
built using Spring Boot and JPA.  
This project demonstrates **industry-standard authentication practices**,
clean architecture, and incremental backend development with a strong focus on
security and maintainability.

---

## 🚀 Features

### 🔐 Authentication & Authorization
- User registration with input validation
- Secure login with encrypted passwords (BCrypt)
- JWT-based stateless authentication
- Role-based access control (ADMIN / USER)
- Protected APIs using Bearer tokens

### 🔄 Token Management
- Short-lived Access Token (JWT)
- Database-backed Refresh Token
- Refresh Token Rotation (old token invalidated on every refresh)
- Logout functionality via refresh token revocation
- Explicit handling of invalid and expired refresh tokens

### 🧩 Code Quality & Architecture
- Clean layered architecture
- DTO-based request and response models
- Builder pattern for response DTOs
- Centralized exception handling
- Performance-optimized database operations
- Clear separation between authentication and domain APIs

---

## 🧰 Tech Stack
- Java 17  
- Spring Boot 3.x  
- Spring Web  
- Spring Data JPA (Hibernate)  
- Spring Security  
- JWT (JSON Web Tokens)  
- MySQL  
- Maven  
- Lombok  

---

## 🏗️ Project Architecture

The application follows a well-defined layered architecture:

- **Controller Layer** – REST API endpoints (`AuthController`, `UserController`, `AdminController`)
- **Service Layer** – Core business and authentication logic
- **Repository Layer** – Database access using Spring Data JPA
- **DTO Layer** – Request and response data transfer objects
- **Security Layer** – JWT utilities, filters, and security configuration
- **Exception Layer** – Centralized and meaningful error handling

---

## 🗄️ Database Design

### users table
| Column        | Description |
|--------------|-------------|
| id           | Primary key |
| name         | User full name |
| email        | Unique email address |
| password     | Encrypted password (BCrypt) |
| role         | USER / ADMIN |
| status       | ACTIVE / INACTIVE |
| created_at  | Creation timestamp |
| updated_at  | Update timestamp |

### refresh_tokens table
| Column        | Description |
|--------------|-------------|
| id           | Primary key |
| token        | Refresh token (UUID) |
| user_id     | Linked user |
| expiry_time | Token expiry timestamp |

---

## 🔌 API Endpoints

### 🔑 Authentication APIs

| Method | Endpoint            | Description |
|------|---------------------|-------------|
| POST | /api/auth/register  | Register a new user |
| POST | /api/auth/login     | Login and receive access & refresh tokens |
| POST | /api/auth/refresh   | Refresh access token (with rotation) |
| POST | /api/auth/logout    | Logout user (revoke refresh token) |

---

### 👤 User APIs (USER / ADMIN)

| Method | Endpoint              | Description |
|------|-----------------------|-------------|
| GET  | /api/users/dashboard  | User dashboard (JWT required) |

---

### 🔐 Admin APIs (ADMIN only)

| Method | Endpoint               | Description |
|------|------------------------|-------------|
| GET  | /api/admin/dashboard   | Admin dashboard (ADMIN role required) |

---

## 🔄 Authentication Flow Diagram

```
Client
  |
  | Login (email, password)
  v
Auth Controller
  |
  | Access Token (JWT) + Refresh Token
  v
Client
  |
  | Authorization: Bearer <Access Token>
  v
Protected API
  |
  | (Access Token expires)
  v
Client
  |
  | Refresh Token
  v
/auth/refresh
  |
  | Validate refresh token
  | Revoke old refresh token
  | Issue new refresh token
  | Issue new access token
  v
Client
```

---

## 🔐 Logout Behavior
- Logout revokes (deletes) the refresh token from the database
- Existing access tokens remain valid until expiry
- After access token expiration, the user must log in again

---

## ▶️ How to Run

1. Clone the repository  
2. Create a MySQL database named `user_auth_db`  
3. Configure database credentials in `application.properties`  
4. Run the application:

```bash
mvn spring-boot:run
```

---

## 🧠 Design Highlights
- Stateless authentication using JWT
- Database-backed refresh tokens for enhanced security
- Refresh token rotation to prevent replay attacks
- Role-based authorization using Spring Security
- Utility classes decoupled from domain entities
- Meaningful Git commits reflecting incremental development

---

## 📌 Future Enhancements
- Refresh token reuse detection alerts
- API rate limiting
- Swagger / OpenAPI documentation
- Unit and integration test coverage

---

## 👨‍💻 Author Notes
This project is designed to demonstrate **real-world backend authentication**
using Spring Boot, focusing on security, clarity, and maintainable architecture.
