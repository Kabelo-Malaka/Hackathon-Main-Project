package com.company.employeelifecycle.repository;

import com.company.employeelifecycle.entity.WorkflowInstance;
import com.company.employeelifecycle.enums.WorkflowInstanceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Repository for WorkflowInstance entity operations.
 */
@Repository
public interface WorkflowInstanceRepository extends JpaRepository<WorkflowInstance, UUID> {

    /**
     * Find all workflow instances by status.
     *
     * @param status the workflow instance status
     * @return List of workflow instances with the given status
     */
    List<WorkflowInstance> findByStatus(WorkflowInstanceStatus status);

    /**
     * Find all workflow instances for a specific employee.
     *
     * @param employeeId the employee ID
     * @return List of workflow instances for the employee
     */
    List<WorkflowInstance> findByEmployeeId(UUID employeeId);
}
