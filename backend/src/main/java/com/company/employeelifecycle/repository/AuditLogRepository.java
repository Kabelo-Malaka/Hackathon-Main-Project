package com.company.employeelifecycle.repository;

import com.company.employeelifecycle.entity.AuditLog;
import com.company.employeelifecycle.enums.EntityType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Repository for AuditLog entity operations.
 */
@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, UUID> {

    /**
     * Find all audit logs for a specific entity.
     *
     * @param entityType the entity type
     * @param entityId   the entity ID
     * @return List of audit logs for the entity
     */
    List<AuditLog> findByEntityTypeAndEntityId(EntityType entityType, UUID entityId);

    /**
     * Find all audit logs by a specific user, ordered by timestamp descending.
     *
     * @param userId the user ID
     * @return List of audit logs by the user
     */
    List<AuditLog> findByUserIdOrderByTimestampDesc(UUID userId);
}
