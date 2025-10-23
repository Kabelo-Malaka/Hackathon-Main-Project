# Employee Lifecycle Backend

Spring Boot 3.x backend application for the Employee Lifecycle Management System.

## Technology Stack

- Java 17+
- Spring Boot 3.2.0
- Spring Data JPA (Hibernate 6.x)
- Spring Security 6.x
- PostgreSQL 16.x
- Flyway Database Migrations
- Maven

## Project Structure

```
backend/
├── src/
│   ├── main/
│   │   ├── java/com/company/employeelifecycle/
│   │   │   ├── EmployeeLifecycleApplication.java
│   │   │   ├── config/          # Configuration classes
│   │   │   └── controller/      # REST controllers
│   │   └── resources/
│   │       ├── application.properties
│   │       └── db/migration/    # Flyway SQL migrations
│   └── test/                    # Test files
└── pom.xml
```

## Running the Application

### Prerequisites
- Java 17+ installed
- PostgreSQL running (via Docker Compose)

### Run with Maven

```bash
./mvnw spring-boot:run
```

Or on Windows:
```bash
mvnw.cmd spring-boot:run
```

The application will start on `http://localhost:8080`

## API Endpoints

### Health Check
```
GET /api/health
```

Response:
```json
{"status":"UP"}
```

## Database Configuration

The application connects to PostgreSQL with these default settings:

- **URL**: `jdbc:postgresql://localhost:5432/employee_lifecycle`
- **Username**: `postgres`
- **Password**: `postgres`

Configuration can be changed in `src/main/resources/application.properties`

## Database Migrations

Flyway automatically runs migrations on application startup.

Migrations are located in: `src/main/resources/db/migration/`

### Migration Naming Convention
- `V1__init.sql` - Initial schema
- `V2__description.sql` - Next migration
- etc.

## Running Tests

```bash
./mvnw test
```

## Building for Production

```bash
./mvnw clean package
```

The JAR file will be created in `target/employee-lifecycle-0.0.1-SNAPSHOT.jar`

Run the JAR:
```bash
java -jar target/employee-lifecycle-0.0.1-SNAPSHOT.jar
```

## Development Notes

### Security Configuration
Currently configured to permit all requests (development mode). Full authentication will be implemented in later stories.

### Logging
Application logs are set to DEBUG level for development. Check `application.properties` to adjust log levels.

## Troubleshooting

### Application won't start
- Check if PostgreSQL is running: `docker ps`
- Verify database connection settings in `application.properties`
- Check for port conflicts on 8080

### Flyway migration errors
- Ensure database is accessible
- Check migration SQL syntax
- To reset: drop database and restart

## IDE Setup

### IntelliJ IDEA
1. Open project as Maven project
2. Enable annotation processing for Lombok
3. Install Lombok plugin

### VS Code
1. Install Java Extension Pack
2. Install Spring Boot Extension Pack
