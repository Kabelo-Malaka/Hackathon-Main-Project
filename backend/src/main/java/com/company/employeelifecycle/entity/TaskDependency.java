package com.company.employeelifecycle.entity;

import com.company.employeelifecycle.enums.DependencyType;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

/**
 * Task dependency entity representing prerequisite relationships between tasks.
 */
@Entity
@Table(name = "task_dependencies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskDependency {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;

    @ManyToOne
    @JoinColumn(name = "prerequisite_task_id", nullable = false)
    private Task prerequisiteTask;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DependencyType dependencyType;
}
