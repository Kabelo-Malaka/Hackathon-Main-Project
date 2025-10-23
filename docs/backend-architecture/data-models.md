# Data Models

The data model follows JPA entity design patterns with clear relationships between workflows, tasks, templates, and users.

## Core JPA Entities

### User Entity
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

### WorkflowTemplate Entity
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

### Workflow Entity
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

### Task Entity
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

### AuditLog Entity
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

## Design Notes

**JSON Fields (PostgreSQL JSONB):**
- Used for flexible data (`customFields`, `checklistItems`, `provisionedItems`)
- Indexed with GIN indexes for queryability
- Validated at application layer with Jakarta Validation

**Known Trade-offs:**
- JSON fields provide flexibility but sacrifice referential integrity
- `completionPercentage` stored redundantly for performance (updated via service layer)
- Template evolution without versioning (acceptable for MVP, can add later)

---
