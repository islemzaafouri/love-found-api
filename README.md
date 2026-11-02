# LoveFound API

> The backend REST API for **LoveFound**, a pet adoption platform that connects people looking to adopt with shelters that have pets in need of a home.

**Frontend:** [love-found-frontend](https://github.com/islemzaafouri/love-found-frontend)

## Overview

LoveFound is designed around three user roles:

- **Adopters** can create an account, manage their adoption profile, browse available pets, and submit and manage adoption applications.
- **Shelters** can create an account, manage their shelter profile, and manage the pets listed by their shelter. They can review applications submitted for their pets.
- **Admins** are represented in the role model and the security configuration.

The API is built with Spring Boot and exposes REST endpoints consumed by the Angular frontend.

## Tech stack

- Java 21
- Spring Boot 4.1.0
- Spring Web MVC
- Spring Data JPA / Hibernate
- Spring Security
- JSON Web Tokens (JWT)
- MySQL
- Flyway database migrations
- Jakarta Bean Validation
- Maven
- Lombok

## Architecture

The codebase is organized by domain. Each feature keeps its API, data-transfer objects, persistence, and business logic close together.

```text
src/main/java/com/lovefound/love_found_api/
├── auth/
│   ├── controller/
│   ├── dto/
│   ├── model/
│   ├── repository/
│   └── service/
├── pet/
│   ├── controller/
│   ├── dto/
│   ├── model/
│   ├── repository/
│   └── service/
├── profiles/
│   ├── dto/
│   ├── entities/
│   ├── enums/
│   ├── Repos/
│   └── service/
├── adoption_application/
│   ├── controllers/
│   ├── dto/
│   ├── models/
│   ├── repository/
│   └── services/
└── core/
    ├── config/
    ├── exceptions/
    └── security/
```

Typical request flow:

1. A controller receives and validates an HTTP request.
2. The service layer applies the business rules.
3. A repository accesses persisted entities through JPA.
4. DTOs define the data returned to or accepted from the frontend.

## Main capabilities

### Authentication and authorization
- Adopter and shelter registration.
- Login endpoint that returns an authentication response containing a JWT.
- Stateless request authentication through a JWT filter.
- Password hashing with BCrypt.
- Role-based access rules for adopters, shelters, and admins.

### Pet management
- Browse available pets and view pet details.
- Filter pets by species or shelter.
- Shelter-owned pet creation, editing, activation, and deactivation.

### Profiles
- Retrieve and update the authenticated adopter's profile.
- Retrieve and update the authenticated shelter's profile.

### Adoption applications
- Submit an application for a pet.
- View and update an adopter's applications.
- Cancel an application.
- Let shelters review applications for their pets and update application status.

## API overview

Base URL for local development: `http://localhost:8080`

| Area | Method | Endpoint | Purpose |
|---|---|---|---|
| Auth | POST | `/api/auth/register/adopter` | Register an adopter |
| Auth | POST | `/api/auth/register/shelter` | Register a shelter |
| Auth | POST | `/api/auth/login` | Log in |
| Pets | GET | `/api/pets` | List available pets |
| Pets | GET | `/api/pets/{id}` | Get pet details |
| Pets | GET | `/api/pets/species/{species}` | Filter by species |
| Pets | GET | `/api/pets/shelter/{shelterId}` | List a shelter's pets |
| Pets | GET | `/api/pets/all` | List all pets |
| Pets | POST | `/api/pets` | Create a pet (shelter) |
| Pets | PUT | `/api/pets/{id}` | Update a pet (shelter owner) |
| Pets | DELETE | `/api/pets/{id}` | Deactivate a pet (shelter owner) |
| Pets | PUT | `/api/pets/activate/{id}` | Reactivate a pet (shelter owner) |
| Profiles | GET | `/api/profile/adopter/me` | Get my adopter profile |
| Profiles | PUT | `/api/profile/adopter/me` | Update my adopter profile |
| Profiles | GET | `/api/profile/shelter/me` | Get my shelter profile |
| Profiles | PUT | `/api/profile/shelter/me` | Update my shelter profile |
| Applications | POST | `/api/applications` | Submit an application |
| Applications | GET | `/api/applications/my-applications` | Get my applications |
| Applications | GET | `/api/applications/{applicationId}` | Get application details |
| Applications | PUT | `/api/applications/{applicationId}` | Update my application |
| Applications | PUT | `/api/applications/{applicationId}/cancel` | Cancel my application |
| Applications | GET | `/api/applications/shelter-applications` | Get applications for my pets |
| Applications | PUT | `/api/applications/{applicationId}/status` | Update application status |

Protected endpoints require the JWT expected by the API's security filter. Access is additionally restricted by user role and, for shelter pet operations, ownership checks in the service/controller flow.

## Data model

The Flyway migrations currently define the following main tables:

- `users`
- `adopter_profiles`
- `shelter_profiles`
- `pets`
- `pet_personality_traits`
- `applications`

An application references an adopter profile and a pet. The database enforces one application per adopter/pet pair. Application statuses are `PENDING`, `ACCEPTED`, `REJECTED`, and `CANCELLED`.

Database schema changes are stored under:

`src/main/resources/db/migration/`

## Run locally

### Prerequisites

- JDK 21
- MySQL
- Git

Maven Wrapper scripts are included, so a separate Maven installation is not required.

### 1. Clone the repository

```bash
git clone https://github.com/islemzaafouri/love-found-api.git
cd love-found-api
```

### 2. Create a MySQL database

Create an empty database for the application, for example:

```sql
CREATE DATABASE lovefound;
```

### 3. Configure database access

Provide the Spring Boot datasource settings for your local MySQL instance using environment variables or a local, uncommitted `application.properties` file.

Typical Spring Boot environment variables are:

```text
SPRING_DATASOURCE_URL
SPRING_DATASOURCE_USERNAME
SPRING_DATASOURCE_PASSWORD
```

Do not commit real database credentials, JWT signing secrets, or other private configuration.

> The repository does not currently include a committed application configuration file. Configure the datasource and JWT signing settings required by the application before starting it.

### 4. Run the API

On Windows:

```powershell
.`mvnw.cmd` spring-boot:run
```

On macOS / Linux:

```bash
chmod +x mvnw
./mvnw spring-boot:run
```

The API is expected to listen on port `8080` with the current frontend development configuration.

Flyway migrations are included in the project and are used to manage the database schema at startup.

## Docker

A Dockerfile is included in the repository. It builds the application with Maven and runs the packaged JAR on port `8080`.

Build the image:

```bash
docker build -f dockerfile -t love-found-api .
```

Run it after providing the required database and JWT configuration:

```bash
docker run --rm -p 8080:8080 \
  -e SPRING_DATASOURCE_URL=jdbc:mysql://host.docker.internal:3306/lovefound \
  -e SPRING_DATASOURCE_USERNAME=your_username \
  -e SPRING_DATASOURCE_PASSWORD=your_password \
  love-found-api
```

Also provide \`JWT_SECRET\` and \`JWT_EXPIRATION\` when running the container. The signing secret must be kept private and must be at least 32 bytes for the configured HS256 algorithm. This repository does not yet include a Docker Compose setup.

## Related repository

- [LoveFound Frontend — Angular application](https://github.com/islemzaafouri/love-found-frontend)

## Project status

LoveFound is an actively developed project. The README describes the current repository structure and implemented API areas; endpoint behavior and configuration may evolve as development continues.
