package com.company.employeelifecycle.repository;

import com.company.employeelifecycle.entity.Employee;
import com.company.employeelifecycle.enums.EmployeeStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository for Employee entity operations.
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID> {

    /**
     * Find employee by email address.
     *
     * @param email the employee email
     * @return Optional containing the employee if found
     */
    Optional<Employee> findByEmail(String email);

    /**
     * Find all employees by status.
     *
     * @param status the employee status
     * @return List of employees with the given status
     */
    List<Employee> findByStatus(EmployeeStatus status);
}
