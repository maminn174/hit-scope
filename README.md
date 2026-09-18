# Hit Scope

Full-stack web application for checking whether a point belongs to a predefined area on a coordinate plane.

The project consists of a **Spring Boot REST API**, **PostgreSQL database**, and **Vue 3 frontend**. It demonstrates a complete client-server application with authentication, persistent data storage, validation, and interactive data visualization.

## Tech Stack

### Backend

* Java 21
* Spring Boot
* Spring MVC
* Spring Data JPA
* Hibernate
* PostgreSQL
* Maven

### Frontend

* Vue 3
* TypeScript
* Vite
* Axios
* HTML5
* CSS3

### Other

* REST API
* BCrypt
* Git

## Features

* User registration
* User authentication
* Password hashing with BCrypt
* Protected application pages
* Point validation
* Point hit detection based on coordinates and radius
* Interactive coordinate plane
* Point submission from form inputs
* Point submission by clicking on the graph
* Persistent storage of users and point results
* Results history
* REST API communication between frontend and backend
* Input validation and error handling

## Architecture

The application is separated into independent frontend and backend modules:

```text
hit-scope/
├── backend/
│   └── Spring Boot REST API
│
└── frontend/
    └── Vue 3 application
```

The backend follows a layered architecture:

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
PostgreSQL
```

### Backend

The Spring Boot application is responsible for:

* REST API endpoints
* User registration and authentication
* Business logic
* Point hit calculation
* Input validation
* Database access
* Password hashing

Spring Data JPA is used for persistence, with Hibernate as the JPA implementation.

### Frontend

The Vue 3 application is responsible for:

* Authentication and registration forms
* Point input form
* Interactive coordinate plane
* Displaying previous results
* Client-side validation
* Communication with the backend REST API

## API

The backend exposes REST endpoints for authentication and point operations.

Example authentication endpoints:

```text
POST /api/auth/register
POST /api/auth/login
```

Point operations are available under:

```text
/api/points
```

The frontend communicates with the API using HTTP requests and processes loading, success, validation, and error states.

## Database

PostgreSQL is used for persistent storage.

The application stores:

* users;
* point check results;
* coordinates and radius;
* whether the submitted point belongs to the target area.

Database access is implemented using Spring Data JPA repositories.

## Getting Started

### Requirements

Make sure the following tools are installed:

* Java 21
* PostgreSQL
* Node.js
* npm

### Database

Create a PostgreSQL database:

```sql
CREATE DATABASE hit_scope;
```

Set the database password as an environment variable:

```bash
DB_PASSWORD=your_postgres_password
```

The backend uses the following configuration:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/hit_scope
spring.datasource.username=postgres
spring.datasource.password=${DB_PASSWORD}
```

Hibernate is configured to create or update the required database tables automatically.

### Backend

Navigate to the backend directory:

```bash
cd backend
```

Run the application:

```bash
./mvnw spring-boot:run
```

On Windows PowerShell:

```powershell
.\mvnw.cmd spring-boot:run
```

The backend runs on:

```text
http://localhost:8080
```

### Frontend

Navigate to the frontend directory:

```bash
cd frontend
```

Install dependencies:

```bash
npm install
```

Start the development server:

```bash
npm run dev
```

## Project Goals

The project was built to practice designing and implementing a complete full-stack application rather than treating the frontend and backend as isolated parts.

The main areas of focus are:

* REST API design
* layered backend architecture
* relational database persistence
* authentication
* frontend-backend integration
* application state and validation
* clean separation of responsibilities

## Planned Improvements

* Spring Security
* JUnit and Mockito tests
* Global exception handling
* Improved DTO validation
* Docker Compose for the application and PostgreSQL
* API documentation
* Automated frontend and backend tests
