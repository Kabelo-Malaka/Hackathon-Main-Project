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
class TaskDependencyRepositoryTest {

    @Autowired
    private TaskDependencyRepository taskDependencyRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void save_CreatesTaskDependency() {
        Task task1 = createTask();
        Task task2 = createTask();

        TaskDependency dependency = TaskDependency.builder()
                .task(task1)
                .prerequisiteTask(task2)
                .dependencyType(DependencyType.SEQUENTIAL)
                .build();

        TaskDependency saved = taskDependencyRepository.save(dependency);

        assertNotNull(saved.getId());
        assertEquals(DependencyType.SEQUENTIAL, saved.getDependencyType());
        assertNotNull(saved.getTask());
        assertNotNull(saved.getPrerequisiteTask());
    }

    @Test
    void findByTaskId_ReturnsTaskPrerequisites() {
        Task task1 = createTask();
        Task prereq1 = createTask();
        Task prereq2 = createTask();

        TaskDependency dep1 = TaskDependency.builder()
                .task(task1)
                .prerequisiteTask(prereq1)
                .dependencyType(DependencyType.SEQUENTIAL)
                .build();
        TaskDependency dep2 = TaskDependency.builder()
                .task(task1)
                .prerequisiteTask(prereq2)
                .dependencyType(DependencyType.SEQUENTIAL)
                .build();

        entityManager.persist(dep1);
        entityManager.persist(dep2);
        entityManager.flush();

        List<TaskDependency> dependencies = taskDependencyRepository.findByTaskId(task1.getId());

        assertEquals(2, dependencies.size());
    }

    @Test
    void findByPrerequisiteTaskId_ReturnsDependentTasks() {
        Task prereq = createTask();
        Task dependent1 = createTask();
        Task dependent2 = createTask();

        TaskDependency dep1 = TaskDependency.builder()
                .task(dependent1)
                .prerequisiteTask(prereq)
                .dependencyType(DependencyType.SEQUENTIAL)
                .build();
        TaskDependency dep2 = TaskDependency.builder()
                .task(dependent2)
                .prerequisiteTask(prereq)
                .dependencyType(DependencyType.SEQUENTIAL)
                .build();

        entityManager.persist(dep1);
        entityManager.persist(dep2);
        entityManager.flush();

        List<TaskDependency> dependencies = taskDependencyRepository.findByPrerequisiteTaskId(prereq.getId());

        assertEquals(2, dependencies.size());
    }

    private Task createTask() {
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
        entityManager.persist(user);

        WorkflowInstance instance = WorkflowInstance.builder()
                .template(template)
                .employee(employee)
                .initiatedBy(user)
                .status(WorkflowInstanceStatus.IN_PROGRESS)
                .createdAt(now)
                .updatedAt(now)
                .build();
        entityManager.persist(instance);

        Task task = Task.builder()
                .workflowInstance(instance)
                .title("Test Task")
                .taskType(TaskType.FORM_COMPLETION)
                .status(TaskStatus.NOT_STARTED)
                .createdAt(now)
                .updatedAt(now)
                .build();
        return entityManager.persist(task);
    }
}
