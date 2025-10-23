# High Level Architecture

## Technical Summary

The Employee Lifecycle Management System follows a **3-tier monolithic architecture** with clear separation between presentation (React frontend), application logic (Spring Boot API), and data storage (PostgreSQL database). The system uses RESTful APIs for client-server communication, session-based authentication with Spring Security, and asynchronous email notifications for task assignments. The architecture leverages Spring Boot's enterprise patterns for maintainability and serves ~100 concurrent users with complete audit trails and workflow orchestration capabilities.

## High Level Overview

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

## High Level Project Diagram

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
