package com.company.employeelifecycle.repository;

import com.company.employeelifecycle.entity.Employee;
import com.company.employeelifecycle.entity.User;
import com.company.employeelifecycle.enums.EmployeeStatus;
import com.company.employeelifecycle.enums.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void save_CreatesEmployee() {
        LocalDateTime now = LocalDateTime.now();
        Employee employee = Employee.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@test.com")
                .role("Software Engineer")
                .department("Engineering")
                .startDate(LocalDate.now())
                .status(EmployeeStatus.PENDING)
                .createdAt(now)
                .updatedAt(now)
                .build();

        Employee saved = employeeRepository.save(employee);

        assertNotNull(saved.getId());
        assertEquals("John", saved.getFirstName());
        assertEquals("john.doe@test.com", saved.getEmail());
        assertNotNull(saved.getCreatedAt());
        assertNotNull(saved.getUpdatedAt());
    }

    @Test
    void findByEmail_ReturnsEmployee() {
        LocalDateTime now = LocalDateTime.now();
        Employee employee = Employee.builder()
                .firstName("Jane")
                .lastName("Smith")
                .email("jane.smith@test.com")
                .role("Product Manager")
                .status(EmployeeStatus.ACTIVE)
                .createdAt(now)
                .updatedAt(now)
                .build();
        entityManager.persist(employee);
        entityManager.flush();

        Optional<Employee> found = employeeRepository.findByEmail("jane.smith@test.com");

        assertTrue(found.isPresent());
        assertEquals("Jane", found.get().getFirstName());
    }

    @Test
    void findByStatus_ReturnsMatchingEmployees() {
        LocalDateTime now = LocalDateTime.now();
        Employee active1 = Employee.builder()
                .firstName("Active")
                .lastName("One")
                .email("active1@test.com")
                .role("Developer")
                .status(EmployeeStatus.ACTIVE)
                .createdAt(now)
                .updatedAt(now)
                .build();
        Employee active2 = Employee.builder()
                .firstName("Active")
                .lastName("Two")
                .email("active2@test.com")
                .role("Developer")
                .status(EmployeeStatus.ACTIVE)
                .createdAt(now)
                .updatedAt(now)
                .build();
        Employee pending = Employee.builder()
                .firstName("Pending")
                .lastName("One")
                .email("pending@test.com")
                .role("Developer")
                .status(EmployeeStatus.PENDING)
                .createdAt(now)
                .updatedAt(now)
                .build();

        entityManager.persist(active1);
        entityManager.persist(active2);
        entityManager.persist(pending);
        entityManager.flush();

        List<Employee> activeEmployees = employeeRepository.findByStatus(EmployeeStatus.ACTIVE);

        assertEquals(2, activeEmployees.size());
    }

    @Test
    void save_WithManager_CreatesEmployeeWithManagerFK() {
        LocalDateTime now = LocalDateTime.now();
        // Create manager user
        User manager = User.builder()
                .email("manager@test.com")
                .password("hashed")
                .firstName("Manager")
                .lastName("User")
                .role(UserRole.MANAGER)
                .active(true)
                .createdAt(now)
                .updatedAt(now)
                .build();
        entityManager.persist(manager);

        Employee employee = Employee.builder()
                .firstName("Report")
                .lastName("Employee")
                .email("report@test.com")
                .role("Developer")
                .status(EmployeeStatus.ACTIVE)
                .manager(manager)
                .createdAt(now)
                .updatedAt(now)
                .build();

        Employee saved = employeeRepository.save(employee);
        entityManager.flush();

        assertNotNull(saved.getManager());
        assertEquals(manager.getId(), saved.getManager().getId());
    }
}
