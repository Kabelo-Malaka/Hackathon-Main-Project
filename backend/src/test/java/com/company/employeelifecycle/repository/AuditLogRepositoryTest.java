package com.company.employeelifecycle.repository;

import com.company.employeelifecycle.entity.AuditLog;
import com.company.employeelifecycle.entity.User;
import com.company.employeelifecycle.enums.AuditAction;
import com.company.employeelifecycle.enums.EntityType;
import com.company.employeelifecycle.enums.UserRole;
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
class AuditLogRepositoryTest {

    @Autowired
    private AuditLogRepository auditLogRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void save_CreatesAuditLog() {
        User user = createUser();
        UUID entityId = UUID.randomUUID();

        AuditLog auditLog = AuditLog.builder()
                .entityType(EntityType.WORKFLOW_INSTANCE)
                .entityId(entityId)
                .action(AuditAction.CREATED)
                .user(user)
                .changeDetails("{\"status\":\"CREATED\"}")
                .timestamp(LocalDateTime.now())
                .build();

        AuditLog saved = auditLogRepository.save(auditLog);

        assertNotNull(saved.getId());
        assertEquals(EntityType.WORKFLOW_INSTANCE, saved.getEntityType());
        assertEquals(AuditAction.CREATED, saved.getAction());
        assertNotNull(saved.getTimestamp());
    }

    @Test
    void findByEntityTypeAndEntityId_ReturnsAuditHistory() {
        User user = createUser();
        UUID entityId = UUID.randomUUID();

        LocalDateTime now = LocalDateTime.now();
        AuditLog created = AuditLog.builder()
                .entityType(EntityType.TASK)
                .entityId(entityId)
                .action(AuditAction.CREATED)
                .user(user)
                .timestamp(now)
                .build();
        AuditLog updated = AuditLog.builder()
                .entityType(EntityType.TASK)
                .entityId(entityId)
                .action(AuditAction.UPDATED)
                .user(user)
                .timestamp(now)
                .build();
        AuditLog otherEntity = AuditLog.builder()
                .entityType(EntityType.TASK)
                .entityId(UUID.randomUUID())
                .action(AuditAction.CREATED)
                .user(user)
                .timestamp(now)
                .build();

        entityManager.persist(created);
        entityManager.persist(updated);
        entityManager.persist(otherEntity);
        entityManager.flush();

        List<AuditLog> auditHistory = auditLogRepository.findByEntityTypeAndEntityId(EntityType.TASK, entityId);

        assertEquals(2, auditHistory.size());
    }

    @Test
    void findByUserIdOrderByTimestampDesc_ReturnsUserAuditLogs() {
        User user1 = createUser();
        User user2 = createUser();

        LocalDateTime now = LocalDateTime.now();
        AuditLog log1 = AuditLog.builder()
                .entityType(EntityType.EMPLOYEE)
                .entityId(UUID.randomUUID())
                .action(AuditAction.CREATED)
                .user(user1)
                .timestamp(now.minusMinutes(2))
                .build();
        AuditLog log2 = AuditLog.builder()
                .entityType(EntityType.EMPLOYEE)
                .entityId(UUID.randomUUID())
                .action(AuditAction.UPDATED)
                .user(user1)
                .timestamp(now)
                .build();
        AuditLog log3 = AuditLog.builder()
                .entityType(EntityType.EMPLOYEE)
                .entityId(UUID.randomUUID())
                .action(AuditAction.CREATED)
                .user(user2)
                .timestamp(now)
                .build();

        entityManager.persist(log1);
        entityManager.persist(log2);
        entityManager.persist(log3);
        entityManager.flush();

        List<AuditLog> user1Logs = auditLogRepository.findByUserIdOrderByTimestampDesc(user1.getId());

        assertEquals(2, user1Logs.size());
        // Verify ordering (most recent first) - timestamps should be descending
        assertTrue(user1Logs.get(0).getTimestamp().isAfter(user1Logs.get(1).getTimestamp()) ||
                   user1Logs.get(0).getTimestamp().equals(user1Logs.get(1).getTimestamp()));
    }

    private User createUser() {
        LocalDateTime now = LocalDateTime.now();
        User user = User.builder()
                .email("user" + UUID.randomUUID().toString() + "@test.com")
                .password("hashed")
                .firstName("Test")
                .lastName("User")
                .role(UserRole.SYSTEM_ADMIN)
                .active(true)
                .createdAt(now)
                .updatedAt(now)
                .build();
        return entityManager.persist(user);
    }
}
