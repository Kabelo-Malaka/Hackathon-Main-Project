package com.company.employeelifecycle.repository;

import com.company.employeelifecycle.entity.Task;
import com.company.employeelifecycle.enums.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Repository for Task entity operations.
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {

    /**
     * Find all tasks assigned to a specific user.
     *
     * @param userId the user ID
     * @return List of tasks assigned to the user
     */
    List<Task> findByAssignedToId(UUID userId);

    /**
     * Find all tasks for a specific workflow instance.
     *
     * @param workflowInstanceId the workflow instance ID
     * @return List of tasks for the workflow instance
     */
    List<Task> findByWorkflowInstanceId(UUID workflowInstanceId);

    /**
     * Find all tasks by status and assigned user.
     *
     * @param status the task status
     * @param userId the user ID
     * @return List of tasks matching the status and user
     */
    List<Task> findByStatusAndAssignedToId(TaskStatus status, UUID userId);
}
