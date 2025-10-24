package com.company.employeelifecycle.repository;

import com.company.employeelifecycle.entity.*;
import com.company.employeelifecycle.enums.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void save_CreatesTask() {
        WorkflowInstance instance = createWorkflowInstance();
        User user = createUser();

        LocalDateTime now = LocalDateTime.now();
        Task task = Task.builder()
                .workflowInstance(instance)
                .title("Test Task")
                .description("Test Description")
                .assignedTo(user)
                .taskType(TaskType.FORM_COMPLETION)
                .status(TaskStatus.NOT_STARTED)
                .createdAt(now)
                .updatedAt(now)
                .build();

        Task saved = taskRepository.save(task);

        assertNotNull(saved.getId());
        assertEquals("Test Task", saved.getTitle());
        assertEquals(TaskType.FORM_COMPLETION, saved.getTaskType());
    }

    @Test
    void findByAssignedToId_ReturnsUserTasks() {
        WorkflowInstance instance = createWorkflowInstance();
        User user1 = createUser();
        User user2 = createUser();

        Task task1 = createTask(instance, user1);
        Task task2 = createTask(instance, user1);
        Task task3 = createTask(instance, user2);

        entityManager.persist(task1);
        entityManager.persist(task2);
        entityManager.persist(task3);
        entityManager.flush();

        List<Task> user1Tasks = taskRepository.findByAssignedToId(user1.getId());

        assertEquals(2, user1Tasks.size());
    }

    @Test
    void findByWorkflowInstanceId_ReturnsWorkflowTasks() {
        WorkflowInstance instance1 = createWorkflowInstance();
        WorkflowInstance instance2 = createWorkflowInstance();
        User user = createUser();

        Task task1 = createTask(instance1, user);
        Task task2 = createTask(instance1, user);
        Task task3 = createTask(instance2, user);

        entityManager.persist(task1);
        entityManager.persist(task2);
        entityManager.persist(task3);
        entityManager.flush();

        List<Task> instance1Tasks = taskRepository.findByWorkflowInstanceId(instance1.getId());

        assertEquals(2, instance1Tasks.size());
    }

    @Test
    void findByStatusAndAssignedToId_ReturnsMatchingTasks() {
        WorkflowInstance instance = createWorkflowInstance();
        User user = createUser();

        LocalDateTime now = LocalDateTime.now();
        Task inProgress = Task.builder()
                .workflowInstance(instance)
                .title("In Progress Task")
                .assignedTo(user)
                .taskType(TaskType.CHECKLIST)
                .status(TaskStatus.IN_PROGRESS)
                .createdAt(now)
                .updatedAt(now)
                .build();
        Task completed = Task.builder()
                .workflowInstance(instance)
                .title("Completed Task")
                .assignedTo(user)
                .taskType(TaskType.CHECKLIST)
                .status(TaskStatus.COMPLETED)
                .createdAt(now)
                .updatedAt(now)
                .build();

        entityManager.persist(inProgress);
        entityManager.persist(completed);
        entityManager.flush();

        List<Task> inProgressTasks = taskRepository.findByStatusAndAssignedToId(TaskStatus.IN_PROGRESS, user.getId());

        assertEquals(1, inProgressTasks.size());
        assertEquals("In Progress Task", inProgressTasks.get(0).getTitle());
    }

    private WorkflowInstance createWorkflowInstance() {
        LocalDateTime now = LocalDateTime.now();
        WorkflowTemplate template = WorkflowTemplate.builder()
                .name("Test Template")
                .type(WorkflowType.ONBOARDING)
                .active(true)
                .createdAt(now)
                .updatedAt(now)
                .build();
        entityManager.persist(template);

        Employee employee = Employee.builder()
                .firstName("Test")
                .lastName("Employee")
                .email("emp" + UUID.randomUUID().toString() + "@test.com")
                .role("Developer")
                .status(EmployeeStatus.ACTIVE)
                .createdAt(now)
                .updatedAt(now)
                .build();
        entityManager.persist(employee);

        User initiator = createUser();

        WorkflowInstance instance = WorkflowInstance.builder()
                .template(template)
                .employee(employee)
                .initiatedBy(initiator)
                .status(WorkflowInstanceStatus.IN_PROGRESS)
                .createdAt(now)
                .updatedAt(now)
                .build();
        return entityManager.persist(instance);
    }

    private User createUser() {
        LocalDateTime now = LocalDateTime.now();
        User user = User.builder()
                .email("user" + UUID.randomUUID().toString() + "@test.com")
                .password("hashed")
                .firstName("Test")
                .lastName("User")
                .role(UserRole.MANAGER)
                .active(true)
                .createdAt(now)
                .updatedAt(now)
                .build();
        return entityManager.persist(user);
    }

    private Task createTask(WorkflowInstance instance, User assignedTo) {
        LocalDateTime now = LocalDateTime.now();
        return Task.builder()
                .workflowInstance(instance)
                .title("Test Task")
                .assignedTo(assignedTo)
                .taskType(TaskType.FORM_COMPLETION)
                .status(TaskStatus.NOT_STARTED)
                .createdAt(now)
                .updatedAt(now)
                .build();
    }
}
