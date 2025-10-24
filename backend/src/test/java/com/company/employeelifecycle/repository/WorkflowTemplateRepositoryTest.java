package com.company.employeelifecycle.repository;

import com.company.employeelifecycle.entity.WorkflowTemplate;
import com.company.employeelifecycle.enums.WorkflowType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class WorkflowTemplateRepositoryTest {

    @Autowired
    private WorkflowTemplateRepository workflowTemplateRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void save_CreatesWorkflowTemplate() {
        WorkflowTemplate template = WorkflowTemplate.builder()
                .name("Developer Onboarding")
                .type(WorkflowType.ONBOARDING)
                .version(1)
                .templateJson("{\"steps\":[]}")
                .active(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        WorkflowTemplate saved = workflowTemplateRepository.save(template);

        assertNotNull(saved.getId());
        assertEquals("Developer Onboarding", saved.getName());
        assertEquals(WorkflowType.ONBOARDING, saved.getType());
        assertTrue(saved.getActive());
    }

    @Test
    void findByTypeAndActiveTrue_ReturnsActiveTemplates() {
        LocalDateTime now = LocalDateTime.now();
        WorkflowTemplate active1 = WorkflowTemplate.builder()
                .name("Onboarding Template 1")
                .type(WorkflowType.ONBOARDING)
                .active(true)
                .createdAt(now)
                .updatedAt(now)
                .build();
        WorkflowTemplate active2 = WorkflowTemplate.builder()
                .name("Onboarding Template 2")
                .type(WorkflowType.ONBOARDING)
                .active(true)
                .createdAt(now)
                .updatedAt(now)
                .build();
        WorkflowTemplate inactive = WorkflowTemplate.builder()
                .name("Inactive Template")
                .type(WorkflowType.ONBOARDING)
                .active(false)
                .createdAt(now)
                .updatedAt(now)
                .build();
        WorkflowTemplate offboarding = WorkflowTemplate.builder()
                .name("Offboarding Template")
                .type(WorkflowType.OFFBOARDING)
                .active(true)
                .createdAt(now)
                .updatedAt(now)
                .build();

        entityManager.persist(active1);
        entityManager.persist(active2);
        entityManager.persist(inactive);
        entityManager.persist(offboarding);
        entityManager.flush();

        List<WorkflowTemplate> activeOnboarding = workflowTemplateRepository.findByTypeAndActiveTrue(WorkflowType.ONBOARDING);

        assertEquals(2, activeOnboarding.size());
    }
}
