package com.company.employeelifecycle.repository;

import com.company.employeelifecycle.entity.WorkflowTemplate;
import com.company.employeelifecycle.enums.WorkflowType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Repository for WorkflowTemplate entity operations.
 */
@Repository
public interface WorkflowTemplateRepository extends JpaRepository<WorkflowTemplate, UUID> {

    /**
     * Find all active workflow templates by type.
     *
     * @param type the workflow type
     * @return List of active workflow templates with the given type
     */
    List<WorkflowTemplate> findByTypeAndActiveTrue(WorkflowType type);
}
