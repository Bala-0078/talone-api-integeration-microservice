# Employee Management System

This is a Spring Boot application for managing employees, including basic CRUD operations and secured REST endpoints.

## Features
- REST API for managing employees
- In-memory H2 database
- Spring Data JPA for persistence
- HTTP Basic authentication with role-based access
- H2 console enabled for database inspection

## Prerequisites
- Java 17 or higher
- Maven 3.6+

## Setup & Run
1. Clone the repository:
   ```
   git clone https://github.com/Bala-0078/talone-api-integeration-microservice.git
   cd talone-api-integeration-microservice
   ```
2. Build the project:
   ```
   mvn clean package
   ```
3. Run the application:
   ```
   mvn spring-boot:run
   ```
   or
   ```
   java -jar target/employeemanagement-0.0.1-SNAPSHOT.jar
   ```

## API Endpoints
- `GET /employees` - Retrieve all employees (Requires ADMIN role)
- `POST /employees` - Add a new employee (Requires ADMIN role)

## Authentication
- Username: `admin`
- Password: `admin123`

## H2 Database Console
- URL: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: `password`

## Packaging
The application is packaged as a runnable JAR file in the `target` directory after building.

## License
MIT
