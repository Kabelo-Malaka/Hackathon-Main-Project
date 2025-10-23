# Testing Strategy

Comprehensive testing approach using JUnit 5, Mockito, and Spring Boot Test.

## Unit Tests

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

## Integration Tests

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

## Repository Tests

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

## Test Coverage Goals

- **Unit Tests**: 80%+ coverage for service and utility classes
- **Integration Tests**: All controller endpoints
- **Repository Tests**: Custom query methods
- **E2E Tests**: Critical user journeys (Playwright from frontend)

---
