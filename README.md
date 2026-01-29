
# User Authentication Service

A RESTful backend application for user management and role-based authentication,
built using Spring Boot and JPA. This project demonstrates clean architecture,
incremental development, and industry-standard backend practices.

---

## 🚀 Features
- User registration with validation
- User login support
- Role-based user management (ADMIN / USER)
- Clean layered architecture
- Centralized exception handling
- RESTful API design

---

## 🧰 Tech Stack
- Java 17
- Spring Boot 3.x
- Spring Web
- Spring Data JPA
- Spring Security
- MySQL
- Maven

---

## 🏗️ Project Architecture
The application follows a layered architecture:

- **Controller Layer** – Handles REST API requests
- **Service Layer** – Contains business logic
- **Repository Layer** – Manages database operations
- **DTO Layer** – Handles request and response objects
- **Exception Layer** – Centralized error handling

---

## 🗄️ Database Design

### users table
| Column        | Description              |
|--------------|--------------------------|
| id           | Primary key              |
| name         | User full name           |
| email        | Unique email address     |
| password     | Encrypted password       |
| role         | USER / ADMIN             |
| status       | ACTIVE / INACTIVE        |
| created_at   | Creation timestamp       |
| updated_at   | Update timestamp         |

---

## 🔌 API Endpoints (Phase 1)

| Method | Endpoint                 | Description           |
|------|--------------------------|-----------------------|
| POST | /api/users/register      | Register new user     |
| POST | /api/users/login         | User login            |
| GET  | /api/users/{id}          | Get user by ID        |

---

## ▶️ How to Run
1. Clone the repository
2. Create a MySQL database named `user_auth_db`
3. Update database credentials in `application.properties`
4. Run the application using:
