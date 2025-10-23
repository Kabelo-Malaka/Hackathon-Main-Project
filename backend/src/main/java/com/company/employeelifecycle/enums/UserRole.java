package com.company.employeelifecycle.enums;

/**
 * User roles for role-based access control (RBAC).
 * Each role has specific permissions within the employee lifecycle system.
 */
public enum UserRole {
    /**
     * HR Administrator - Full access to workflow templates and HR operations
     */
    HR_ADMIN,

    /**
     * Manager - View team workflows and provide employee details
     */
    MANAGER,

    /**
     * Tech Support - Complete IT provisioning tasks
     */
    TECH_SUPPORT,

    /**
     * Finance - Complete finance-related tasks
     */
    FINANCE,

    /**
     * System Administrator - Full system access and user management
     */
    SYSTEM_ADMIN
}
