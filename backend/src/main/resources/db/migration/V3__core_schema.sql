-- Story 1.3: Core Workflow Data Models and Database Schema
-- Creates tables for employees, workflow templates, workflow instances, tasks, task dependencies, and audit logs

-- Create employees table
CREATE TABLE employees (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    role VARCHAR(255) NOT NULL,
    department VARCHAR(255),
    start_date DATE,
    manager_id UUID,
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Create workflow_templates table
CREATE TABLE workflow_templates (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) NOT NULL,
    type VARCHAR(50) NOT NULL,
    version INTEGER,
    template_json TEXT,
    active BOOLEAN NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Create workflow_instances table
CREATE TABLE workflow_instances (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    template_id UUID NOT NULL,
    employee_id UUID NOT NULL,
    initiated_by UUID NOT NULL,
    status VARCHAR(50) NOT NULL,
    current_step_index INTEGER,
    started_at TIMESTAMP,
    completed_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Create tasks table
CREATE TABLE tasks (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    workflow_instance_id UUID NOT NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    assigned_to UUID,
    task_type VARCHAR(50) NOT NULL,
    status VARCHAR(50) NOT NULL,
    due_date DATE,
    completed_by UUID,
    completed_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Create task_dependencies table
CREATE TABLE task_dependencies (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    task_id UUID NOT NULL,
    prerequisite_task_id UUID NOT NULL,
    dependency_type VARCHAR(50) NOT NULL
);

-- Create audit_logs table
CREATE TABLE audit_logs (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    entity_type VARCHAR(50) NOT NULL,
    entity_id UUID NOT NULL,
    action VARCHAR(50) NOT NULL,
    user_id UUID,
    change_details TEXT,
    timestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Add foreign key constraints
-- Employee foreign keys
ALTER TABLE employees
ADD CONSTRAINT fk_employee_manager
FOREIGN KEY (manager_id) REFERENCES users(id) ON DELETE RESTRICT;

-- WorkflowInstance foreign keys
ALTER TABLE workflow_instances
ADD CONSTRAINT fk_workflow_instance_template
FOREIGN KEY (template_id) REFERENCES workflow_templates(id) ON DELETE RESTRICT;

ALTER TABLE workflow_instances
ADD CONSTRAINT fk_workflow_instance_employee
FOREIGN KEY (employee_id) REFERENCES employees(id) ON DELETE CASCADE;

ALTER TABLE workflow_instances
ADD CONSTRAINT fk_workflow_instance_initiated_by
FOREIGN KEY (initiated_by) REFERENCES users(id) ON DELETE RESTRICT;

-- Task foreign keys
ALTER TABLE tasks
ADD CONSTRAINT fk_task_workflow_instance
FOREIGN KEY (workflow_instance_id) REFERENCES workflow_instances(id) ON DELETE CASCADE;

ALTER TABLE tasks
ADD CONSTRAINT fk_task_assigned_to
FOREIGN KEY (assigned_to) REFERENCES users(id) ON DELETE RESTRICT;

ALTER TABLE tasks
ADD CONSTRAINT fk_task_completed_by
FOREIGN KEY (completed_by) REFERENCES users(id) ON DELETE RESTRICT;

-- TaskDependency foreign keys
ALTER TABLE task_dependencies
ADD CONSTRAINT fk_task_dependency_task
FOREIGN KEY (task_id) REFERENCES tasks(id) ON DELETE CASCADE;

ALTER TABLE task_dependencies
ADD CONSTRAINT fk_task_dependency_prerequisite
FOREIGN KEY (prerequisite_task_id) REFERENCES tasks(id) ON DELETE CASCADE;

-- AuditLog foreign keys
ALTER TABLE audit_logs
ADD CONSTRAINT fk_audit_log_user
FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE RESTRICT;

-- Create indexes for frequently queried columns
CREATE INDEX idx_employees_status ON employees(status);
CREATE INDEX idx_employees_email ON employees(email);

CREATE INDEX idx_workflow_instances_status ON workflow_instances(status);
CREATE INDEX idx_workflow_instances_employee_id ON workflow_instances(employee_id);

CREATE INDEX idx_tasks_assigned_to ON tasks(assigned_to);
CREATE INDEX idx_tasks_status ON tasks(status);
CREATE INDEX idx_tasks_workflow_instance_id ON tasks(workflow_instance_id);

CREATE INDEX idx_task_dependencies_task_id ON task_dependencies(task_id);

CREATE INDEX idx_audit_logs_entity ON audit_logs(entity_type, entity_id);
CREATE INDEX idx_audit_logs_timestamp ON audit_logs(timestamp);
