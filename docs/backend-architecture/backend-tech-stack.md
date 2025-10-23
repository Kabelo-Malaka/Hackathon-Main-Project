# Backend Tech Stack

## Technology Stack Table (Per PRD)

| Category | Technology | Version | Purpose | Rationale |
|----------|-----------|---------|---------|-----------|
| **Language** | Java | 17+ LTS | Primary language | Type safety, enterprise reliability, Spring compatibility |
| **Framework** | Spring Boot | 3.x | Web application framework | Comprehensive enterprise framework, auto-configuration, production-ready |
| **Build Tool** | Maven or Gradle | Latest | Dependency management & build | Standard Java build tools, Spring Initializr support |
| **Database** | PostgreSQL | 16.x | Primary database | ACID compliance, JSON support, complex relationships, proven reliability |
| **ORM** | Spring Data JPA | 3.x (included) | Data access layer | Type-safe repositories, reduces boilerplate, Hibernate integration |
| **Hibernate** | Hibernate | 6.x (via JPA) | JPA implementation | Mature ORM, lazy loading, caching, query optimization |
| **Connection Pool** | HikariCP | (Boot default) | Database connection pooling | Fastest connection pool, Spring Boot default, production-ready |
| **Database Migration** | Flyway | Latest | Schema versioning | Version control for database, automated migrations |
| **Security** | Spring Security | 6.x (included) | Authentication & authorization | Session management, RBAC, CSRF protection, BCrypt hashing |
| **Email** | Spring Mail | 3.x (included) | SMTP email sending | JavaMail API integration, template support, Outlook compatibility |
| **Validation** | Jakarta Validation | 3.x | Bean validation | @Valid annotations, constraint validation, clean validation |
| **API Documentation** | Springdoc OpenAPI | 2.x | Swagger UI generation | Auto-generated API docs, interactive testing, OpenAPI 3.0 |
| **Logging** | Logback | (Boot default) | Application logging | Spring Boot default, SLF4J facade, log rotation |
| **Async Processing** | Spring @Async | (included) | Background tasks | Email sending, non-blocking operations, thread pool management |
| **Scheduling** | Spring @Scheduled | (included) | Cron jobs | Daily reminders (FR10), automated cleanup tasks |
| **Testing Framework** | JUnit 5 | Latest | Unit testing | Industry standard, Spring Boot Test integration |
| **Mocking** | Mockito | Latest | Test mocking | Mock dependencies, verify interactions |
| **API Testing** | Spring MockMvc | (included) | REST endpoint testing | Test controllers without server, fast integration tests |
| **HTTP Client** | RestTemplate/WebClient | (included) | External API calls | Built-in Spring HTTP clients (if needed for integrations) |

## Development Tools

| Category | Technology | Version | Purpose | Rationale |
|----------|-----------|---------|---------|-----------|
| **IDE** | IntelliJ IDEA / Eclipse | Latest | Java development | Spring Boot support, code generation, debugging |
| **API Testing** | Postman / Insomnia | Latest | Manual API testing | Interactive request testing during development |
| **Database Client** | DBeaver / pgAdmin | Latest | PostgreSQL management | Visual database management and querying |
| **Lombok** | Lombok | Latest | Reduce boilerplate | Auto-generate getters/setters, constructors, builders |

## Key Spring Dependencies (pom.xml / build.gradle)

```xml
<!-- Core Spring Boot Starters -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-mail</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>

<!-- Database -->
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <scope>runtime</scope>
</dependency>
<dependency>
    <groupId>org.flywaydb</groupId>
    <artifactId>flyway-core</artifactId>
</dependency>

<!-- API Documentation -->
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.x</version>
</dependency>

<!-- Utilities -->
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
</dependency>

<!-- Testing -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>org.springframework.security</groupId>
    <artifactId>spring-security-test</artifactId>
    <scope>test</scope>
</dependency>
```

---
