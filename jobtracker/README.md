# Job Application Tracker

A REST API backend built with Java and Spring Boot that allows users to track their job applications during a co-op or internship search.

## Tech Stack

- Java 21
- Spring Boot 3.5
- Spring Security + JWT Authentication
- PostgreSQL
- Docker & Docker Compose
- Maven

## Features

- User registration and login with JWT authentication
- Password hashing with BCrypt
- Create, read, update and delete job applications
- Filter applications by status (APPLIED, INTERVIEWING, OFFERED, REJECTED)
- Auto-generated API documentation with Swagger UI

## Getting Started

### Prerequisites
- Java 21
- Docker Desktop

### Run the project

1. Clone the repository
```
   git clone https://github.com/kavyp06/job-tracker.git
   cd job-tracker
```

2. Start the database
```
   docker compose up -d
```

3. Run the application
```
   ./mvnw spring-boot:run
```

4. Open Swagger UI at `http://localhost:8080/swagger-ui/index.html`

## API Endpoints

### Auth
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /api/auth/register | Register a new user |
| POST | /api/auth/login | Login and get JWT token |

### Applications
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | /api/applications | Create a new application |
| GET | /api/applications/user/{userId} | Get all applications |
| GET | /api/applications/user/{userId}?status=APPLIED | Filter by status |
| PUT | /api/applications/{id} | Update an application |
| DELETE | /api/applications/{id} | Delete an application |

## Database Schema

### users
| Column | Type |
|--------|------|
| id | BIGINT (PK) |
| email | VARCHAR (unique) |
| password | VARCHAR (hashed) |
| created_at | TIMESTAMP |

### job_applications
| Column | Type |
|--------|------|
| id | BIGINT (PK) |
| user_id | BIGINT (FK) |
| company | VARCHAR |
| role | VARCHAR |
| status | ENUM |
| applied_date | DATE |
| notes | TEXT |
| created_at | TIMESTAMP |

## Application Status Flow

APPLIED → INTERVIEWING → OFFERED or REJECTED