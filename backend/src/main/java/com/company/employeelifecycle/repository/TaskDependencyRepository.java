package com.company.employeelifecycle.repository;

import com.company.employeelifecycle.entity.TaskDependency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Repository for TaskDependency entity operations.
 */
@Repository
public interface TaskDependencyRepository extends JpaRepository<TaskDependency, UUID> {

    /**
     * Find all prerequisites for a specific task.
     *
     * @param taskId the task ID
     * @return List of task dependencies for the task
     */
    List<TaskDependency> findByTaskId(UUID taskId);

    /**
     * Find all tasks dependent on a specific prerequisite task.
     *
     * @param prerequisiteTaskId the prerequisite task ID
     * @return List of task dependencies with the given prerequisite
     */
    List<TaskDependency> findByPrerequisiteTaskId(UUID prerequisiteTaskId);
}
