package com.company.employeelifecycle.repository;

import com.company.employeelifecycle.entity.Employee;
import com.company.employeelifecycle.entity.User;
import com.company.employeelifecycle.entity.WorkflowInstance;
import com.company.employeelifecycle.entity.WorkflowTemplate;
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
class WorkflowInstanceRepositoryTest {

    @Autowired
    private WorkflowInstanceRepository workflowInstanceRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void save_CreatesWorkflowInstance() {
        WorkflowTemplate template = createTemplate();
        Employee employee = createEmployee();
        User user = createUser();

        LocalDateTime now = LocalDateTime.now();
        WorkflowInstance instance = WorkflowInstance.builder()
                .template(template)
                .employee(employee)
                .initiatedBy(user)
                .status(WorkflowInstanceStatus.NOT_STARTED)
                .currentStepIndex(0)
                .createdAt(now)
                .updatedAt(now)
                .build();

        WorkflowInstance saved = workflowInstanceRepository.save(instance);

        assertNotNull(saved.getId());
        assertEquals(WorkflowInstanceStatus.NOT_STARTED, saved.getStatus());
        assertNotNull(saved.getTemplate());
        assertNotNull(saved.getEmployee());
    }

    @Test
    void findByStatus_ReturnsMatchingInstances() {
        WorkflowTemplate template = createTemplate();
        Employee employee = createEmployee();
        User user = createUser();

        WorkflowInstance inProgress1 = createInstance(template, employee, user, WorkflowInstanceStatus.IN_PROGRESS);
        WorkflowInstance inProgress2 = createInstance(template, employee, user, WorkflowInstanceStatus.IN_PROGRESS);
        WorkflowInstance completed = createInstance(template, employee, user, WorkflowInstanceStatus.COMPLETED);

        entityManager.persist(inProgress1);
        entityManager.persist(inProgress2);
        entityManager.persist(completed);
        entityManager.flush();

        List<WorkflowInstance> inProgressInstances = workflowInstanceRepository.findByStatus(WorkflowInstanceStatus.IN_PROGRESS);

        assertEquals(2, inProgressInstances.size());
    }

    @Test
    void findByEmployeeId_ReturnsEmployeeInstances() {
        WorkflowTemplate template = createTemplate();
        Employee employee1 = createEmployee();
        Employee employee2 = createEmployee();
        User user = createUser();

        WorkflowInstance instance1 = createInstance(template, employee1, user, WorkflowInstanceStatus.IN_PROGRESS);
        WorkflowInstance instance2 = createInstance(template, employee1, user, WorkflowInstanceStatus.COMPLETED);
        WorkflowInstance instance3 = createInstance(template, employee2, user, WorkflowInstanceStatus.IN_PROGRESS);

        entityManager.persist(instance1);
        entityManager.persist(instance2);
        entityManager.persist(instance3);
        entityManager.flush();

        List<WorkflowInstance> employee1Instances = workflowInstanceRepository.findByEmployeeId(employee1.getId());

        assertEquals(2, employee1Instances.size());
    }

    private WorkflowTemplate createTemplate() {
        LocalDateTime now = LocalDateTime.now();
        WorkflowTemplate template = WorkflowTemplate.builder()
                .name("Test Template")
                .type(WorkflowType.ONBOARDING)
                .active(true)
                .createdAt(now)
                .updatedAt(now)
                .build();
        return entityManager.persist(template);
    }

    private Employee createEmployee() {
        LocalDateTime now = LocalDateTime.now();
        Employee employee = Employee.builder()
                .firstName("Test")
                .lastName("Employee")
                .email("test" + UUID.randomUUID().toString() + "@test.com")
                .role("Developer")
                .status(EmployeeStatus.ACTIVE)
                .createdAt(now)
                .updatedAt(now)
                .build();
        return entityManager.persist(employee);
    }

    private User createUser() {
        LocalDateTime now = LocalDateTime.now();
        User user = User.builder()
                .email("user" + UUID.randomUUID().toString() + "@test.com")
                .password("hashed")
                .firstName("Test")
                .lastName("User")
                .role(UserRole.HR_ADMIN)
                .active(true)
                .createdAt(now)
                .updatedAt(now)
                .build();
        return entityManager.persist(user);
    }

    private WorkflowInstance createInstance(WorkflowTemplate template, Employee employee, User user, WorkflowInstanceStatus status) {
        LocalDateTime now = LocalDateTime.now();
        return WorkflowInstance.builder()
                .template(template)
                .employee(employee)
                .initiatedBy(user)
                .status(status)
                .createdAt(now)
                .updatedAt(now)
                .build();
    }
}
