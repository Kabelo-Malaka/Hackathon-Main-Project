# Employee Lifecycle Management System Backend Architecture Document

## Change Log

| Date | Version | Description | Author |
|------|---------|-------------|--------|
| 2025-10-22 | 1.0 | Initial backend architecture document | Winston (Architect) |

---

## Introduction

This document outlines the backend architecture for the Employee Lifecycle Management System, including API design, database schema, service architecture, and deployment strategy. Its primary goal is to serve as the guiding architectural blueprint for AI-driven backend development, ensuring consistency and adherence to chosen patterns and technologies.

**Relationship to Frontend Architecture:**
This document works in conjunction with the Frontend Architecture Document (`docs/ui-architecture.md`) which defines the React + TypeScript frontend using Vite, Material-UI, and React Query. The backend architecture detailed here provides the REST API and data services consumed by that frontend.

**Core System Purpose:**
The backend supports a workflow orchestration platform that automates employee onboarding and offboarding processes. It must handle:
- Workflow template management (drag-and-drop designer support)
- Dynamic task routing based on roles and departments
- Mandatory verification checklists
- Complete audit trails
- Email notifications via Outlook/SMTP integration
- Role-based access control (HR Admin, Manager, Tech Support, Finance)

### Starter Template Decision

**Backend Framework:** Spring Boot 3.x with Java 17+

**Rationale (from PRD NFR17):**
- Enterprise-grade Java framework with proven reliability
- Spring ecosystem provides comprehensive solutions (Security, Data JPA, Mail)
- Strong type safety and compile-time error detection
- Excellent ORM with Spring Data JPA and Hibernate
- Built-in dependency injection and inversion of control
- Mature enterprise support and extensive community

**Starter Template:** Spring Initializr (start.spring.io)
- Spring Web for REST API
- Spring Data JPA for database access
- Spring Security for authentication and authorization
- Spring Mail (JavaMail API) for SMTP integration
- Springdoc OpenAPI for Swagger documentation
- PostgreSQL Driver
- Maven or Gradle for dependency management

---

## High Level Architecture

### Technical Summary

The Employee Lifecycle Management System follows a **3-tier monolithic architecture** with clear separation between presentation (React frontend), application logic (Spring Boot API), and data storage (PostgreSQL database). The system uses RESTful APIs for client-server communication, session-based authentication with Spring Security, and asynchronous email notifications for task assignments. The architecture leverages Spring Boot's enterprise patterns for maintainability and serves ~100 concurrent users with complete audit trails and workflow orchestration capabilities.

### High Level Overview

**Architectural Style:** Monolithic 3-tier architecture
- Single deployable backend application
- Clear layer separation (controllers → services → repositories)
- Shared database with strong referential integrity
- Synchronous REST API with asynchronous email processing

**Repository Structure:** Monorepo
- Frontend and backend in single repository
- Shared TypeScript types between frontend/backend
- Simplified deployment and versioning
- Single CI/CD pipeline

**Service Architecture:** Single backend service
- No microservices complexity needed for initial scope
- All workflow logic in one application
- Database transactions ensure data consistency
- Background job queue for email sending

**Primary Data Flow:**
1. Frontend initiates workflow creation via REST API
2. Backend validates, creates workflow, and generates tasks
3. Task assignments trigger email notifications (queued)
4. Users complete tasks via UI, backend updates workflow state
5. Workflow completion triggers offboarding mirror generation
6. All actions recorded in audit log

**Key Architectural Decisions:**

1. **Monolith over Microservices**
   - Rationale: 100 concurrent users, single development team, tightly coupled workflow logic
   - Trade-off: Easier deployment and debugging vs. independent scaling

2. **PostgreSQL over NoSQL**
   - Rationale: Complex relationships (workflows → tasks → checklists), ACID transactions critical for audit trails
   - Trade-off: Schema rigidity vs. data integrity guarantees

3. **Session-based Authentication (Spring Security)**
   - Rationale: HTTP-only cookies for security, built-in CSRF protection, easier revocation
   - Implementation: Spring Security with session management, 30-minute timeout
   - Trade-off: Server-side session storage vs. enhanced security for internal tool

4. **Async Email Processing with Spring @Async**
   - Rationale: Don't block API responses, leverage Spring's built-in async support
   - Implementation: Spring @Async with TaskExecutor thread pool
   - Trade-off: Slight delivery delay vs. reliability and user experience

### High Level Project Diagram

```mermaid
graph TB
    subgraph "Client Layer"
        UI[React Frontend<br/>Vite + TypeScript + MUI]
    end

    subgraph "Spring Boot Application"
        subgraph "Controller Layer"
            Controllers[REST Controllers<br/>@RestController]
            Security[Spring Security<br/>Session Auth + RBAC]
        end

        subgraph "Service Layer"
            WorkflowSvc[@Service<br/>WorkflowService]
            TaskSvc[@Service<br/>TaskService]
            TemplateSvc[@Service<br/>TemplateService]
            UserSvc[@Service<br/>UserService]
            AuditSvc[@Service<br/>AuditService]
            EmailSvc[@Service @Async<br/>EmailService]
        end

        subgraph "Repository Layer"
            Repos[Spring Data JPA<br/>@Repository Interfaces]
        end
    end

    subgraph "Data Layer"
        DB[(PostgreSQL 16.x<br/>HikariCP Pool)]
    end

    subgraph "External Services"
        SMTP[Outlook SMTP<br/>Spring JavaMail]
    end

    UI -->|HTTPS/JSON| Controllers
    Controllers --> Security
    Security --> WorkflowSvc
    Security --> TaskSvc
    Security --> TemplateSvc
    Security --> UserSvc

    WorkflowSvc --> Repos
    TaskSvc --> Repos
    TemplateSvc --> Repos
    UserSvc --> Repos

    WorkflowSvc --> AuditSvc
    TaskSvc --> AuditSvc
    AuditSvc --> Repos

    TaskSvc --> EmailSvc
    WorkflowSvc --> EmailSvc
    EmailSvc -->|@Async| SMTP

    Repos -->|Hibernate/JPA| DB
```

---

## Backend Tech Stack

### Technology Stack Table (Per PRD)

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

### Development Tools

| Category | Technology | Version | Purpose | Rationale |
|----------|-----------|---------|---------|-----------|
| **IDE** | IntelliJ IDEA / Eclipse | Latest | Java development | Spring Boot support, code generation, debugging |
| **API Testing** | Postman / Insomnia | Latest | Manual API testing | Interactive request testing during development |
| **Database Client** | DBeaver / pgAdmin | Latest | PostgreSQL management | Visual database management and querying |
| **Lombok** | Lombok | Latest | Reduce boilerplate | Auto-generate getters/setters, constructors, builders |

### Key Spring Dependencies (pom.xml / build.gradle)

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

## Data Models

The data model follows JPA entity design patterns with clear relationships between workflows, tasks, templates, and users.

### Core JPA Entities

#### User Entity
```java
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password; // BCrypt hashed

    private String firstName;
    private String lastName;

    @Enumerated(EnumType.STRING)
    private UserRole role; // HR_ADMIN, MANAGER, TECH_SUPPORT, FINANCE, SYSTEM_ADMIN

    private String department;
    private Boolean active = true;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
```

#### WorkflowTemplate Entity
```java
@Entity
@Table(name = "workflow_templates")
public class WorkflowTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    @Enumerated(EnumType.STRING)
    private WorkflowType type; // ONBOARDING, OFFBOARDING

    private String role;
    private String description;

    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private Map<String, Object> customFields; // Dynamic fields (FR18)

    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private List<ConditionalRule> conditionalRules; // If-then logic (FR20)

    private Boolean active = true;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    @OneToMany(mappedBy = "workflowTemplate", cascade = CascadeType.ALL)
    private List<TaskTemplate> taskTemplates;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
```

#### Workflow Entity
```java
@Entity
@Table(name = "workflows")
public class Workflow {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "workflow_template_id")
    private WorkflowTemplate workflowTemplate;

    @Enumerated(EnumType.STRING)
    private WorkflowType type;

    private String employeeName;
    private String employeeEmail;
    private String role;
    private String department;

    private LocalDate startDate;
    private LocalDate targetCompletionDate;
    private LocalDateTime actualCompletionDate;

    @Enumerated(EnumType.STRING)
    private WorkflowStatus status; // NOT_STARTED, IN_PROGRESS, BLOCKED, COMPLETED

    private Integer completionPercentage = 0;

    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private Map<String, Object> customFieldValues; // Dynamic values (FR19)

    @ManyToOne
    @JoinColumn(name = "mirrored_from_workflow_id")
    private Workflow mirroredFromWorkflow; // Offboarding mirror (FR15)

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    @OneToMany(mappedBy = "workflow", cascade = CascadeType.ALL)
    private List<Task> tasks;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
```

#### Task Entity
```java
@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "workflow_id", nullable = false)
    private Workflow workflow;

    @ManyToOne
    @JoinColumn(name = "task_template_id")
    private TaskTemplate taskTemplate;

    private String title;
    private String description;

    @ManyToOne
    @JoinColumn(name = "assigned_to")
    private User assignedTo;

    @Enumerated(EnumType.STRING)
    private UserRole assignedRole;

    @Enumerated(EnumType.STRING)
    private TaskStatus status; // PENDING, IN_PROGRESS, COMPLETED

    private LocalDate dueDate;
    private LocalDateTime startedAt;
    private LocalDateTime completedAt;

    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private List<ChecklistItem> checklistItems; // Verification items (FR13, FR16)

    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private List<ProvisionedItem> provisionedItems; // For offboarding mirror (FR14)

    private String notes;
    private Integer orderIndex;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
```

#### AuditLog Entity
```java
@Entity
@Table(name = "audit_logs")
@Immutable
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    private EntityType entityType; // WORKFLOW, TASK, USER, TEMPLATE

    private UUID entityId;

    @Enumerated(EnumType.STRING)
    private AuditAction action; // CREATED, UPDATED, DELETED, COMPLETED, ASSIGNED

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private Map<String, Object> changes; // Before/after state

    @Type(JsonType.class)
    @Column(columnDefinition = "jsonb")
    private Map<String, Object> metadata; // IP, user agent, etc.

    @CreatedDate
    private LocalDateTime timestamp;
}
```

### Design Notes

**JSON Fields (PostgreSQL JSONB):**
- Used for flexible data (`customFields`, `checklistItems`, `provisionedItems`)
- Indexed with GIN indexes for queryability
- Validated at application layer with Jakarta Validation

**Known Trade-offs:**
- JSON fields provide flexibility but sacrifice referential integrity
- `completionPercentage` stored redundantly for performance (updated via service layer)
- Template evolution without versioning (acceptable for MVP, can add later)

---

## API Specification

RESTful API following Spring MVC conventions with standard HTTP methods and status codes.

### Base URL
```
http://localhost:8080/api
```

### Authentication Endpoints

**POST /api/auth/login**
- Purpose: Authenticate user and create session
- Request Body:
```json
{
  "email": "admin@company.com",
  "password": "SecurePass123"
}
```
- Response: 200 OK with session cookie
```json
{
  "success": true,
  "user": {
    "id": "uuid",
    "email": "admin@company.com",
    "firstName": "John",
    "lastName": "Doe",
    "role": "HR_ADMIN"
  }
}
```

**POST /api/auth/logout**
- Purpose: Invalidate session
- Response: 200 OK

**GET /api/auth/me**
- Purpose: Get current user info
- Response: 200 OK with user object

### Workflow Endpoints

**GET /api/workflows**
- Purpose: Get all workflows with optional filtering (FR23)
- Query Parameters: `?status=IN_PROGRESS&department=Engineering&page=0&size=20`
- Response: Paginated list of workflows

**GET /api/workflows/{id}**
- Purpose: Get workflow details
- Response: Complete workflow with tasks

**POST /api/workflows**
- Purpose: Create new workflow (FR1, FR3)
- Request Body:
```json
{
  "workflowTemplateId": "uuid",
  "employeeName": "Jane Smith",
  "employeeEmail": "jane@company.com",
  "role": "Software Engineer",
  "department": "Engineering",
  "startDate": "2025-11-01",
  "customFieldValues": {
    "remote": true,
    "startLocation": "Home Office"
  }
}
```
- Response: 201 Created with workflow object

**PATCH /api/workflows/{id}**
- Purpose: Update workflow status or details
- Request Body: Partial workflow object
- Response: 200 OK with updated workflow

**DELETE /api/workflows/{id}**
- Purpose: Delete workflow (admin only)
- Response: 204 No Content

### Task Endpoints

**GET /api/workflows/{workflowId}/tasks**
- Purpose: Get all tasks for a workflow
- Response: List of tasks

**GET /api/tasks/{id}**
- Purpose: Get task details with checklist
- Response: Task object with checklist items

**PATCH /api/tasks/{id}**
- Purpose: Update task (status, checklist completion, notes)
- Request Body:
```json
{
  "status": "IN_PROGRESS",
  "checklistItems": [
    {"id": "1", "label": "Create AD account", "checked": true},
    {"id": "2", "label": "Assign email", "checked": false}
  ],
  "notes": "Waiting for manager approval"
}
```
- Response: 200 OK with updated task

**POST /api/tasks/{id}/complete**
- Purpose: Mark task as complete (validates all required checklist items)
- Response: 200 OK or 400 Bad Request if validation fails

**POST /api/tasks/{id}/reassign**
- Purpose: Reassign task to different user (FR22)
- Request Body:
```json
{
  "assignedToUserId": "uuid"
}
```
- Response: 200 OK with updated task

### Template Endpoints (HR Admin only)

**GET /api/templates**
- Purpose: Get all workflow templates
- Response: List of templates

**POST /api/templates**
- Purpose: Create new template (FR2)
- Request Body: Template object with task templates
- Response: 201 Created

**PUT /api/templates/{id}**
- Purpose: Update template
- Response: 200 OK

**DELETE /api/templates/{id}**
- Purpose: Deactivate template
- Response: 204 No Content

### Audit Endpoints

**GET /api/audit**
- Purpose: Get audit trail (FR17)
- Query Parameters: `?entityType=WORKFLOW&entityId=uuid&page=0&size=50`
- Response: Paginated audit logs

### Health Check

**GET /api/health**
- Purpose: Health check endpoint
- Response:
```json
{
  "status": "UP",
  "timestamp": "2025-10-22T10:30:00Z"
}
```

### Error Responses

All errors follow standard format:
```json
{
  "timestamp": "2025-10-22T10:30:00Z",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed: All required checklist items must be checked",
  "path": "/api/tasks/123/complete"
}
```

---

## Source Tree

Spring Boot backend following standard Maven/Gradle project structure with clear layer separation.

```
backend/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── company/
│   │   │           └── employeelifecycle/
│   │   │               ├── EmployeeLifecycleApplication.java  # Main Spring Boot application
│   │   │               ├── config/                             # Configuration classes
│   │   │               │   ├── SecurityConfig.java
│   │   │               │   ├── AsyncConfig.java
│   │   │               │   ├── OpenApiConfig.java
│   │   │               │   └── DataSourceConfig.java
│   │   │               ├── controller/                         # REST Controllers
│   │   │               │   ├── AuthController.java
│   │   │               │   ├── WorkflowController.java
│   │   │               │   ├── TaskController.java
│   │   │               │   ├── TemplateController.java
│   │   │               │   └── AuditController.java
│   │   │               ├── service/                            # Business logic
│   │   │               │   ├── WorkflowService.java
│   │   │               │   ├── TaskService.java
│   │   │               │   ├── TemplateService.java
│   │   │               │   ├── UserService.java
│   │   │               │   ├── AuditService.java
│   │   │               │   ├── EmailService.java
│   │   │               │   └── impl/                           # Service implementations
│   │   │               ├── repository/                         # Spring Data JPA repositories
│   │   │               │   ├── UserRepository.java
│   │   │               │   ├── WorkflowRepository.java
│   │   │               │   ├── WorkflowTemplateRepository.java
│   │   │               │   ├── TaskRepository.java
│   │   │               │   ├── TaskTemplateRepository.java
│   │   │               │   └── AuditLogRepository.java
│   │   │               ├── entity/                             # JPA entities
│   │   │               │   ├── User.java
│   │   │               │   ├── Workflow.java
│   │   │               │   ├── WorkflowTemplate.java
│   │   │               │   ├── Task.java
│   │   │               │   ├── TaskTemplate.java
│   │   │               │   └── AuditLog.java
│   │   │               ├── dto/                                # Data Transfer Objects
│   │   │               │   ├── request/
│   │   │               │   │   ├── CreateWorkflowRequest.java
│   │   │               │   │   ├── UpdateTaskRequest.java
│   │   │               │   │   └── LoginRequest.java
│   │   │               │   └── response/
│   │   │               │       ├── WorkflowResponse.java
│   │   │               │       ├── TaskResponse.java
│   │   │               │       └── UserResponse.java
│   │   │               ├── enums/                              # Enumerations
│   │   │               │   ├── UserRole.java
│   │   │               │   ├── WorkflowType.java
│   │   │               │   ├── WorkflowStatus.java
│   │   │               │   ├── TaskStatus.java
│   │   │               │   └── AuditAction.java
│   │   │               ├── exception/                          # Custom exceptions
│   │   │               │   ├── ResourceNotFoundException.java
│   │   │               │   ├── ValidationException.java
│   │   │               │   ├── UnauthorizedException.java
│   │   │               │   └── GlobalExceptionHandler.java     # @ControllerAdvice
│   │   │               ├── security/                           # Security components
│   │   │               │   ├── CustomUserDetailsService.java
│   │   │               │   ├── AuthenticationSuccessHandler.java
│   │   │               │   └── AuthenticationFailureHandler.java
│   │   │               ├── mapper/                             # Entity-DTO mappers
│   │   │               │   ├── WorkflowMapper.java
│   │   │               │   ├── TaskMapper.java
│   │   │               │   └── UserMapper.java
│   │   │               ├── validation/                         # Custom validators
│   │   │               │   └── ChecklistValidator.java
│   │   │               └── util/                               # Utility classes
│   │   │                   ├── DateUtils.java
│   │   │                   └── EmailTemplates.java
│   │   └── resources/
│   │       ├── application.properties                          # Main configuration
│   │       ├── application-dev.properties                      # Dev profile
│   │       ├── application-prod.properties                     # Prod profile
│   │       ├── db/
│   │       │   └── migration/                                  # Flyway migrations
│   │       │       ├── V1__create_users_table.sql
│   │       │       ├── V2__create_workflow_templates.sql
│   │       │       ├── V3__create_workflows.sql
│   │       │       ├── V4__create_tasks.sql
│   │       │       └── V5__create_audit_logs.sql
│   │       ├── templates/                                      # Email templates
│   │       │   ├── task-assigned.html
│   │       │   ├── task-reminder.html
│   │       │   └── workflow-complete.html
│   │       └── static/                                         # Static resources
│   └── test/
│       ├── java/
│       │   └── com/
│       │       └── company/
│       │           └── employeelifecycle/
│       │               ├── controller/                         # Controller tests
│       │               │   ├── WorkflowControllerTest.java
│       │               │   └── TaskControllerTest.java
│       │               ├── service/                            # Service tests
│       │               │   ├── WorkflowServiceTest.java
│       │               │   └── TaskServiceTest.java
│       │               ├── repository/                         # Repository tests
│       │               │   └── WorkflowRepositoryTest.java
│       │               └── integration/                        # Integration tests
│       │                   └── WorkflowIntegrationTest.java
│       └── resources/
│           └── application-test.properties                     # Test configuration
├── pom.xml                                                     # Maven configuration
└── README.md                                                   # Backend documentation
```

### Key Organizational Principles

**1. Layer Separation:**
- **Controller**: HTTP/REST concerns, validation, response formatting
- **Service**: Business logic, orchestration, transactions
- **Repository**: Data access, queries
- **Entity**: Domain models

**2. Package by Feature (Alternative):**
For larger projects, consider organizing by feature instead of layer:
```
com/company/employeelifecycle/
├── workflow/
│   ├── WorkflowController.java
│   ├── WorkflowService.java
│   ├── WorkflowRepository.java
│   └── Workflow.java
└── task/
    ├── TaskController.java
    ├── TaskService.java
    └── Task.java
```

**3. Configuration:**
- Separate profiles for dev/prod environments
- Externalized configuration via application.properties
- Sensitive data via environment variables

---

## Security

Comprehensive security implementation using Spring Security.

### Authentication

**Session-Based Authentication:**
```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/login", "/api/health").permitAll()
                .requestMatchers("/api/templates/**").hasRole("HR_ADMIN")
                .requestMatchers("/api/**").authenticated()
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .maximumSessions(1)
                .maxSessionsPreventsLogin(false)
            )
            .sessionManagement(session -> session
                .sessionFixation().changeSessionId()
                .invalidSessionUrl("/api/auth/login")
                .sessionAuthenticationErrorUrl("/api/auth/login")
            )
            .formLogin(form -> form
                .loginProcessingUrl("/api/auth/login")
                .successHandler(authenticationSuccessHandler())
                .failureHandler(authenticationFailureHandler())
            )
            .logout(logout -> logout
                .logoutUrl("/api/auth/logout")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
            )
            .csrf(csrf -> csrf
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
            );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12); // Strength factor 12
    }
}
```

### Authorization (RBAC)

**Role-Based Access Control:**
- `HR_ADMIN`: Full access, template management
- `MANAGER`: View team workflows, provide employee details
- `TECH_SUPPORT`: Complete provisioning tasks, view assigned tasks
- `FINANCE`: Complete finance tasks, view assigned tasks
- `SYSTEM_ADMIN`: Full system access, user management

**Method-Level Security:**
```java
@Service
public class TemplateService {

    @PreAuthorize("hasRole('HR_ADMIN')")
    public WorkflowTemplate createTemplate(CreateTemplateRequest request) {
        // Only HR admins can create templates
    }

    @PreAuthorize("hasAnyRole('HR_ADMIN', 'MANAGER')")
    public Workflow createWorkflow(CreateWorkflowRequest request) {
        // HR and managers can create workflows
    }
}
```

### Security Measures

1. **Password Security**
   - BCrypt hashing with salt (strength factor 12)
   - Minimum password requirements enforced
   - No password storage in logs or audit trails

2. **Session Management**
   - HTTP-only session cookies
   - 30-minute inactivity timeout
   - Session fixation protection
   - Single session per user

3. **CSRF Protection**
   - Enabled for all state-changing operations
   - CSRF token in cookie (accessible to frontend)
   - Validated on POST/PUT/PATCH/DELETE

4. **HTTPS Enforcement**
   - Production requires HTTPS
   - HTTP Strict Transport Security (HSTS) headers
   - Secure cookies in production

5. **SQL Injection Prevention**
   - Parameterized queries via JPA/Hibernate
   - No raw SQL string concatenation

6. **XSS Prevention**
   - Input validation and sanitization
   - Output encoding in responses
   - Content Security Policy headers

7. **Rate Limiting**
   - Max 5 login attempts per IP per minute
   - API rate limiting (100 requests/minute per user)

---

## Testing Strategy

Comprehensive testing approach using JUnit 5, Mockito, and Spring Boot Test.

### Unit Tests

**Service Layer Tests:**
```java
@ExtendWith(MockitoExtension.class)
class WorkflowServiceTest {

    @Mock
    private WorkflowRepository workflowRepository;

    @Mock
    private TaskService taskService;

    @Mock
    private AuditService auditService;

    @InjectMocks
    private WorkflowService workflowService;

    @Test
    void createWorkflow_Success() {
        // Arrange
        CreateWorkflowRequest request = new CreateWorkflowRequest(/*...*/);
        Workflow workflow = new Workflow(/*...*/);
        when(workflowRepository.save(any())).thenReturn(workflow);

        // Act
        Workflow result = workflowService.createWorkflow(request);

        // Assert
        assertNotNull(result);
        assertEquals("Jane Smith", result.getEmployeeName());
        verify(taskService).generateTasksFromTemplate(any(), any());
        verify(auditService).logWorkflowCreation(any());
    }
}
```

### Integration Tests

**Controller Integration Tests:**
```java
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class WorkflowControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(roles = "HR_ADMIN")
    void createWorkflow_ReturnsCreated() throws Exception {
        CreateWorkflowRequest request = new CreateWorkflowRequest(/*...*/);

        mockMvc.perform(post("/api/workflows")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.employeeName").value("Jane Smith"))
            .andExpect(jsonPath("$.status").value("NOT_STARTED"));
    }
}
```

### Repository Tests

**Data Layer Tests:**
```java
@DataJpaTest
class WorkflowRepositoryTest {

    @Autowired
    private WorkflowRepository workflowRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void findByStatus_ReturnsMatchingWorkflows() {
        // Arrange
        Workflow workflow = new Workflow(/*...*/);
        workflow.setStatus(WorkflowStatus.IN_PROGRESS);
        entityManager.persist(workflow);

        // Act
        List<Workflow> results = workflowRepository.findByStatus(WorkflowStatus.IN_PROGRESS);

        // Assert
        assertEquals(1, results.size());
        assertEquals(WorkflowStatus.IN_PROGRESS, results.get(0).getStatus());
    }
}
```

### Test Coverage Goals

- **Unit Tests**: 80%+ coverage for service and utility classes
- **Integration Tests**: All controller endpoints
- **Repository Tests**: Custom query methods
- **E2E Tests**: Critical user journeys (Playwright from frontend)

---

## Coding Standards

Java and Spring Boot coding standards for consistency and maintainability.

### Naming Conventions

**Classes:**
- Controllers: `*Controller` (e.g., `WorkflowController`)
- Services: `*Service` (e.g., `WorkflowService`)
- Repositories: `*Repository` (e.g., `WorkflowRepository`)
- Entities: Domain name (e.g., `Workflow`, `Task`)
- DTOs: `*Request`, `*Response` (e.g., `CreateWorkflowRequest`)
- Exceptions: `*Exception` (e.g., `ResourceNotFoundException`)

**Methods:**
- camelCase (e.g., `createWorkflow`, `findByStatus`)
- Boolean methods: `is*`, `has*`, `can*` (e.g., `isComplete`, `hasPermission`)

**Variables:**
- camelCase (e.g., `workflowId`, `taskList`)
- Constants: UPPER_SNAKE_CASE (e.g., `MAX_RETRIES`, `DEFAULT_PAGE_SIZE`)

### Spring Annotations

**Controller Layer:**
```java
@RestController
@RequestMapping("/api/workflows")
@RequiredArgsConstructor
@Validated
public class WorkflowController {

    private final WorkflowService workflowService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public WorkflowResponse createWorkflow(@Valid @RequestBody CreateWorkflowRequest request) {
        return workflowService.createWorkflow(request);
    }
}
```

**Service Layer:**
```java
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class WorkflowService {

    private final WorkflowRepository workflowRepository;
    private final AuditService auditService;

    public Workflow createWorkflow(CreateWorkflowRequest request) {
        log.info("Creating workflow for employee: {}", request.getEmployeeName());
        // Business logic
    }
}
```

### Critical Rules

1. **NEVER expose entities directly** - Always use DTOs in controllers
2. **ALWAYS validate input** - Use `@Valid` and Jakarta Validation annotations
3. **ALWAYS use transactions** - `@Transactional` on service methods that modify data
4. **ALWAYS log appropriately** - Use SLF4J with appropriate log levels
5. **NEVER catch generic Exception** - Catch specific exceptions
6. **ALWAYS use Optional** - For nullable return values
7. **NEVER use `null` checks** - Use Optional, `@NonNull`, or validation
8. **ALWAYS document public APIs** - Use Javadoc for public methods
9. **NEVER hardcode values** - Use application.properties or constants
10. **ALWAYS clean up resources** - Use try-with-resources for closeable objects

### Code Quality

**Lombok Usage:**
```java
@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Workflow {
    // Fields only, Lombok generates getters/setters/constructors
}
```

**Validation:**
```java
public class CreateWorkflowRequest {

    @NotBlank(message = "Employee name is required")
    @Size(max = 100)
    private String employeeName;

    @Email(message = "Invalid email format")
    @NotBlank
    private String employeeEmail;

    @NotNull
    @FutureOrPresent
    private LocalDate startDate;
}
```

---

**End of Document**

