# Employee Lifecycle Management System Product Requirements Document (PRD)

## Goals and Background Context

### Goals

- Enable HR to initiate and track employee onboarding/offboarding processes in <5 minutes with complete visibility
- Eliminate orphaned accounts and uncollected equipment through automated offboarding mirroring of onboarding records
- Reduce average onboarding completion time by 40% (from 5 days to 3 days) through automated task routing
- Achieve 100% task verification compliance by making checklist completion mandatory before task closure
- Provide real-time visibility into employee transition status for all stakeholders (HR, managers, tech support, finance)
- Ensure security compliance with complete audit trails of all provisioning and deprovisioning actions
- Integrate seamlessly with existing Outlook email workflows to minimize change management friction

### Background Context

The Employee Lifecycle Management System addresses critical gaps in current manual, email-based employee onboarding and offboarding processes. Currently, HR sends documents via email that are manually passed between line managers, finance, and tech support, creating a chaotic process with no tracking, inconsistent execution, and significant security risks from orphaned accounts and missed equipment collection.

The proposed web-based Workflow Orchestration Platform automates task routing, enforces mandatory verification checklists, and maintains complete audit trails. A key innovation is the "offboarding mirror" concept where the system automatically generates offboarding checklists from onboarding records, ensuring perfect symmetry and eliminating security gaps. By integrating with Outlook email and providing real-time dashboards, the platform transforms employee transitions from disorganized, rushed processes into tracked, consistent, and secure workflows that ensure nothing falls through the cracks.

### Change Log

| Date | Version | Description | Author |
|------|---------|-------------|--------|
| 2025-10-16 | 1.0 | Initial PRD creation from Project Brief | Product Manager - John |

---

## Requirements

### Functional

**FR1:** The system shall provide pre-built workflow template functionality for both onboarding and offboarding processes with role-based task variations (e.g., Developer, Sales, Manager).

**FR2:** The system shall provide a drag-and-drop template designer that allows HR administrators to create, modify, and organize workflow templates without code changes.

**FR3:** The system shall automatically distribute tasks to appropriate stakeholders (HR, managers, finance, tech support) based on employee role, department, and workflow template configuration.

**FR4:** The system shall support parallel task execution where multiple departments can work simultaneously (e.g., finance and tech support tasks running in parallel).

**FR5:** The system shall support sequential task dependencies where tasks can be configured to wait for prerequisite task completion (e.g., manager approval before tech provisioning).

**FR6:** The system shall provide a real-time dashboard displaying all active employee transitions with visual pipeline representation and color-coded status indicators (not started, in progress, blocked, complete).

**FR7:** The system shall provide role-specific dashboard views allowing managers to see only their team's transitions while HR and admins see all transitions.

**FR8:** The system shall display overdue task alerts on the dashboard and calculate estimated completion dates based on current progress and historical data.

**FR9:** The system shall integrate with corporate Outlook email server via SMTP to send task assignment notifications with embedded web links to task forms.

**FR10:** The system shall send automated reminder emails for pending tasks with configurable cadence and escalation rules.

**FR11:** The system shall send completion confirmation emails to relevant stakeholders when tasks are marked complete.

**FR12:** The system shall generate dynamic checklists for tech support based on employee role requirements and manager-specified customizations.

**FR13:** The system shall enforce mandatory checkbox verification requiring all checklist items to be checked before a task can be marked complete.

**FR14:** The system shall automatically log all provisioned items (accounts, software, hardware) during onboarding for use in offboarding.

**FR15:** The system shall automatically generate offboarding checklists by mirroring the onboarding record, creating a reverse checklist of all items that need deprovisioning or collection.

**FR16:** The system shall require verification checkboxes for account deactivation and hardware collection during offboarding, preventing task completion until all items are checked.

**FR17:** The system shall maintain a complete audit trail recording who performed what action and when for all workflow activities.

**FR18:** The system shall allow HR administrators to add custom fields to workflow templates through a field builder interface.

**FR19:** The system shall automatically flow custom field values through to relevant downstream tasks based on configuration.

**FR20:** The system shall support conditional task logic with "if-then" rules (e.g., "If remote = yes, skip office desk assignment task").

**FR21:** The system shall dynamically adjust workflow task lists based on conditional logic evaluation at runtime.

**FR22:** The system shall allow users to reassign tasks to alternate stakeholders when the primary assignee is unavailable.

**FR23:** The system shall provide search and filter functionality on the dashboard to find specific employee transitions by name, date, status, or department.

**FR24:** The system shall generate basic reports showing process completion times, task SLA compliance, and verification rates.

**FR25:** The system shall implement role-based access control (RBAC) with distinct permissions for HR Admin, Manager, Tech Support, Finance, and System Administrator roles.

### Non Functional

**NFR1:** The system shall load web pages in less than 2 seconds under normal network conditions.

**NFR2:** The system shall refresh dashboard data in less than 1 second when users request updated status.

**NFR3:** The system shall support at least 100 concurrent users without performance degradation.

**NFR4:** The system shall deliver task assignment emails within 30 seconds of task creation.

**NFR5:** The system shall be accessible via modern web browsers (Chrome, Firefox, Edge, Safari - latest two versions).

**NFR6:** The system shall provide responsive web design that adapts to tablet screen sizes (mobile app not required for MVP).

**NFR7:** The system shall encrypt all data at rest using industry-standard encryption algorithms (AES-256).

**NFR8:** The system shall encrypt all data in transit using TLS 1.2 or higher.

**NFR9:** The system shall implement session timeout after 30 minutes of inactivity for security purposes.

**NFR10:** The system shall maintain audit logs that are immutable and tamper-proof for compliance purposes.

**NFR11:** The system shall provide database backup capability with point-in-time recovery support.

**NFR12:** The system shall achieve 99% uptime during business hours (8am-6pm local time).

**NFR13:** The system shall be deployable via Docker containers for consistent environment across development, testing, and production.

**NFR14:** The system shall follow RESTful API design principles for all backend services to enable future integrations.

**NFR15:** The system shall log all errors and exceptions with sufficient detail for debugging and monitoring.

**NFR16:** The system shall use PostgreSQL as the database system for all persistent data storage.

**NFR17:** The system shall be developed using React with TypeScript for the frontend and Spring Boot (Java) for the backend.

**NFR18:** The system shall implement a modular monolith architecture with clear service boundaries (WorkflowService, NotificationService, TemplateService, UserService, AuditService).

**NFR19:** The system shall support horizontal scaling by adding additional Docker container instances as load increases (post-MVP consideration).

**NFR20:** The system shall maintain GDPR compliance for employee data retention and provide data export/deletion capabilities upon request.

---

## User Interface Design Goals

### Overall UX Vision

The Employee Lifecycle Management System prioritizes **clarity, efficiency, and progressive disclosure** to serve users across varying technical skill levels. The interface should feel familiar and approachable, reducing training time while providing power users with shortcuts and advanced capabilities.

**Core UX Principles:**
- **Glanceable Status:** Users should understand system state within 3 seconds of viewing any screen
- **Minimal Clicks to Action:** Common tasks (checking status, completing checklists) achievable in 2-3 clicks
- **Error Prevention over Recovery:** Design prevents mistakes rather than relying on undo/retry
- **Context-Aware Guidance:** Interface adapts based on user role and current task state
- **Graceful Degradation:** Core functionality accessible even if JavaScript or real-time updates fail

The system should feel like a **collaborative workspace** where stakeholders see their specific responsibilities clearly while maintaining awareness of the broader process flow.

### Key Interaction Paradigms

**Dashboard-Centric Navigation:**
The dashboard serves as the primary hub where users land after login. All workflows radiate from this central point, with persistent navigation allowing quick return to dashboard from any screen.

**Task-Driven Workflow:**
Users interact primarily through **task cards** that represent their assigned work. Each card shows: employee name, task type, status, due date, and priority. Clicking a card opens the task detail view with embedded forms or checklists.

**Progressive Disclosure for Complexity:**
- Basic users (managers, tech support) see simplified interfaces with clear next actions
- Administrative users (HR admins) can expand sections to access template editing, custom fields, and conditional logic configuration
- Workflow templates start with simple drag-and-drop; advanced features hidden behind "Advanced Options" toggles

**Real-Time Status Updates:**
The dashboard uses color-coded visual indicators (green, yellow, red, gray) and auto-refreshes to show live progress. Notifications appear as non-intrusive toast messages in the corner rather than modal dialogs that block workflow.

**Checklist-First Verification:**
For tech support tasks, the checklist is the primary interface element. Large checkboxes, clear item descriptions, and visual progress bars (e.g., "7/9 items complete") make verification the natural path forward. The "Mark Complete" button remains disabled until all items checked.

**Email-to-Web Handoff:**
Users receive Outlook emails with action buttons or links. Clicking opens the web app directly to the relevant task with user context preserved. The web interface explicitly shows "You arrived here from your email notification" with option to return to email view.

### Core Screens and Views

**1. Login Screen**
Standard authentication with username/password. Company logo and branding. "Remember me" option. Password reset link.

**2. Main Dashboard**
Primary landing page showing all active employee transitions relevant to logged-in user. Includes:
- Visual pipeline (columns for "Not Started", "In Progress", "Completed")
- Drag-and-drop cards for each employee transition
- Filter/search bar at top
- Summary metrics (e.g., "5 tasks due today", "2 overdue")
- Quick action button: "Initiate New Onboarding" (HR only)

**3. Task Detail View**
Opened when clicking a task card from dashboard. Shows:
- Employee information summary (name, role, department, start date)
- Task description and instructions
- Embedded form or checklist specific to task type
- Comment thread for stakeholder communication
- Action buttons: "Save Draft", "Submit", "Reassign Task"
- Related tasks in workflow (previous/next context)

**4. Workflow Template Designer (Admin)**
Drag-and-drop canvas for creating/editing workflow templates:
- Left sidebar: template library and task blocks
- Center canvas: visual workflow with task nodes and connectors
- Right sidebar: selected task properties panel
- Custom field builder interface
- Conditional logic rule editor
- Template save/publish controls

**5. Checklist Completion Interface (Tech Support)**
Specialized view for equipment and access provisioning tasks:
- Large checkboxes with item descriptions
- Progress indicator (completed vs. total)
- Notes field for each checklist item
- Attached documentation or setup guides
- "Mark All Complete" button (disabled until 100% checked)

**6. Reports Screen**
Basic reporting interface with pre-built reports:
- Dropdown to select report type (completion times, SLA compliance, verification rates)
- Date range selector
- Export to PDF/CSV button
- Simple charts and tables displaying metrics

**7. Settings/Admin Screen**
Administrative functions:
- User management (add/remove users, assign roles)
- SMTP configuration for email integration
- Workflow template management (create, edit, archive)
- System audit log viewer
- Custom field library

**8. Employee Transition History View**
Detailed view showing complete timeline for a single employee:
- Chronological list of all completed tasks
- Audit trail with timestamps and actors
- Provisioned items during onboarding (used for offboarding)
- Comments and notes from all stakeholders

### Accessibility

**Target Level: WCAG AA Compliance**

The system will meet WCAG 2.1 Level AA standards, ensuring usability for users with disabilities:
- Keyboard navigation for all interactive elements
- Screen reader compatibility with semantic HTML and ARIA labels
- Sufficient color contrast ratios (4.5:1 for text)
- Form labels and error messages clearly associated with inputs
- No time-based actions that cannot be extended or disabled
- Resizable text up to 200% without loss of functionality

*Assumption: WCAG AAA not required for MVP as it's primarily internal tool for office workers, but architecture should allow future enhancement.*

### Branding

**Corporate-Neutral Design System**

For MVP, the system will use a clean, professional design that can be customized post-launch:
- Neutral color palette: Navy blue (primary), gray (secondary), green/red (status indicators)
- Modern sans-serif typography (e.g., Inter, Roboto, or system fonts)
- Subtle shadows and rounded corners for card-based layouts
- Company logo placement in header with configurable upload

*Assumption: No specific corporate branding requirements provided. Design will be professional but generic, allowing easy theme customization in Phase 2. If corporate style guide exists, please provide for integration.*

### Target Device and Platforms

**Web Responsive (Desktop-First, Tablet-Compatible)**

**Primary Target:** Desktop browsers (1920x1080, 1366x768 common resolutions)
- Full dashboard with multi-column layouts
- Rich drag-and-drop interactions for template designer
- Keyboard shortcuts for power users

**Secondary Target:** Tablets (iPad, Android tablets in landscape orientation)
- Responsive layout adapts to tablet screen sizes (768px+)
- Touch-optimized buttons and tap targets (minimum 44x44px)
- Dashboard columns collapse/stack appropriately
- Template designer available but simplified (less drag-and-drop precision)

**Not Supported for MVP:** Smartphones (320-414px width)
- Mobile phone access shows message: "For best experience, please use a desktop or tablet"
- Link provided to email notifications as alternative for on-the-go status checks

*Assumption: Primary users (HR, managers, tech support) work from office desktops or laptops. Tablet support enables managers to check status from meetings. Full mobile app deferred to Phase 2.*

---

## Technical Assumptions

### Repository Structure: Monorepo

The project shall use a **monorepo structure** containing both frontend and backend code in a single Git repository with separate directories.

**Rationale:**
- Simplifies dependency management and versioning - frontend and backend stay synchronized
- Enables atomic commits spanning both layers when features require coordinated changes
- Easier for small development team (2 developers) to navigate and maintain
- Reduces overhead of managing multiple repositories, CI/CD pipelines, and release coordination
- Supports modular monolith architecture where services are logically separated but physically co-located

**Repository Structure:**
```
/
├── frontend/          # React TypeScript application
├── backend/           # Spring Boot application
├── docker/            # Docker compose and container configurations
├── docs/              # Project documentation (PRD, architecture, etc.)
└── scripts/           # Build and deployment scripts
```

### Service Architecture

**Architecture Pattern: Modular Monolith**

The system shall be implemented as a **modular monolith** with clear service boundaries within a single deployable application.

**Core Service Modules:**
- **WorkflowService:** Manages workflow templates, instances, task creation, routing, and state transitions
- **NotificationService:** Handles email generation, SMTP integration, reminder scheduling, and notification tracking
- **TemplateService:** Manages workflow templates, custom fields, conditional logic rules, and template versioning
- **UserService:** Handles authentication, authorization, role-based access control, and user management
- **AuditService:** Records all system actions with immutable audit logs for compliance and troubleshooting

**Rationale:**
- **Simplicity for MVP:** Single deployment unit reduces operational complexity compared to microservices
- **Team Size:** Small team (1 backend, 1 frontend developer) can manage monolith more effectively than distributed services
- **Performance:** In-process service calls avoid network latency of microservices communication
- **Transaction Management:** Easier to maintain data consistency within single database transactions
- **Future Flexibility:** Clear module boundaries enable extraction to microservices later if scaling demands it
- **Development Velocity:** Faster iteration without inter-service contract negotiations

**Module Boundaries:**
- Each service module has its own package namespace
- Services communicate through well-defined interfaces (dependency injection)
- Shared data models isolated to dedicated domain package
- No circular dependencies between service modules

### Testing Requirements

**Testing Strategy: Unit + Integration Testing with Selective E2E**

**Required Test Coverage:**

**Unit Tests (Target: 80%+ code coverage)**
- All business logic in service modules
- Conditional logic evaluation (template rules)
- Workflow state machine transitions
- Utility functions and data transformations
- JUnit 5 for backend (Spring Boot)
- Jest for frontend (React/TypeScript)

**Integration Tests**
- Service layer integration with PostgreSQL database
- SMTP email sending (with mock server for CI/CD)
- REST API endpoint testing (Spring MockMvc)
- React component integration tests with React Testing Library
- Authentication and authorization flows

**End-to-End Tests (Critical User Journeys Only)**
- HR initiates onboarding → Manager completes form → Tech support checks off items → Workflow completes
- Offboarding mirrors onboarding record
- Dashboard displays real-time status updates
- Playwright or Cypress for browser automation (to be determined by dev team)

**Manual Testing**
- Email rendering across Outlook clients (desktop, web, mobile)
- Responsive design on various screen sizes and browsers
- Accessibility testing with screen readers (NVDA/JAWS)
- Cross-browser compatibility (Chrome, Firefox, Edge, Safari)

**Test Data Management:**
- Seed scripts for development database with sample workflows
- Test fixtures for repeatable integration tests
- Anonymized production-like data for UAT environment

**Rationale:**
- Unit + integration tests provide high confidence with manageable maintenance burden
- E2E tests expensive to maintain - limit to critical happy paths
- Manual testing necessary for subjective UX and email rendering validation
- 80% coverage target balances thoroughness with pragmatism (not chasing 100%)

### Additional Technical Assumptions and Requests

**Frontend Technology Stack:**
- **Framework:** React 18+ with TypeScript 5+
- **State Management:** React Context API for simple state; consider Redux Toolkit if complexity warrants
- **UI Component Library:** Material-UI (MUI) or Ant Design for pre-built components (drag-and-drop, data tables, forms)
- **Styling:** CSS Modules or Styled Components for component-scoped styles
- **HTTP Client:** Axios for REST API calls with interceptors for auth tokens
- **Build Tool:** Vite for fast development server and optimized production builds
- **Code Quality:** ESLint + Prettier for code formatting; Husky for pre-commit hooks

**Backend Technology Stack:**
- **Framework:** Spring Boot 3.x with Java 17+
- **Dependency Injection:** Spring Framework built-in DI container
- **Data Access:** Spring Data JPA with Hibernate for ORM
- **Security:** Spring Security for authentication, authorization, and session management
- **Email:** JavaMail API with Spring Mail for SMTP integration
- **Validation:** Jakarta Bean Validation (JSR 380) for input validation
- **API Documentation:** Springdoc OpenAPI (Swagger) for REST API documentation
- **Logging:** SLF4J with Logback for structured logging
- **Build Tool:** Maven or Gradle (team preference; Maven recommended for consistency)

**Database:**
- **RDBMS:** PostgreSQL 15+
- **Schema Management:** Flyway or Liquibase for database migrations (versioned schema changes)
- **Connection Pooling:** HikariCP (Spring Boot default)
- **Backup Strategy:** PostgreSQL pg_dump for backups; point-in-time recovery enabled

**Deployment & Infrastructure:**
- **Containerization:** Docker for consistent environments across dev/test/prod
- **Orchestration:** Docker Compose for local development; production deployment TBD (bare Docker, Kubernetes, or platform-specific)
- **Environment Configuration:** Environment variables for secrets and configuration (no hardcoded credentials)
- **CI/CD:** GitHub Actions, GitLab CI, or Jenkins for automated testing and deployment (to be determined based on organization infrastructure)
- **Monitoring:** Basic logging to stdout (captured by Docker); consider Prometheus + Grafana for production metrics (post-MVP)

**Security:**
- **Authentication:** Session-based authentication with HTTP-only cookies (no JWT for MVP simplicity)
- **Password Storage:** BCrypt hashing with salt (Spring Security default)
- **HTTPS:** TLS 1.2+ required for production deployment
- **CSRF Protection:** Enabled for all state-changing operations
- **SQL Injection Prevention:** Parameterized queries via JPA (no raw SQL concatenation)
- **XSS Prevention:** React's JSX escaping by default; Content Security Policy headers

**Integration Requirements:**
- **SMTP Server:** Corporate Outlook/Exchange SMTP server (host, port, credentials to be provided by IT)
- **SMTP Settings:** TLS encryption, authentication required
- **Email Sending Rate:** Respect any rate limits imposed by corporate mail server (to be clarified with IT)
- **Email Templates:** HTML email templates with fallback plain text versions
- **Deep Linking:** Email links include authentication tokens or session handles to route users to specific tasks

**Development Environment:**
- **IDE:** IntelliJ IDEA (recommended) or VS Code
- **Database:** PostgreSQL Docker container for local development
- **SMTP:** MailHog or similar SMTP mock server for local email testing
- **Browser DevTools:** Chrome DevTools for frontend debugging
- **API Testing:** Postman or Insomnia for manual REST API testing

**Code Quality & Standards:**
- **Naming Conventions:** Java standard (camelCase methods, PascalCase classes); JavaScript/TypeScript standard
- **Code Reviews:** Pull request reviews required before merging to main branch
- **Branch Strategy:** Git Flow or GitHub Flow (feature branches, main/develop branches)
- **Commit Messages:** Conventional Commits format for structured commit history
- **Documentation:** Inline code comments for complex logic; README files for setup instructions

**Performance Targets:**
- **API Response Time:** 95th percentile < 500ms for standard CRUD operations
- **Database Query Optimization:** Indexes on foreign keys and frequently queried columns
- **Pagination:** All list endpoints support pagination (page size default: 20, max: 100)
- **Caching:** Consider Redis for session storage or frequently accessed data (post-MVP if needed)

**Assumptions:**
- Development team has access to corporate Outlook SMTP server for integration testing
- PostgreSQL hosting environment supports required performance levels (100 concurrent connections)
- Docker infrastructure available in production environment or can be provisioned
- No legacy system integration required for MVP (all data manually entered)
- Corporate firewall allows outbound SMTP connections from application server

**Open Technical Questions:**
- What is the corporate SMTP server hostname and port?
- Are there organizational standards for logging, monitoring, or APM tools we must use?
- Is there an existing identity provider (LDAP, Active Directory) we should integrate with (post-MVP)?
- What is the production hosting environment (cloud provider, on-premise, hybrid)?
- Are there specific compliance requirements (SOC 2, HIPAA) requiring additional security controls?

---

## Epic List

### Epic 1: Foundation & Basic Workflow Capability
Establish project infrastructure, authentication, and core workflow engine to enable basic onboarding workflow execution from initiation through task completion.

### Epic 2: Dashboard, Real-Time Tracking & Email Notifications
Build the real-time tracking dashboard with task assignment, status visualization, and Outlook email integration to provide visibility and task routing for all stakeholders.

### Epic 3: Checklist Verification & Automated Offboarding Mirror
Implement mandatory checklist verification for tech support and the automated offboarding mirror that generates deprovisioning tasks from onboarding records, delivering the security and compliance innovation.

### Epic 4: Template Customization & Production Readiness
Enable HR administrators to customize workflow templates through drag-and-drop designer, custom fields, and conditional logic, plus deliver reporting, audit logs, and production deployment preparation.

---

## Epic 1: Foundation & Basic Workflow Capability

**Epic Goal:**

Establish the complete technical foundation for the Employee Lifecycle Management System including project infrastructure, authentication, database schema, and core workflow engine. By the end of this epic, HR users will be able to initiate a basic onboarding workflow for a Developer role, tasks will route to appropriate stakeholders (manager, tech support), and stakeholders will be able to view and complete their assigned tasks, demonstrating end-to-end workflow execution. This proves the fundamental architecture and validates that the modular monolith design supports the required functionality.

---

### Story 1.1: Project Setup and Infrastructure

As a **Developer**,
I want the project repository, build configuration, and local development environment established,
so that the team can begin coding with consistent tooling and all services running locally.

**Acceptance Criteria:**

1. Monorepo Git repository created with `/frontend`, `/backend`, `/docker`, `/docs`, and `/scripts` directories
2. Backend Spring Boot 3.x project initialized with Maven/Gradle, Java 17+, and configured with Spring Web, Spring Data JPA, Spring Security, and Spring Mail dependencies
3. Frontend React 18+ project initialized with TypeScript 5+, Vite build tool, and Axios HTTP client configured
4. Docker Compose configuration created that starts PostgreSQL 15+ container with exposed port and persisted volume
5. Database connection configured in Spring Boot with HikariCP connection pool and Flyway migration tool setup
6. Backend application successfully connects to PostgreSQL and creates initial schema via Flyway migration (V1__init.sql with empty placeholder)
7. Frontend Vite dev server runs on localhost:5173 and successfully proxies API calls to backend on localhost:8080
8. Backend Spring Boot application runs on localhost:8080 and serves health check endpoint GET /api/health returning {"status": "UP"}
9. README.md in root directory documents setup instructions: prerequisites, how to start Docker Compose, how to run backend, how to run frontend, and how to verify everything works
10. .gitignore configured to exclude node_modules, target/build directories, IDE files, and environment configuration files

---

### Story 1.2: User Authentication and Role-Based Access Control

As a **System Administrator**,
I want users to authenticate with username/password and have role-based access control,
so that HR, Managers, Tech Support, and Finance can only access features appropriate to their role.

**Acceptance Criteria:**

1. User entity created with fields: id, username, passwordHash, email, firstName, lastName, role (enum: HR_ADMIN, MANAGER, TECH_SUPPORT, FINANCE, SYSTEM_ADMIN), active (boolean), createdAt, updatedAt
2. Spring Security configured with session-based authentication using HTTP-only cookies (not JWT)
3. Password hashing implemented using BCrypt with Spring Security defaults
4. Login page UI created in React with username and password fields, "Remember me" checkbox, and "Login" button
5. POST /api/auth/login endpoint accepts {username, password} and returns user profile with role on successful authentication, creates server-side session
6. GET /api/auth/me endpoint returns currently authenticated user profile or 401 Unauthorized if not logged in
7. POST /api/auth/logout endpoint terminates session and clears authentication cookie
8. Session timeout configured to 30 minutes of inactivity (Spring Security session management)
9. Frontend React app implements authentication context (AuthContext) that stores current user and provides login/logout functions
10. Protected route wrapper component created that redirects unauthenticated users to login page
11. Database seed script (data.sql) creates test users for each role: hr@test.com (HR_ADMIN), manager@test.com (MANAGER), tech@test.com (TECH_SUPPORT), finance@test.com (FINANCE) with password "password123"
12. Manual test confirms: user can log in, session persists across browser refresh, user can log out, session expires after 30 minutes

---

### Story 1.3: Core Workflow Data Models and Database Schema

As a **Developer**,
I want the database schema and JPA entities defined for workflows, tasks, and employees,
so that the backend can persist and query workflow execution data.

**Acceptance Criteria:**

1. Employee entity created with fields: id, firstName, lastName, email, role (job role, not system role), department, startDate, managerId (FK to User), status (enum: PENDING, ACTIVE, OFFBOARDING, DEPARTED), createdAt, updatedAt
2. WorkflowTemplate entity created with fields: id, name, type (enum: ONBOARDING, OFFBOARDING), version, templateJson (JSON column storing template structure), active, createdAt, updatedAt
3. WorkflowInstance entity created with fields: id, templateId (FK), employeeId (FK), initiatedBy (FK to User), status (enum: NOT_STARTED, IN_PROGRESS, COMPLETED, CANCELLED), currentStepIndex, startedAt, completedAt, createdAt, updatedAt
4. Task entity created with fields: id, workflowInstanceId (FK), title, description, assignedTo (FK to User), taskType (enum: FORM_COMPLETION, CHECKLIST, APPROVAL), status (enum: NOT_STARTED, IN_PROGRESS, COMPLETED), dueDate, completedBy (FK to User), completedAt, createdAt, updatedAt
5. TaskDependency entity created to store prerequisites with fields: id, taskId (FK), prerequisiteTaskId (FK), dependencyType (enum: SEQUENTIAL, PARALLEL)
6. AuditLog entity created with fields: id, entityType, entityId, action, userId (FK to User), changeDetails (JSON column), timestamp
7. Flyway migration (V2__core_schema.sql) creates all tables with appropriate indexes on foreign keys and frequently queried columns (e.g., workflowInstance.status, task.assignedTo, task.status)
8. JPA repositories created for all entities with basic CRUD operations
9. Database constraints enforced: NOT NULL on required fields, foreign key constraints with appropriate CASCADE/RESTRICT rules
10. Manual test using database client confirms: tables exist, can insert sample data, foreign key constraints work correctly

---

### Story 1.4: Basic Workflow Template - Hardcoded Developer Onboarding

As a **System Administrator**,
I want a hardcoded Developer onboarding workflow template seeded in the database,
so that HR can use a predefined template without needing template creation UI in this epic.

**Acceptance Criteria:**

1. Flyway data migration (V3__seed_developer_template.sql) inserts a WorkflowTemplate record with name="Developer Onboarding", type=ONBOARDING, active=true
2. Template JSON structure defines 4 tasks in sequence:
   - Task 1: "Manager Completes Employee Details" (assignedToRole: MANAGER, type: FORM_COMPLETION)
   - Task 2: "Finance Sets Up Payroll" (assignedToRole: FINANCE, type: FORM_COMPLETION, dependency: Task 1)
   - Task 3: "Tech Support Provisions Accounts and Equipment" (assignedToRole: TECH_SUPPORT, type: CHECKLIST, dependency: Task 1)
   - Task 4: "HR Confirms Onboarding Complete" (assignedToRole: HR_ADMIN, type: APPROVAL, dependencies: Task 2 AND Task 3)
3. Template JSON schema documented in docs/workflow-template-schema.md showing structure for tasks, dependencies, assignedToRole, taskType
4. WorkflowService.createWorkflowFromTemplate(templateId, employeeId) method implemented that reads template JSON and creates WorkflowInstance + Task records
5. Task assignment logic resolves assignedToRole to actual User based on employee context (e.g., employee.managerId for MANAGER role, any user with TECH_SUPPORT role for tech tasks)
6. Unit tests verify template JSON parsing and WorkflowInstance + Task creation from template
7. Manual test: call createWorkflowFromTemplate with test employee, verify WorkflowInstance and 4 Task records created in database with correct dependencies

---

### Story 1.5: Initiate Onboarding Workflow (HR Function)

As an **HR Administrator**,
I want to initiate a new employee onboarding workflow by entering employee details,
so that tasks are automatically created and routed to the appropriate stakeholders.

**Acceptance Criteria:**

1. React component "InitiateOnboardingForm" created with fields: firstName, lastName, email, jobRole (dropdown with Developer/Sales/Manager), department, startDate (date picker), manager (dropdown populated from users with MANAGER role)
2. Form validation: all fields required, email must be valid format, startDate cannot be in the past
3. POST /api/workflows/initiate endpoint accepts {employeeData, templateId} and returns created WorkflowInstance with ID
4. Backend creates Employee record with status=PENDING
5. Backend creates WorkflowInstance from specified template (Developer Onboarding template ID)
6. Backend creates Task records for all tasks in template, assigns tasks to appropriate users based on role and employee context
7. Backend logs action to AuditLog: {entityType: "WorkflowInstance", action: "INITIATED", userId: currentUser.id}
8. GET /api/workflows/{instanceId} endpoint returns WorkflowInstance with embedded employee data and list of tasks
9. Frontend displays success message after initiation: "Onboarding workflow initiated for [Employee Name]. Workflow ID: [ID]"
10. Error handling: if manager not found, employee email already exists, or template not active, return appropriate error message
11. Integration test: HR user logs in, submits form, verifies Employee, WorkflowInstance, and 4 Task records created in database

---

### Story 1.6: View Assigned Tasks (All Stakeholder Roles)

As a **Manager, Tech Support, Finance, or HR user**,
I want to see a list of tasks assigned to me,
so that I know what onboarding actions I need to complete.

**Acceptance Criteria:**

1. React component "TaskList" created displaying table with columns: Employee Name, Task Title, Due Date, Status, Actions
2. GET /api/tasks/my-tasks endpoint returns all tasks where assignedTo = currentUser.id, ordered by dueDate ASC, status ASC (NOT_STARTED first)
3. Each task row includes: employee.firstName + employee.lastName, task.title, task.dueDate (formatted as MM/DD/YYYY), status badge (color-coded: gray=NOT_STARTED, yellow=IN_PROGRESS, green=COMPLETED)
4. "View Details" button in Actions column navigates to /tasks/{taskId} route
5. Empty state message displayed if no tasks assigned: "You have no assigned tasks at this time."
6. Status badge colors: gray for NOT_STARTED, yellow for IN_PROGRESS, green for COMPLETED
7. Component fetches data on mount and displays loading spinner while fetching
8. Error handling: if API call fails, display error message "Unable to load tasks. Please try again."
9. Page title displayed: "My Assigned Tasks"
10. Integration test: Tech Support user logs in, verifies they see Task 3 ("Tech Support Provisions Accounts and Equipment") for the employee created in Story 1.5

---

### Story 1.7: Complete Form-Based Task (Manager and Finance)

As a **Manager or Finance user**,
I want to complete my assigned form-based task by filling out required information,
so that the workflow can proceed to dependent tasks.

**Acceptance Criteria:**

1. React component "TaskDetailView" created with layout: Employee Info section (name, role, department, start date), Task Info section (title, description, status, due date), Task Form section, Action Buttons
2. GET /api/tasks/{taskId} endpoint returns task details with embedded employee data
3. For FORM_COMPLETION task type, dynamic form rendered based on task configuration:
   - Manager task form includes: workLocation (text), additionalSoftwareNeeds (textarea), teamAssignment (text), deskNumber (text)
   - Finance task form includes: payrollId (text), bankAccountVerified (checkbox), benefitsEnrollmentComplete (checkbox)
4. Form validation: all fields required except additionalSoftwareNeeds (optional)
5. "Save Draft" button calls PUT /api/tasks/{taskId}/draft with form data, updates task but keeps status=IN_PROGRESS
6. "Submit Task" button calls PUT /api/tasks/{taskId}/complete with form data, validates all required fields, updates task status=COMPLETED, sets completedAt=now, completedBy=currentUser
7. Task cannot be submitted if status is already COMPLETED (button disabled, message displayed)
8. Upon successful submission, display success message and navigate back to "My Assigned Tasks" list
9. Backend WorkflowService checks if task completion triggers dependent tasks: if all prerequisites of dependent task are complete, update dependent task status from NOT_STARTED to IN_PROGRESS
10. Backend logs action to AuditLog: {entityType: "Task", entityId: taskId, action: "COMPLETED", userId: currentUser.id}
11. Integration test: Manager completes Task 1 with form data, verifies task status=COMPLETED, verifies Task 2 (Finance) and Task 3 (Tech Support) status changed to IN_PROGRESS (both depend on Task 1)

---

### Story 1.8: Workflow State Management and Completion

As a **System**,
I want the workflow to automatically track overall status and complete when all tasks are done,
so that stakeholders know when onboarding is fully complete without manual tracking.

**Acceptance Criteria:**

1. WorkflowService.checkAndUpdateWorkflowStatus(workflowInstanceId) method implemented that evaluates all tasks in workflow
2. If all tasks have status=COMPLETED, update WorkflowInstance status=COMPLETED and set completedAt=now
3. If any task has status=IN_PROGRESS, update WorkflowInstance status=IN_PROGRESS
4. If all tasks are NOT_STARTED, WorkflowInstance status remains NOT_STARTED
5. WorkflowService.checkAndUpdateWorkflowStatus() called automatically after any task status change (triggered by task completion endpoint)
6. GET /api/workflows/{instanceId}/status endpoint returns workflow status with task completion summary: {status, totalTasks, completedTasks, completionPercentage}
7. Backend logs to AuditLog when workflow status changes to COMPLETED: {entityType: "WorkflowInstance", action: "COMPLETED"}
8. Employee.status updated from PENDING to ACTIVE when onboarding WorkflowInstance completes
9. Unit test: given workflow with 4 tasks where 3 are COMPLETED and 1 is IN_PROGRESS, checkAndUpdateWorkflowStatus() keeps workflow status=IN_PROGRESS
10. Unit test: given workflow with 4 tasks all COMPLETED, checkAndUpdateWorkflowStatus() updates workflow status=COMPLETED and employee status=ACTIVE
11. Integration test: complete all 4 tasks in Developer onboarding workflow sequentially (Task 1 → Task 2 & Task 3 → Task 4), verify workflow status=COMPLETED and employee status=ACTIVE

---

## Epic 2: Dashboard, Real-Time Tracking & Email Notifications

**Epic Goal:**

Transform the basic task list into a powerful real-time tracking dashboard that provides visibility into all employee transitions across the organization. Implement Outlook email integration so that task assignments, reminders, and completions trigger automated notifications, reducing manual follow-ups. By the end of this epic, HR and managers will have a visual pipeline showing exactly where each onboarding is in the process, what's blocked, and what's overdue. Email notifications will route tasks to stakeholders seamlessly, and the dashboard will auto-refresh to show live progress, addressing the critical "no visibility" pain point identified in the Project Brief.

---

### Story 2.1: Dashboard Layout and Core Navigation

As a **HR Administrator, Manager, or Stakeholder**,
I want a centralized dashboard homepage with clear navigation,
so that I can quickly access all employee transition tracking features from one place.

**Acceptance Criteria:**

1. React component "Dashboard" created as the main landing page after login, accessible at route `/dashboard`
2. Top navigation bar includes: application logo/title ("Employee Lifecycle Management"), current user name, role badge, logout button
3. Left sidebar navigation (collapsible) includes menu items: "Dashboard" (active), "My Tasks", "Reports" (disabled/grayed for Epic 2), "Settings" (disabled/grayed for Epic 2)
4. Main content area contains dashboard title "Employee Transitions" and placeholder for workflow cards (populated in Story 2.2)
5. Dashboard summary metrics section displays: "Active Onboardings: [count]", "Tasks Due Today: [count]", "Overdue Tasks: [count]" (values fetched from API)
6. GET /api/dashboard/summary endpoint returns {activeWorkflows, tasksDueToday, overdueTasks} counts
7. "Initiate New Onboarding" button prominently displayed in top-right corner (visible only to HR_ADMIN role)
8. Clicking "Initiate New Onboarding" navigates to `/onboarding/initiate` (form from Story 1.5)
9. Responsive layout adapts to tablet screen sizes (sidebar collapses to hamburger menu on <768px width)
10. Loading state displayed while fetching summary metrics
11. Integration test: HR user logs in, lands on dashboard, sees correct summary counts based on existing workflows in database

---

### Story 2.2: Visual Workflow Pipeline with Status Columns

As a **HR Administrator or Manager**,
I want to see all employee transitions organized in a visual pipeline by status,
so that I can quickly identify which onboardings are in progress, blocked, or complete.

**Acceptance Criteria:**

1. Dashboard main content area displays three columns: "Not Started", "In Progress", "Completed"
2. GET /api/workflows endpoint returns all WorkflowInstance records with embedded employee data, ordered by startedAt DESC (most recent first)
3. Each workflow displayed as a "WorkflowCard" component showing: employee name, job role, department, start date, progress indicator (e.g., "3/4 tasks complete"), status badge
4. WorkflowCard color-coded by status: gray border for NOT_STARTED, blue border for IN_PROGRESS, green border for COMPLETED
5. Cards automatically placed in correct column based on WorkflowInstance.status
6. Progress indicator calculated from task completion: displays "completedTasks / totalTasks" with visual progress bar
7. Each WorkflowCard clickable: clicking navigates to `/workflows/{instanceId}` detail view
8. Empty column message: "No workflows in this status" displayed if column has zero cards
9. Column headers display count: "In Progress (5)", "Completed (12)", etc.
10. Cards within each column sorted by startedAt DESC (newest first)
11. Integration test: Create 3 workflows (1 NOT_STARTED, 1 IN_PROGRESS with 2/4 tasks done, 1 COMPLETED), verify dashboard displays cards in correct columns with accurate progress indicators

---

### Story 2.3: Workflow Detail View with Task Timeline

As a **HR Administrator or Manager**,
I want to view detailed information about a specific employee transition including all tasks and their status,
so that I can understand exactly where the workflow is and identify any blockers.

**Acceptance Criteria:**

1. React component "WorkflowDetailView" created accessible at route `/workflows/{instanceId}`
2. GET /api/workflows/{instanceId} endpoint returns WorkflowInstance with embedded employee data and full list of tasks with assignee details
3. Page layout includes three sections: Employee Information, Workflow Progress, Task Timeline
4. Employee Information section displays: name, email, job role, department, start date, manager name, current employment status
5. Workflow Progress section displays: overall status badge, progress percentage, started date, completed date (if applicable), "Initiated by [user name]"
6. Task Timeline section displays chronological list of all tasks with: task title, assigned to (user name), status, due date, completed by (user name) and completed date (if completed)
7. Task status icons: gray circle for NOT_STARTED, blue spinner for IN_PROGRESS, green checkmark for COMPLETED
8. Visual connector lines between tasks showing dependencies (simple vertical timeline for Epic 2; detailed dependency graph deferred)
9. "Back to Dashboard" button returns to `/dashboard`
10. If user doesn't have permission to view workflow (e.g., Manager viewing another manager's team), display 403 Forbidden message
11. Loading state while fetching workflow details
12. Integration test: HR user views WorkflowDetailView for in-progress workflow, sees all 4 tasks with correct statuses and assignees

---

### Story 2.4: Filter and Search Functionality

As a **HR Administrator or Manager**,
I want to filter and search employee transitions on the dashboard,
so that I can quickly find specific employees or focus on workflows in a certain state.

**Acceptance Criteria:**

1. Search bar added to top of dashboard above pipeline columns with placeholder text "Search by employee name, email, or department..."
2. Search input triggers filter as user types (debounced by 300ms to avoid excessive API calls)
3. GET /api/workflows endpoint supports query parameters: `?search={text}&status={status}&department={dept}`
4. Search parameter filters workflows where employee name, email, or department contains search text (case-insensitive)
5. Filter dropdown added next to search bar with options: "All Statuses", "Not Started", "In Progress", "Completed"
6. Selecting filter updates URL query string (?status=in_progress) and fetches filtered workflows
7. Department filter dropdown populated dynamically from existing employee departments in database
8. "Clear Filters" button displayed when any filter is active, clicking resets all filters and shows all workflows
9. Filtered results count displayed: "Showing 8 of 25 workflows" when filters active
10. If search/filter returns zero results, display message: "No workflows match your filters. Try adjusting your search criteria."
11. Integration test: Enter search text "John", verify only workflows for employees named John displayed; select "In Progress" filter, verify only in-progress workflows shown

---

### Story 2.5: Role-Specific Dashboard Views and Permissions

As a **Manager**,
I want to see only employee transitions for my team members on my dashboard,
so that I'm not overwhelmed with irrelevant workflows and can focus on my responsibilities.

**Acceptance Criteria:**

1. GET /api/workflows endpoint applies role-based filtering:
   - HR_ADMIN and SYSTEM_ADMIN: see all workflows
   - MANAGER: see only workflows where employee.managerId = currentUser.id
   - TECH_SUPPORT and FINANCE: see all workflows (need visibility to plan workload)
2. Dashboard summary metrics filtered by same role-based rules (Manager sees counts only for their team)
3. "My Team's Onboardings" label displayed on dashboard for Manager role instead of "All Employee Transitions"
4. Manager role cannot access "Initiate New Onboarding" button (only HR can initiate)
5. WorkflowDetailView enforces same permissions: Manager can only view workflows for their team members
6. Attempting to access restricted workflow via direct URL returns 403 Forbidden with message "You don't have permission to view this workflow"
7. Unit test: verify role-based filtering logic for each role type
8. Integration test: Manager user logs in, initiates workflow for employee managed by different manager, Manager user does NOT see that workflow on dashboard

---

### Story 2.6: SMTP Email Configuration and Integration

As a **System Administrator**,
I want the system to connect to the corporate Outlook SMTP server,
so that automated email notifications can be sent to stakeholders.

**Acceptance Criteria:**

1. Spring Boot application.properties configured with SMTP settings: `spring.mail.host`, `spring.mail.port`, `spring.mail.username`, `spring.mail.password`, `spring.mail.properties.mail.smtp.auth=true`, `spring.mail.properties.mail.smtp.starttls.enable=true`
2. SMTP credentials stored in environment variables (not hardcoded): ${SMTP_HOST}, ${SMTP_PORT}, ${SMTP_USERNAME}, ${SMTP_PASSWORD}
3. NotificationService class created with method sendEmail(toEmail, subject, htmlBody, plainTextBody)
4. NotificationService uses Spring JavaMailSender to send emails via configured SMTP server
5. Email template created for testing: simple HTML template with subject "Test Email from ELMS" and body "This is a test email to verify SMTP integration."
6. GET /api/admin/test-email?recipientEmail={email} endpoint (SYSTEM_ADMIN only) sends test email to specified address
7. Email sending is asynchronous (uses @Async to avoid blocking request threads)
8. If email sending fails, error logged with full exception details but doesn't crash application
9. For local development, MailHog Docker container added to docker-compose.yml for SMTP testing (no real emails sent locally)
10. README updated with instructions for configuring SMTP settings and using MailHog for local testing
11. Manual test: System Admin calls test-email endpoint, verifies email received in MailHog inbox (local) or actual Outlook inbox (production)

---

### Story 2.7: Task Assignment Email Notifications

As a **Manager, Tech Support, or Finance user**,
I want to receive an email notification when a task is assigned to me,
so that I'm immediately aware of my responsibilities without having to check the system constantly.

**Acceptance Criteria:**

1. HTML email template created for task assignment with sections: greeting, employee name, task title, task description, due date, "View Task" button (deep link to task detail page)
2. Plain text email fallback template created with same information (for email clients that don't support HTML)
3. NotificationService.sendTaskAssignmentEmail(task, assignee) method implemented
4. When task status changes from NOT_STARTED to IN_PROGRESS (dependencies satisfied), send task assignment email to assignedTo user
5. Email subject line: "New Task Assigned: [Task Title] for [Employee Name]"
6. "View Task" button links to: `https://{appDomain}/tasks/{taskId}?token={authToken}`
7. Authentication token generated for email link that expires in 7 days and allows one-time task access without login (or redirects to login if expired)
8. Email includes footer with system name and "Do not reply to this email" notice
9. Email sending integrated into WorkflowService: after task assignment, call NotificationService asynchronously
10. NotificationLog entity created to track sent emails with fields: id, recipientEmail, subject, sentAt, emailType (enum: TASK_ASSIGNMENT, REMINDER, COMPLETION), taskId (FK), success (boolean)
11. Integration test: Complete Manager task (Task 1), verify Finance and Tech Support tasks transition to IN_PROGRESS, verify 2 emails sent (checked in NotificationLog table)

---

### Story 2.8: Due Date Calculation and Overdue Task Alerts

As a **HR Administrator**,
I want tasks to have automatically calculated due dates and overdue alerts,
so that I can proactively follow up on delayed tasks before they impact the employee start date.

**Acceptance Criteria:**

1. WorkflowTemplate JSON schema extended to include `dueInDays` field for each task (e.g., Task 1 due in 2 days, Task 2 due in 1 day from prerequisite completion)
2. When task is created from template, calculate dueDate based on: workflow startDate + task.dueInDays
3. For dependent tasks, dueDate calculated from: prerequisite task completedAt + task.dueInDays (updates when prerequisite completes)
4. Task entity dueDate field populated during workflow instantiation
5. Dashboard WorkflowCard displays red "overdue" badge if any task in workflow has status != COMPLETED and dueDate < today
6. Dashboard summary metric "Overdue Tasks" counts tasks where status IN (NOT_STARTED, IN_PROGRESS) and dueDate < now
7. TaskList (Story 1.6) displays due date column with color coding: green if due date > 2 days away, yellow if due in 1-2 days, red if overdue
8. GET /api/tasks/overdue endpoint returns all overdue tasks (for future reminder system)
9. WorkflowDetailView shows overdue badge next to overdue tasks in timeline
10. Flyway migration updates Developer Onboarding template to include dueInDays: Task 1 (2 days), Task 2 (1 day after Task 1), Task 3 (3 days after Task 1), Task 4 (1 day after Task 2 & 3 complete)
11. Integration test: Create workflow with start date 5 days ago, verify tasks with dueDate in past display as overdue on dashboard and task list

---

### Story 2.9: Email Reminder System for Pending Tasks

As a **Manager, Tech Support, or Finance user**,
I want to receive email reminders for tasks that are approaching their due date or overdue,
so that I don't forget to complete my assigned tasks.

**Acceptance Criteria:**

1. Scheduled job (Spring @Scheduled) created that runs daily at 9:00 AM: `TaskReminderScheduler.sendDailyReminders()`
2. Job queries for tasks where: status IN (NOT_STARTED, IN_PROGRESS) AND dueDate BETWEEN now AND now+24hours (due within next 24 hours)
3. Job queries for overdue tasks where: status IN (NOT_STARTED, IN_PROGRESS) AND dueDate < now
4. HTML email template created for reminders with subject "Reminder: Task Due Soon - [Task Title]" (for upcoming) or "OVERDUE: Task Requires Attention - [Task Title]" (for overdue)
5. Reminder email content includes: employee name, task title, due date, days overdue (if applicable), "View Task" button
6. Reminder emails sent only if task hasn't been completed (double-check status before sending)
7. Reminder sent once per day maximum (NotificationLog checked to prevent duplicate reminders within 24 hours)
8. Configuration property added: `app.reminders.enabled=true` (can disable reminders in non-prod environments)
9. Configuration property added: `app.reminders.schedule=0 0 9 * * *` (cron expression for 9am daily, configurable)
10. Scheduled job logs summary: "Sent 5 due-soon reminders and 2 overdue reminders"
11. Unit test: mock scheduled job execution, verify reminder emails sent for due-soon and overdue tasks
12. Integration test: Create task with dueDate = tomorrow, manually trigger scheduled job, verify reminder email sent (checked in NotificationLog)

---

## Epic 3: Checklist Verification & Automated Offboarding Mirror

**Epic Goal:**

Implement the core security innovation of the system: mandatory checklist verification for tech support provisioning and the automated offboarding mirror that ensures every provisioned item is deprovisioned when an employee departs. By the end of this epic, tech support will complete checklists that cannot be bypassed, all provisioned accounts/equipment will be automatically logged, and offboarding workflows will be generated from onboarding records to ensure perfect symmetry. This eliminates the security gaps (orphaned accounts, uncollected equipment) identified as critical pain points in the Project Brief.

---

### Story 3.1: Checklist Data Model and Template Configuration

As a **System Administrator**,
I want checklists defined in workflow templates with specific items for each role,
so that tech support has clear, standardized lists of accounts and equipment to provision.

**Acceptance Criteria:**

1. ChecklistItem entity created with fields: id, taskId (FK), description, category (enum: ACCOUNT, SOFTWARE, HARDWARE, ACCESS), required (boolean), completed (boolean), completedBy (FK to User), completedAt, notes (text)
2. WorkflowTemplate JSON schema extended to include checklist definitions for CHECKLIST task types:
   ```json
   {
     "taskType": "CHECKLIST",
     "checklistItems": [
       {"description": "Active Directory Account", "category": "ACCOUNT", "required": true},
       {"description": "Email Account (Outlook)", "category": "ACCOUNT", "required": true},
       ...
     ]
   }
   ```
3. Flyway migration updates Developer Onboarding template Task 3 (Tech Support) to include checklist items:
   - Active Directory Account (ACCOUNT, required)
   - Email Account / Outlook (ACCOUNT, required)
   - VPN Access (ACCESS, required)
   - GitHub Enterprise Access (ACCESS, required)
   - Jira Access (ACCESS, required)
   - Slack Account (ACCOUNT, required)
   - Laptop - Dell XPS 15 (HARDWARE, required)
   - Monitor x2 (HARDWARE, required)
   - Visual Studio License (SOFTWARE, required)
4. When WorkflowService creates tasks from template, if task type is CHECKLIST, create ChecklistItem records from template definition
5. ChecklistItem records linked to parent Task via taskId foreign key
6. GET /api/tasks/{taskId}/checklist endpoint returns all ChecklistItem records for a task
7. Unit test: verify checklist items created from template JSON when workflow instantiated
8. Integration test: Create workflow from Developer Onboarding template, verify Task 3 has 9 ChecklistItem records in database

---

### Story 3.2: Checklist Completion UI for Tech Support

As a **Tech Support user**,
I want a dedicated checklist interface to mark items as provisioned,
so that I can systematically complete setup tasks and verify nothing is missed.

**Acceptance Criteria:**

1. React component "ChecklistCompletionView" created, rendered when TaskDetailView displays task with taskType=CHECKLIST
2. Checklist UI displays: task title, employee information, checklist items grouped by category (Accounts, Software, Hardware, Access)
3. Each checklist item rendered as: large checkbox (44x44px for touch), item description, notes field (expandable textarea), timestamp and user who checked (if completed)
4. Checkboxes visually distinct: empty square for unchecked, green checkmark for checked
5. Progress indicator displayed at top: "7/9 items completed" with visual progress bar
6. Clicking checkbox immediately calls PUT /api/tasks/{taskId}/checklist/{itemId}/toggle to update completed status
7. Checkbox toggle updates ChecklistItem.completed, sets completedBy=currentUser, completedAt=now (or clears if unchecking)
8. Notes field auto-saves on blur: PUT /api/tasks/{taskId}/checklist/{itemId}/notes with {notes: "text"}
9. "Mark Task Complete" button displayed at bottom, disabled until all required checklist items are checked
10. Attempting to submit with incomplete required items shows validation message: "All required items must be completed before submitting"
11. When all required items checked, "Mark Task Complete" button enabled (green, prominent)
12. Clicking "Mark Task Complete" calls PUT /api/tasks/{taskId}/complete, transitions task to COMPLETED status
13. Integration test: Tech Support user opens Task 3 (checklist), checks off 8/9 items, verify button disabled; check 9th item, verify button enabled; submit task, verify task status=COMPLETED

---

### Story 3.3: Provisioned Items Logging for Offboarding

As a **System**,
I want all provisioned checklist items automatically logged when completed,
so that the system knows exactly what needs to be deprovisioned during offboarding.

**Acceptance Criteria:**

1. ProvisionedItem entity created with fields: id, employeeId (FK), workflowInstanceId (FK), checklistItemId (FK), description, category (enum: ACCOUNT, SOFTWARE, HARDWARE, ACCESS), provisionedBy (FK to User), provisionedAt, notes, deprovisionedAt, deprovisionedBy (FK to User)
2. When ChecklistItem is marked complete (completed=true), automatically create ProvisionedItem record copying: description, category, notes, and linking to employee
3. ProvisionedItem.provisionedBy = ChecklistItem.completedBy, provisionedAt = ChecklistItem.completedAt
4. If ChecklistItem is later unchecked (completed=false), delete corresponding ProvisionedItem record (user correction scenario)
5. GET /api/employees/{employeeId}/provisioned-items endpoint returns all ProvisionedItem records where deprovisionedAt IS NULL (still active)
6. ProvisionedItem records serve as source of truth for what was set up and what needs cleanup during offboarding
7. Backend service method: ProvisioningLogService.logProvisionedItem(checklistItem) encapsulates this logic
8. Called automatically from checklist toggle endpoint after updating ChecklistItem
9. Unit test: given completed ChecklistItem, verify ProvisionedItem record created with correct fields
10. Integration test: Tech Support completes Task 3 checklist with 9 items, verify 9 ProvisionedItem records created for employee
11. Integration test: Tech Support unchecks 1 item, verify corresponding ProvisionedItem deleted (8 remaining)

---

### Story 3.4: Initiate Offboarding Workflow

As an **HR Administrator**,
I want to initiate an offboarding workflow for a departing employee,
so that all necessary deprovisioning tasks are created and tracked.

**Acceptance Criteria:**

1. React component "InitiateOffboardingForm" created accessible at route `/offboarding/initiate`
2. Form includes: employee selector (dropdown or autocomplete of active employees), last work date (date picker), reason (dropdown: Resignation, Termination, Retirement, Other)
3. Employee selector populated from employees where status=ACTIVE
4. Form validation: employee required, last work date cannot be in the past
5. POST /api/workflows/initiate-offboarding endpoint accepts {employeeId, lastWorkDate, reason}
6. Backend retrieves employee record, updates status from ACTIVE to OFFBOARDING
7. Backend creates WorkflowInstance with type=OFFBOARDING, links to employee, stores lastWorkDate in metadata
8. Offboarding workflow template (hardcoded for Epic 3, similar to onboarding) includes tasks:
   - Task 1: "HR Prepares Offboarding Checklist" (HR_ADMIN, FORM_COMPLETION)
   - Task 2: "Manager Conducts Exit Interview" (MANAGER, FORM_COMPLETION, dependency: Task 1)
   - Task 3: "Tech Support Deprovisions Accounts and Collects Equipment" (TECH_SUPPORT, CHECKLIST, dependency: Task 1)
   - Task 4: "Finance Processes Final Payroll" (FINANCE, FORM_COMPLETION, dependency: Task 2 & Task 3)
9. WorkflowService creates WorkflowInstance and Task records from offboarding template
10. GET /api/workflows/{instanceId} endpoint returns offboarding workflow (same structure as onboarding)
11. Dashboard displays offboarding workflows alongside onboarding (differentiate with type badge)
12. Flyway migration creates offboarding template record in database
13. Integration test: HR initiates offboarding for active employee, verify WorkflowInstance created with type=OFFBOARDING, employee status=OFFBOARDING, 4 tasks created

---

### Story 3.5: Automated Offboarding Checklist Generation from Onboarding Records

As a **Tech Support user**,
I want the offboarding checklist automatically populated with items provisioned during onboarding,
so that I know exactly what accounts and equipment to deprovision without guessing or referring to old emails.

**Acceptance Criteria:**

1. When offboarding WorkflowInstance is created, system queries ProvisionedItem records for employee where deprovisionedAt IS NULL
2. For Tech Support offboarding task (Task 3 - deprovision checklist), automatically create ChecklistItem records mirroring ProvisionedItem records
3. Each ChecklistItem description prefixed with action verb: "Deactivate [account]", "Revoke [access]", "Collect [hardware]", "Uninstall [software]" based on category
4. Example transformations:
   - ACCOUNT: "Active Directory Account" → "Deactivate Active Directory Account"
   - HARDWARE: "Laptop - Dell XPS 15" → "Collect Laptop - Dell XPS 15"
   - ACCESS: "VPN Access" → "Revoke VPN Access"
   - SOFTWARE: "Visual Studio License" → "Revoke Visual Studio License"
5. ChecklistItem notes field pre-populated with ProvisionedItem notes (context from onboarding)
6. ChecklistItem.required = true for all generated items (must verify everything deprovisioned)
7. Additional metadata: ChecklistItem.metadata stores reference to original ProvisionedItem.id for tracking
8. If employee has NO ProvisionedItem records (hired before system implementation), display message: "No provisioned items found from system records. Please complete checklist manually based on HR documentation."
9. GET /api/tasks/{taskId}/checklist endpoint for offboarding task returns auto-generated ChecklistItem records
10. Unit test: given employee with 9 ProvisionedItem records, verify offboarding task generates 9 ChecklistItem records with correct descriptions
11. Integration test: Complete onboarding workflow for employee (9 items provisioned), initiate offboarding, verify Tech Support offboarding task has 9 checklist items mirroring onboarding

---

### Story 3.6: Offboarding Checklist Completion and Deprovisioning Verification

As a **Tech Support user**,
I want to complete the offboarding checklist by verifying each item is deprovisioned,
so that I can confirm all accounts are deactivated and equipment is collected before the employee departs.

**Acceptance Criteria:**

1. ChecklistCompletionView (from Story 3.2) works identically for offboarding tasks as onboarding tasks
2. When Tech Support checks off offboarding checklist item (e.g., "Deactivate Active Directory Account"), ChecklistItem.completed = true
3. Backend additionally updates corresponding ProvisionedItem record: sets deprovisionedAt=now, deprovisionedBy=currentUser
4. ProvisionedItem update triggered by checking item with metadata.provisionedItemId reference
5. "Mark Task Complete" button disabled until all required checklist items verified (same validation as onboarding)
6. Offboarding task cannot be completed if any ProvisionedItem still has deprovisionedAt=NULL
7. Upon task completion, backend verifies: SELECT COUNT(*) FROM ProvisionedItem WHERE employeeId={id} AND deprovisionedAt IS NULL; if count > 0, reject completion with error
8. When offboarding WorkflowInstance completes (all tasks done), update Employee.status from OFFBOARDING to DEPARTED
9. Dashboard WorkflowDetailView for offboarding shows which items have been deprovisioned (green checkmarks)
10. GET /api/employees/{employeeId}/provisioned-items endpoint now returns deprovisionedAt and deprovisionedBy for completed items
11. Integration test: Tech Support completes offboarding checklist for employee with 9 provisioned items, verify all 9 ProvisionedItem records have deprovisionedAt populated
12. Integration test: Complete entire offboarding workflow, verify employee status changes to DEPARTED

---

### Story 3.7: Offboarding Security Report and Compliance View

As an **HR Administrator or System Administrator**,
I want to view a report of all active employees and whether they have any orphaned provisioned items,
so that I can identify security risks and ensure compliance.

**Acceptance Criteria:**

1. React component "SecurityComplianceReport" created accessible at route `/reports/security` (HR_ADMIN and SYSTEM_ADMIN only)
2. Report displays table with columns: Employee Name, Status, Provisioned Items (count), Orphaned Items (count), Last Activity Date, Actions
3. GET /api/reports/security-compliance endpoint returns data for all employees
4. "Orphaned Items" calculated as: COUNT(ProvisionedItem WHERE employeeId={id} AND deprovisionedAt IS NULL AND employee.status IN (DEPARTED, OFFBOARDING))
5. Employees with orphaned items highlighted in red with warning icon
6. Clicking employee row expands to show detailed list of orphaned provisioned items with descriptions and provision dates
7. "View Details" button navigates to employee's offboarding workflow (if exists) or displays message "No offboarding workflow initiated"
8. Filter options: "Show All Employees", "Show Only With Orphaned Items", "Show Only Departed"
9. Export to CSV button generates downloadable report with all data
10. Summary metrics displayed at top: "Total Employees: [count]", "Active with Provisioned Items: [count]", "Employees with Orphaned Items: [count]", "Security Risk Score: [calculation]"
11. Security Risk Score = (orphaned items count / total provisioned items) * 100 (percentage of items not properly deprovisioned)
12. Integration test: Create employee with completed onboarding (9 provisioned items), set status=DEPARTED without completing offboarding, verify report shows 9 orphaned items for that employee

---

### Story 3.8: Manual Provisioned Item Management

As a **System Administrator**,
I want to manually add or remove provisioned items for employees,
so that I can correct data for employees hired before the system was implemented or fix mistakes.

**Acceptance Criteria:**

1. React component "ManageProvisionedItems" created accessible at route `/employees/{employeeId}/provisioned-items` (SYSTEM_ADMIN only)
2. Page displays employee information and table of all ProvisionedItem records (both active and deprovisioned)
3. Table columns: Description, Category, Provisioned By, Provisioned Date, Deprovisioned By, Deprovisioned Date, Status, Actions
4. "Add Item" button opens modal form with fields: description (text), category (dropdown: ACCOUNT, SOFTWARE, HARDWARE, ACCESS), notes (textarea)
5. POST /api/employees/{employeeId}/provisioned-items endpoint creates ProvisionedItem record with provisionedBy=currentUser, provisionedAt=now
6. Manual items not linked to workflowInstance or checklistItem (nullable foreign keys)
7. "Delete" button (trash icon) calls DELETE /api/provisioned-items/{itemId} - only allowed if deprovisionedAt IS NULL (can't delete historical records)
8. "Mark Deprovisioned" button calls PUT /api/provisioned-items/{itemId}/deprovision - sets deprovisionedAt=now, deprovisionedBy=currentUser
9. Audit log records all manual changes: {entityType: "ProvisionedItem", action: "MANUALLY_ADDED" or "MANUALLY_DELETED" or "MANUALLY_DEPROVISIONED"}
10. Warning message displayed: "Manual changes should only be made to correct errors or for employees hired before system implementation. Changes are logged for audit purposes."
11. Integration test: System Admin manually adds 3 provisioned items for employee, verify items appear in GET /api/employees/{employeeId}/provisioned-items
12. Integration test: System Admin creates offboarding workflow for employee with manual items, verify offboarding checklist includes manually added items

---

## Epic 4: Template Customization & Production Readiness

**Epic Goal:**

Empower HR administrators to customize workflow templates without developer intervention by providing a template editor, custom field builder, and conditional logic engine. Deliver essential reporting, audit capabilities, and production deployment preparation to ensure the system is ready for pilot deployment. By the end of this epic, HR can create role-specific templates, add company-specific fields, define conditional task logic, view comprehensive reports, and the system is deployed to a production-ready environment with proper monitoring and security hardening.

---

### Story 4.1: Template Designer - List and View Templates

As an **HR Administrator**,
I want to view all workflow templates and understand their structure,
so that I can decide which templates to customize or create new ones based on existing patterns.

**Acceptance Criteria:**

1. React component "TemplateLibrary" created accessible at route `/templates` (HR_ADMIN and SYSTEM_ADMIN only)
2. Page displays list of all WorkflowTemplate records with cards showing: name, type (ONBOARDING/OFFBOARDING), version, active status, last modified date, "View" and "Edit" buttons
3. GET /api/templates endpoint returns all templates with metadata (task count, field count, usage count)
4. Filter options: "All Templates", "Onboarding Only", "Offboarding Only", "Active Only", "Inactive"
5. Search bar filters templates by name
6. "Create New Template" button navigates to `/templates/new` (deferred to future; Epic 4 focuses on editing existing)
7. "View" button navigates to `/templates/{templateId}/view` (read-only preview)
8. "Edit" button navigates to `/templates/{templateId}/edit` (editor from Story 4.2)
9. Template preview shows: template name, task list with titles and types, custom fields defined, conditional rules summary
10. "Duplicate Template" button creates copy of template for customization
11. GET /api/templates/{templateId} endpoint returns complete template structure including tasks, checklist items, custom fields, conditional rules
12. Integration test: HR Admin views template library, sees Developer Onboarding and Offboarding templates, clicks View to see template structure

---

### Story 4.2: Template Designer - Edit Template and Task Configuration

As an **HR Administrator**,
I want to edit workflow templates to add, remove, or modify tasks,
so that I can customize workflows to match our company's specific onboarding process.

**Acceptance Criteria:**

1. React component "TemplateEditor" created accessible at route `/templates/{templateId}/edit` (HR_ADMIN and SYSTEM_ADMIN only)
2. Editor displays template name (editable), type (readonly), and list of tasks in sequence
3. Each task displayed as card with fields: title (editable), description (editable), assignedToRole (dropdown), taskType (dropdown: FORM_COMPLETION, CHECKLIST, APPROVAL), dueInDays (number input)
4. "Add Task" button appends new task to template with default values
5. "Delete Task" button (trash icon) removes task from template with confirmation prompt
6. Task cards draggable for reordering (simple up/down arrows for Epic 4; full drag-and-drop deferred)
7. "Move Up" and "Move Down" buttons reorder tasks in sequence
8. "Edit Dependencies" button opens modal showing all tasks; user selects prerequisite tasks via checkboxes (creates sequential dependencies)
9. "Save Template" button calls PUT /api/templates/{templateId} to persist changes
10. Template versioning: saving creates new version number, previous version archived (template.version incremented)
11. Validation: cannot save template with circular dependencies (e.g., Task A depends on Task B, Task B depends on Task A)
12. Validation: template must have at least 1 task, all tasks must have assignedToRole
13. Success message displayed on save: "Template saved successfully. Version [X] created."
14. Integration test: HR Admin edits Developer Onboarding template, adds new task "IT Orientation Meeting", saves template, verifies new task appears when creating workflow from template

---

### Story 4.3: Custom Field Builder

As an **HR Administrator**,
I want to add custom fields to workflow templates,
so that I can capture company-specific information not included in the default template.

**Acceptance Criteria:**

1. TemplateEditor (Story 4.2) includes "Custom Fields" section displaying list of custom fields defined for template
2. CustomField entity created with fields: id, templateId (FK), fieldName, fieldLabel, fieldType (enum: TEXT, TEXTAREA, NUMBER, DATE, DROPDOWN, CHECKBOX), required (boolean), options (JSON for dropdown choices), displayOrder
3. "Add Custom Field" button opens modal form with fields: fieldLabel (text), fieldType (dropdown), required (checkbox), options (textarea for dropdown, comma-separated)
4. Custom field form validates: fieldLabel required, options required if fieldType=DROPDOWN
5. POST /api/templates/{templateId}/custom-fields endpoint creates CustomField record
6. Custom fields displayed in editor with: label, type badge, required indicator, "Edit" and "Delete" buttons
7. DELETE /api/templates/{templateId}/custom-fields/{fieldId} removes custom field
8. When workflow initiated from template with custom fields, InitiateOnboardingForm (Story 1.5) dynamically renders custom field inputs
9. Custom field values stored in WorkflowInstance.metadata JSON column
10. TaskDetailView for FORM_COMPLETION tasks displays custom field values in employee context section
11. Example custom fields for Developer Onboarding: "GitHub Username" (TEXT, required), "Preferred OS" (DROPDOWN: Windows/Mac/Linux, required), "Security Clearance Level" (DROPDOWN: None/Confidential/Secret, optional)
12. Integration test: HR Admin adds custom field "GitHub Username" to Developer Onboarding template, creates workflow, verifies custom field appears in initiation form and task detail view

---

### Story 4.4: Conditional Task Logic Engine

As an **HR Administrator**,
I want to define rules that show or hide tasks based on custom field values,
so that workflows adapt to different employee types without creating dozens of separate templates.

**Acceptance Criteria:**

1. ConditionalRule entity created with fields: id, templateId (FK), taskId (FK - task affected by rule), condition (JSON storing rule logic), action (enum: INCLUDE_TASK, SKIP_TASK)
2. TemplateEditor includes "Conditional Logic" section for each task with "Add Rule" button
3. Rule builder modal allows HR to define: IF [customFieldName] [operator] [value] THEN [action]
4. Operators supported: EQUALS, NOT_EQUALS, CONTAINS, GREATER_THAN, LESS_THAN, IS_EMPTY, IS_NOT_EMPTY
5. Example rule: IF "Employment Type" EQUALS "Remote" THEN SKIP_TASK "Assign Office Desk"
6. POST /api/templates/{templateId}/conditional-rules endpoint creates ConditionalRule record
7. When WorkflowService creates workflow from template, evaluate conditional rules against custom field values from initiation form
8. If rule evaluates to SKIP_TASK, do not create Task record for that task
9. If rule evaluates to INCLUDE_TASK, create Task record (default behavior if no rules)
10. WorkflowInstance.metadata stores rule evaluation results for audit: {taskSkipped: "Assign Office Desk", reason: "Employment Type = Remote"}
11. ConditionalRuleEvaluator service class implements rule evaluation logic with unit tests for all operators
12. Complex rules with AND/OR logic deferred to future; Epic 4 supports single-condition rules only
13. Integration test: Add rule to Developer Onboarding: IF "Employment Type" EQUALS "Remote" THEN SKIP "Assign Office Desk" task; create remote employee workflow, verify desk assignment task not created

---

### Story 4.5: Basic Reporting Dashboard

As an **HR Administrator**,
I want to view reports on workflow completion times, task SLA compliance, and user activity,
so that I can identify process bottlenecks and measure system effectiveness.

**Acceptance Criteria:**

1. React component "ReportsDashboard" created accessible at route `/reports` (HR_ADMIN and SYSTEM_ADMIN only)
2. Reports page includes tabbed interface with three reports: "Completion Times", "SLA Compliance", "User Activity"
3. **Completion Times Report:**
   - Bar chart showing average days to complete workflows by template type
   - Table with columns: Template Name, Workflows Completed, Avg Completion Time, Min Time, Max Time
   - Date range filter: Last 30 days, Last 90 days, Last Year, Custom Range
4. GET /api/reports/completion-times?startDate={date}&endDate={date} returns aggregated data
5. **SLA Compliance Report:**
   - Pie chart showing percentage of tasks completed on-time vs overdue
   - Table with columns: Task Title, Total Assigned, Completed On-Time, Overdue, SLA Compliance %
   - Filter by department, role, task type
6. GET /api/reports/sla-compliance?department={dept}&role={role} returns aggregated data
7. **User Activity Report:**
   - Table showing tasks completed per user with columns: User Name, Role, Tasks Completed, Avg Completion Time, Tasks Currently Assigned
   - Sort by tasks completed (desc) to identify most active users
8. GET /api/reports/user-activity returns aggregated data
9. All reports include "Export to CSV" button that downloads data
10. Loading spinner displayed while fetching report data
11. Empty state message if no data available: "No data available for selected date range"
12. Integration test: Generate report data by creating and completing 5 workflows, view Completion Times report, verify average calculation correct

---

### Story 4.6: Comprehensive Audit Log Viewer

As a **System Administrator**,
I want to view detailed audit logs of all system actions,
so that I can investigate issues, track changes, and ensure compliance with security policies.

**Acceptance Criteria:**

1. React component "AuditLogViewer" created accessible at route `/admin/audit-log` (SYSTEM_ADMIN only)
2. Page displays table of AuditLog records with columns: Timestamp, User, Entity Type, Entity ID, Action, Details, IP Address
3. GET /api/admin/audit-log endpoint returns paginated audit logs (page size 50, default sort by timestamp DESC)
4. Filter options: User (dropdown), Entity Type (dropdown), Action (dropdown), Date Range (date pickers)
5. Search box filters by Entity ID or changeDetails text content
6. "View Details" button expands row to show full changeDetails JSON in formatted, readable display
7. AuditLog entity extended to capture IP address: add ipAddress field, populate from request in Spring Boot @Aspect or filter
8. Color-coded action types: blue for CREATE, yellow for UPDATE, red for DELETE, green for COMPLETE
9. Real-time updates: page auto-refreshes every 60 seconds to show latest logs
10. "Export Filtered Logs" button downloads CSV of currently filtered/searched logs
11. Pagination controls: Previous, Next, Jump to Page, Records per Page (25, 50, 100)
12. Integration test: Perform various actions (create workflow, complete task, edit template), verify audit logs recorded with correct user, entity type, and action

---

### Story 4.7: Production Environment Configuration and Security Hardening

As a **DevOps Engineer**,
I want production-ready configuration and security measures implemented,
so that the system can be deployed securely to a production environment.

**Acceptance Criteria:**

1. Environment-specific configuration profiles created: application-dev.properties, application-prod.properties
2. Production configuration uses environment variables for all secrets: database credentials, SMTP credentials, session secret key
3. HTTPS enforced in production: Spring Security configured to require HTTPS, redirects HTTP to HTTPS
4. CSRF protection enabled for all POST/PUT/DELETE endpoints (Spring Security default, verify not disabled)
5. CORS configuration restricted to production domain (not wildcard *)
6. SQL injection prevention verified: all database queries use parameterized queries via JPA (no string concatenation)
7. XSS prevention verified: React JSX auto-escapes; Content-Security-Policy header configured to disallow inline scripts
8. Session security hardened: HTTP-only cookies, Secure flag in production, SameSite=Strict
9. Rate limiting implemented on authentication endpoints: max 5 login attempts per IP per minute (Spring Boot Bucket4j or similar)
10. Security headers configured: X-Frame-Options: DENY, X-Content-Type-Options: nosniff, Strict-Transport-Security
11. Docker Compose production configuration created with: PostgreSQL with volume mount, application container with health checks, reverse proxy (Nginx) for TLS termination
12. README section added: "Production Deployment Guide" with steps for environment variable configuration, database initialization, TLS certificate setup, Docker deployment
13. Manual security testing checklist: SQL injection attempts fail, XSS attempts blocked, CSRF token required, session hijacking prevented

---

### Story 4.8: Database Backup and Restore Procedures

As a **System Administrator**,
I want documented and tested database backup/restore procedures,
so that data can be recovered in case of failure or corruption.

**Acceptance Criteria:**

1. Backup script created: `scripts/backup-database.sh` that runs `pg_dump` with consistent snapshot option
2. Script accepts parameters: database connection string, output directory, backup file name prefix
3. Backup file named with timestamp: `elms-backup-2025-10-16-14-30-00.sql`
4. Backup script compresses output with gzip to save storage space
5. Cron job configuration documented in README for automated daily backups: `0 2 * * * /scripts/backup-database.sh`
6. Restore script created: `scripts/restore-database.sh` that drops existing database (with confirmation prompt), recreates schema, and restores from backup file
7. Restore script validates backup file integrity before restoring
8. Point-in-time recovery (PITR) enabled for PostgreSQL: WAL archiving configured in postgresql.conf
9. Documentation added: "Backup and Restore Procedures" in docs/operations/backup-restore.md with step-by-step instructions
10. Recovery Time Objective (RTO) documented: Target restoration within 1 hour of failure detection
11. Recovery Point Objective (RPO) documented: Maximum 24 hours of data loss (daily backup schedule)
12. Manual test: Create backup, create test data, restore from backup, verify data matches backup point

---

### Story 4.9: System Monitoring and Health Checks

As a **DevOps Engineer**,
I want health check endpoints and monitoring infrastructure,
so that I can detect and respond to system issues proactively.

**Acceptance Criteria:**

1. Spring Boot Actuator dependency added, health endpoint enabled at GET /actuator/health
2. Health check includes: database connectivity, disk space, SMTP server connectivity (optional - don't fail health check if SMTP down)
3. Custom health indicator for workflow processing: checks if any workflows stuck (no task completions in last 7 days with status IN_PROGRESS)
4. GET /actuator/health returns: {"status": "UP", "components": {"db": "UP", "diskSpace": "UP", "smtp": "UP"}}
5. GET /actuator/health returns 200 OK if UP, 503 Service Unavailable if DOWN
6. Prometheus metrics endpoint enabled at GET /actuator/prometheus (if Prometheus available in infrastructure)
7. Application logs structured in JSON format for easy parsing: {"timestamp": "...", "level": "INFO", "logger": "...", "message": "...", "context": {...}}
8. Log levels configured: DEBUG for dev, INFO for production, ERROR always logged
9. Critical errors trigger alerts: database connection failures, SMTP failures, uncaught exceptions logged with full stack trace
10. README section: "Monitoring and Operations" with instructions for health check integration with monitoring tools (Prometheus, Grafana, Uptime Robot, etc.)
11. Docker health check configured: `HEALTHCHECK --interval=30s --timeout=3s CMD wget --quiet --tries=1 --spider http://localhost:8080/actuator/health || exit 1`
12. Integration test: Stop PostgreSQL container, verify health check returns DOWN; restart PostgreSQL, verify health check returns UP

---

### Story 4.10: MVP Pilot Deployment and Validation

As a **Product Manager**,
I want the system deployed to a pilot environment and validated with real users,
so that we can confirm MVP success criteria before broader rollout.

**Acceptance Criteria:**

1. Pilot environment provisioned: production-like infrastructure with smaller resource allocation (2 CPU, 4GB RAM, 50GB storage)
2. Pilot database seeded with: 5 test users (1 HR, 1 Manager, 2 Tech Support, 1 Finance), Developer Onboarding template, Offboarding template
3. Pilot deployment checklist executed: Docker containers deployed, database initialized, SMTP configured, TLS certificate installed, health checks passing
4. User acceptance testing (UAT) plan documented with test scenarios:
   - HR initiates onboarding for new developer
   - Manager completes employee details form
   - Tech Support completes 9-item checklist
   - Finance completes payroll setup
   - Workflow completes, employee status changes to ACTIVE
   - HR initiates offboarding
   - Tech Support completes deprovisioning checklist (9 items mirrored from onboarding)
   - Offboarding completes, employee status changes to DEPARTED
   - Security report shows zero orphaned items
5. MVP Success Criteria validation (from Project Brief):
   - ✅ All employee onboardings and offboardings can be initiated and tracked through the system
   - ✅ Tech support can complete checklists with 100% verification
   - ✅ Offboarding automatically uses onboarding records to generate task lists
   - ✅ Managers and HR have real-time visibility into progress
   - ✅ System reduces average onboarding time (measure: complete pilot onboarding faster than manual process)
   - ✅ Zero critical security gaps (orphaned admin accounts) in pilot group
6. Pilot user feedback collected via survey: usability rating (1-5 scale), time savings estimation, pain points identified, feature requests captured
7. Pilot deployment documentation created: deployment steps, rollback procedure, common issues and resolutions, user training materials (quick start guide, video walkthrough)
8. Pilot success metrics tracked: workflows completed, average completion time, task SLA compliance, user adoption rate, critical bugs encountered
9. Go/No-Go decision criteria defined: <5 critical bugs, >80% user satisfaction, MVP success criteria met
10. Manual validation: Product Manager and key stakeholders complete full onboarding and offboarding workflow in pilot environment, confirm all features working as specified

---

## Checklist Results Report

### Executive Summary

**Overall PRD Completeness:** 95%

**MVP Scope Appropriateness:** Just Right - The 4 epics deliver core value progressively with appropriate features for 12-16 week timeline

**Readiness for Architecture Phase:** Ready - Technical constraints clear, requirements comprehensive, stories well-defined

**Most Critical Gaps:** Minor - No blocking issues identified; some enhancements recommended for future iterations

---

### Category Analysis

| Category                         | Status  | Critical Issues |
| -------------------------------- | ------- | --------------- |
| 1. Problem Definition & Context  | PASS    | None            |
| 2. MVP Scope Definition          | PASS    | None            |
| 3. User Experience Requirements  | PASS    | None            |
| 4. Functional Requirements       | PASS    | None            |
| 5. Non-Functional Requirements   | PASS    | None            |
| 6. Epic & Story Structure        | PASS    | None            |
| 7. Technical Guidance            | PASS    | None            |
| 8. Cross-Functional Requirements | PASS    | None            |
| 9. Clarity & Communication       | PASS    | None            |

---

### Detailed Validation Results

#### 1. Problem Definition & Context: PASS (100%)

✅ **Problem Statement:** Clearly articulated in Goals & Background Context section - manual email-based processes with no tracking, inconsistent execution, security risks from orphaned accounts
✅ **Target Users:** Specifically identified - HR administrators, IT/tech support, line managers, finance teams
✅ **Success Metrics:** Quantified - 40% reduction in onboarding time, 100% offboarding completion rate, 95% SLA compliance, <2% equipment loss
✅ **Business Impact:** Cost inefficiency, security risk, employee experience degradation, compliance risk documented
✅ **Differentiation:** "Offboarding mirror" innovation clearly distinguishes from existing solutions

#### 2. MVP Scope Definition: PASS (98%)

✅ **Core Functionality:** 8 MVP features clearly distinguished (Process Templates, Task Routing, Dashboard, Email Integration, Checklists, Offboarding Mirror, Custom Fields, Conditional Logic)
✅ **Scope Boundaries:** Extensive "Out of Scope" list in Project Brief (mobile apps, AD integration, advanced analytics, SSO, etc.)
✅ **Rationale:** Each feature justified with reference to pain points from stakeholder feedback
✅ **MVP Minimalism:** Features are essential; no "nice-to-haves" included in MVP
⚠️ **Minor Note:** Epic 4 is feature-rich (10 stories); could consider moving some reporting/monitoring to post-MVP if timeline pressure emerges

#### 3. User Experience Requirements: PASS (95%)

✅ **User Journeys:** Complete flows documented in UI Design Goals section (dashboard-centric navigation, task-driven workflow, checklist-first verification)
✅ **Core Screens:** 8 screens identified (Login, Dashboard, Task Detail, Workflow Template Designer, Checklist, Reports, Settings, History)
✅ **Usability:** WCAG AA accessibility target, responsive web design, error prevention principles
✅ **Platform Compatibility:** Desktop-first, tablet-compatible, mobile explicitly out of scope with rationale
✅ **Error Handling:** Validation messages specified in acceptance criteria throughout stories

#### 4. Functional Requirements: PASS (100%)

✅ **Feature Completeness:** 25 functional requirements (FR1-FR25) cover all MVP features comprehensively
✅ **Requirements Quality:** Each requirement uses "shall" language, focuses on WHAT not HOW, is testable
✅ **User Stories:** 35 user stories across 4 epics with clear "As a [role], I want [action], so that [benefit]" format
✅ **Acceptance Criteria:** Every story has 10-14 specific, testable acceptance criteria
✅ **Dependencies:** Sequential dependencies between stories explicitly identified (e.g., Story 1.2 auth before 1.3 data models)
✅ **Story Sizing:** Stories sized for AI agent execution (2-8 hours each, ~30-60 hours per epic)

#### 5. Non-Functional Requirements: PASS (100%)

✅ **Performance:** Page load <2s, dashboard refresh <1s, 100 concurrent users, email delivery <30s (NFR1-NFR4)
✅ **Security:** Encryption at rest/transit, session timeout, audit logs immutable, RBAC, GDPR compliance (NFR7-NFR10, NFR20, NFR25)
✅ **Reliability:** 99% uptime during business hours, database backup, point-in-time recovery (NFR11-NFR12)
✅ **Technical Constraints:** React/TypeScript, Spring Boot/Java, PostgreSQL, Docker, modular monolith architecture (NFR13-NFR19)
✅ **Scalability:** Horizontal scaling consideration noted (NFR19)

#### 6. Epic & Story Structure: PASS (98%)

✅ **Epic Definition:** 4 epics represent cohesive functionality units with clear goals
✅ **Epic Sequence:** Logical progression - Foundation → Dashboard/Email → Checklists/Offboarding → Customization/Production
✅ **Epic 1 Foundation:** Includes project setup, auth, data models, hardcoded template, workflow execution - proves end-to-end capability
✅ **Value Delivery:** Each epic delivers testable, deployable functionality (not just scaffolding)
✅ **Story Breakdown:** Stories are independent where possible, sized appropriately, include context
✅ **First Epic Completeness:** Epic 1 establishes all infrastructure, auth, core workflow engine, plus working workflow from HR initiation through task completion

#### 7. Technical Guidance: PASS (100%)

✅ **Architecture Direction:** Modular monolith with 5 service modules (WorkflowService, NotificationService, TemplateService, UserService, AuditService) clearly defined
✅ **Technical Constraints:** Full stack specified - React 18+, Spring Boot 3.x, PostgreSQL 15+, Docker, SMTP integration
✅ **Decision Framework:** Trade-offs documented (monolith vs microservices, session auth vs JWT, polling vs WebSockets)
✅ **Integration Points:** SMTP email, REST APIs, future webhook support identified
✅ **Technical Risks:** Email deliverability, template designer complexity, conditional logic evaluation flagged
✅ **Security Requirements:** HTTPS, CSRF protection, SQL injection prevention, XSS prevention, rate limiting detailed in Story 4.7

#### 8. Cross-Functional Requirements: PASS (98%)

✅ **Data Requirements:** 10 entities defined (User, Employee, WorkflowTemplate, WorkflowInstance, Task, TaskDependency, ChecklistItem, ProvisionedItem, CustomField, ConditionalRule, AuditLog, NotificationLog)
✅ **Schema Changes:** Flyway migrations tied to stories (V1__init, V2__core_schema, V3__seed_developer_template)
✅ **Integration:** SMTP for Outlook, RESTful APIs, future Active Directory integration planned
✅ **Operational Requirements:** Docker deployment, health checks, monitoring, backup/restore procedures (Epic 4 Stories 4.7-4.9)
✅ **Testing:** Unit (80% coverage), integration, E2E (critical paths), manual testing specified in Technical Assumptions

#### 9. Clarity & Communication: PASS (95%)

✅ **Documentation Quality:** Clear, consistent language; well-structured with sections, subsections, tables
✅ **Organization:** Logical flow from Goals → Requirements → UI → Technical → Epics → Stories
✅ **Terminology:** Consistent use of WorkflowInstance, Task, ChecklistItem, ProvisionedItem, etc.
✅ **Diagrams:** Template JSON structure example provided; workflow dependency descriptions
✅ **Versioning:** Change log included; template versioning strategy documented

---

### Top Issues by Priority

**BLOCKERS:** None identified

**HIGH:** None identified

**MEDIUM:**
- Epic 4 Story Count: 10 stories may be ambitious for final epic if timeline slips; consider prioritizing Stories 4.1-4.6 as MVP, deferring 4.7-4.10 to post-MVP hardening phase if needed
- Real-Time Dashboard Updates: Polling approach specified but no explicit refresh interval defined; recommend adding to Story 2.2 acceptance criteria

**LOW:**
- Workflow Visualization: Story 2.3 mentions "simple vertical timeline" but could benefit from wireframe or more detailed description
- Error Message Consistency: Various stories specify error messages; recommend creating error message style guide for consistency

---

### MVP Scope Assessment

**Appropriateness:** Just Right

**Reasoning:**
- Foundation (Epic 1): Absolutely essential - infrastructure, auth, core workflow engine
- Dashboard & Email (Epic 2): Directly addresses "no visibility" pain point - critical for MVP value
- Checklists & Offboarding (Epic 3): The innovation differentiator - mandatory verification and security guarantee
- Customization & Production (Epic 4): HR flexibility + production readiness - appropriate for pilot deployment

**Features That Could Be Cut (If Timeline Pressure):**
- Story 4.4 Conditional Task Logic: Single-condition rules are nice-to-have; templates can use separate templates per role instead
- Story 4.5 Reporting Dashboard: Basic metrics could be deferred to post-MVP; dashboard visibility sufficient for pilot
- Stories 4.7-4.10 Production Hardening: Could deploy to staging/pilot without full production hardening initially

**No Missing Essential Features:** All core workflows (onboarding, offboarding, tracking, verification) covered

**Timeline Realism:** 12-16 weeks with 2-person team is achievable:
- Epic 1: 4 weeks (30-40 hours)
- Epic 2: 4 weeks (38-46 hours)
- Epic 3: 4 weeks (38-46 hours)
- Epic 4: 6-8 weeks (52-62 hours) - most feature-rich
- Buffer: 2-4 weeks for integration, bug fixes, UAT

---

### Technical Readiness

**Clarity of Technical Constraints:** Excellent
- Full stack specified with version numbers
- Architecture pattern (modular monolith) clearly defined
- Service boundaries and responsibilities documented
- Database schema entities identified
- Integration approach (SMTP, REST APIs) detailed

**Identified Technical Risks:**
- Email Deliverability: Corporate SMTP rate limits, spam filters - mitigation via async sending, error logging
- Template Designer Complexity: Full drag-and-drop deferred; up/down arrows sufficient for MVP
- Conditional Logic: Limited to single-condition rules to avoid complexity explosion
- Scaling: Session-based auth requires sticky sessions if horizontally scaled

**Areas Needing Architect Investigation:**
- Database Indexing Strategy: Foreign keys and frequently queried columns mentioned; architect should design comprehensive index plan
- Email Template Rendering: HTML/plain text templates; architect should choose templating engine (Thymeleaf, Freemarker, etc.)
- Workflow State Machine: Task dependencies and status transitions; architect should design state machine logic
- Frontend State Management: React Context API mentioned with Redux Toolkit consideration; architect should decide based on complexity assessment

---

### Recommendations

#### For Immediate Action: None (PRD is ready)

#### For Future Iterations:
1. **Add Dashboard Refresh Interval:** Specify polling frequency in Story 2.2 (recommend 30-60 seconds)
2. **Create Error Message Style Guide:** Centralize error message patterns for consistency
3. **Wireframe Key Screens:** Add visual mockups for Dashboard (Story 2.2) and Checklist UI (Story 3.2) to reduce ambiguity
4. **Define Notification Retry Logic:** Story 2.7 mentions async email sending; specify retry policy for failed deliveries
5. **Clarify Template Versioning Impact:** Story 4.2 creates new template version on save; define how existing in-progress workflows are affected

#### Suggested Improvements:
- **Story 2.2 Dashboard Pipeline:** Consider adding story acceptance criteria for keyboard navigation between workflow cards (accessibility)
- **Story 3.5 Offboarding Mirror:** Add acceptance criteria for handling edge case where ProvisionedItem was manually deleted before offboarding initiated
- **Story 4.10 Pilot Deployment:** Consider adding rollback smoke tests to pilot validation (verify rollback procedure works)

---

### Final Decision

**✅ READY FOR ARCHITECT**

The PRD and epics are comprehensive, properly structured, and ready for architectural design. The requirements are clear, testable, and appropriately scoped for a 12-16 week MVP. The technical constraints provide sufficient guidance without over-specifying implementation details. The epic structure delivers value incrementally with logical dependencies.

**Key Strengths:**
- Clear problem statement with quantified pain points
- Well-defined user personas and success metrics
- Comprehensive functional and non-functional requirements
- Logical epic sequencing with independent, testable stories
- Detailed acceptance criteria enable clear implementation and testing
- "Offboarding mirror" innovation clearly articulated as differentiator
- Production readiness considerations included from start

**Recommended Next Steps:**
1. Architect reviews PRD and creates Architecture Document
2. UX Expert creates wireframes for key screens (Dashboard, Checklist, Template Designer)
3. Development team estimates stories for sprint planning
4. Product Manager schedules stakeholder review of PRD for final approval

---

## Next Steps

### UX Expert Prompt

**Task:** Create UX design specifications and wireframes for the Employee Lifecycle Management System

**Context:** Review the complete PRD above, focusing on the "User Interface Design Goals" section and user stories across all epics. Pay special attention to:
- Dashboard pipeline visualization (Epic 2, Stories 2.1-2.2)
- Checklist completion interface (Epic 3, Story 3.2)
- Template designer and custom field builder (Epic 4, Stories 4.1-4.3)
- Workflow detail view with task timeline (Epic 2, Story 2.3)

**Deliverables:**
1. High-fidelity wireframes for 8 core screens identified in UI Design Goals section
2. Interaction design specifications for dashboard, checklist, and template designer
3. Responsive breakpoints and tablet adaptation strategies
4. Accessibility implementation guide (WCAG AA compliance approach)
5. Component library recommendations (Material-UI or Ant Design integration)
6. Visual design system: color palette, typography, iconography, spacing

**Start Command:** Review this PRD and begin creating wireframes for the Dashboard (primary screen) with visual pipeline, workflow cards, and summary metrics.

---

### Architect Prompt

**Task:** Create technical architecture document for the Employee Lifecycle Management System

**Context:** Review the complete PRD above, focusing on the "Technical Assumptions" section, functional/non-functional requirements (FR1-FR25, NFR1-NFR20), and all user stories across 4 epics.

**Key Architecture Decisions Needed:**
1. **Database Schema Design:** Define complete schema for 12+ entities with indexes, constraints, and relationships
2. **Service Layer Design:** Detail the 5 service modules (WorkflowService, NotificationService, TemplateService, UserService, AuditService) with interfaces and responsibilities
3. **Workflow State Machine:** Design task dependency resolution and workflow status calculation logic
4. **Email Templating:** Select templating engine and design template structure
5. **API Design:** Define RESTful API endpoints, request/response formats, error handling
6. **Authentication & Authorization:** Spring Security configuration, session management, RBAC implementation
7. **Frontend Architecture:** React component hierarchy, state management approach, API client design
8. **Deployment Architecture:** Docker Compose configuration, environment management, scaling considerations

**Deliverables:**
1. System architecture diagram (component view, deployment view)
2. Database schema with ER diagram
3. API specification (OpenAPI/Swagger format preferred)
4. Sequence diagrams for critical workflows (onboarding initiation, checklist completion, offboarding generation)
5. Security architecture and threat model
6. Monitoring and observability strategy
7. Development setup guide

**Start Command:** Begin with the database schema design, creating an ER diagram for all entities and their relationships as defined in the PRD.

---

*This Product Requirements Document was created using the BMAD-METHOD™ framework.*

**Document Version:** 1.0
**Last Updated:** 2025-10-16
**Author:** Product Manager - John
**Status:** Approved - Ready for Architecture Phase

