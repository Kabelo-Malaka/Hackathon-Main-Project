# Source Tree

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

## Key Organizational Principles

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
