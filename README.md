# Lab Project

Full-stack application with a Spring Boot backend, PostgreSQL database, and Vue 3 frontend.

## Stack

- Java 21
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Vue 3
- Vite
- TypeScript

## Project Structure

```text
backend/   Spring Boot application
frontend/  Vue 3 application
```

## Backend Setup

Create a PostgreSQL database:

```sql
CREATE DATABASE lab_project;
```

Set the database password as an environment variable:

```bash
DB_PASSWORD=your_postgres_password
```

Run the backend:

```bash
cd backend
./mvnw spring-boot:run
```

On Windows PowerShell:

```powershell
cd backend
.\mvnw.cmd spring-boot:run
```

The backend uses:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/lab_project
spring.datasource.username=postgres
spring.datasource.password=${DB_PASSWORD}
```

## Frontend Setup

Install dependencies and start the dev server:

```bash
cd frontend
npm install
npm run dev
```

## Notes

- Do not commit local passwords, tokens, `.env` files, IDE settings, or `node_modules`.
- Hibernate is configured with `spring.jpa.hibernate.ddl-auto=update`, so database tables are created or updated when the backend starts.
