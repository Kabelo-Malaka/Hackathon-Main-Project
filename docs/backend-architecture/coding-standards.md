# Coding Standards

Java and Spring Boot coding standards for consistency and maintainability.

## Naming Conventions

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

## Spring Annotations

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

## Critical Rules

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

## Code Quality

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

