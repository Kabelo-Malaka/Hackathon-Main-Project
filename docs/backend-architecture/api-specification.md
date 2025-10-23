# API Specification

RESTful API following Spring MVC conventions with standard HTTP methods and status codes.

## Base URL
```
http://localhost:8080/api
```

## Authentication Endpoints

**POST /api/auth/login**
- Purpose: Authenticate user and create session
- Request Body:
```json
{
  "email": "admin@company.com",
  "password": "SecurePass123"
}
```
- Response: 200 OK with session cookie
```json
{
  "success": true,
  "user": {
    "id": "uuid",
    "email": "admin@company.com",
    "firstName": "John",
    "lastName": "Doe",
    "role": "HR_ADMIN"
  }
}
```

**POST /api/auth/logout**
- Purpose: Invalidate session
- Response: 200 OK

**GET /api/auth/me**
- Purpose: Get current user info
- Response: 200 OK with user object

## Workflow Endpoints

**GET /api/workflows**
- Purpose: Get all workflows with optional filtering (FR23)
- Query Parameters: `?status=IN_PROGRESS&department=Engineering&page=0&size=20`
- Response: Paginated list of workflows

**GET /api/workflows/{id}**
- Purpose: Get workflow details
- Response: Complete workflow with tasks

**POST /api/workflows**
- Purpose: Create new workflow (FR1, FR3)
- Request Body:
```json
{
  "workflowTemplateId": "uuid",
  "employeeName": "Jane Smith",
  "employeeEmail": "jane@company.com",
  "role": "Software Engineer",
  "department": "Engineering",
  "startDate": "2025-11-01",
  "customFieldValues": {
    "remote": true,
    "startLocation": "Home Office"
  }
}
```
- Response: 201 Created with workflow object

**PATCH /api/workflows/{id}**
- Purpose: Update workflow status or details
- Request Body: Partial workflow object
- Response: 200 OK with updated workflow

**DELETE /api/workflows/{id}**
- Purpose: Delete workflow (admin only)
- Response: 204 No Content

## Task Endpoints

**GET /api/workflows/{workflowId}/tasks**
- Purpose: Get all tasks for a workflow
- Response: List of tasks

**GET /api/tasks/{id}**
- Purpose: Get task details with checklist
- Response: Task object with checklist items

**PATCH /api/tasks/{id}**
- Purpose: Update task (status, checklist completion, notes)
- Request Body:
```json
{
  "status": "IN_PROGRESS",
  "checklistItems": [
    {"id": "1", "label": "Create AD account", "checked": true},
    {"id": "2", "label": "Assign email", "checked": false}
  ],
  "notes": "Waiting for manager approval"
}
```
- Response: 200 OK with updated task

**POST /api/tasks/{id}/complete**
- Purpose: Mark task as complete (validates all required checklist items)
- Response: 200 OK or 400 Bad Request if validation fails

**POST /api/tasks/{id}/reassign**
- Purpose: Reassign task to different user (FR22)
- Request Body:
```json
{
  "assignedToUserId": "uuid"
}
```
- Response: 200 OK with updated task

## Template Endpoints (HR Admin only)

**GET /api/templates**
- Purpose: Get all workflow templates
- Response: List of templates

**POST /api/templates**
- Purpose: Create new template (FR2)
- Request Body: Template object with task templates
- Response: 201 Created

**PUT /api/templates/{id}**
- Purpose: Update template
- Response: 200 OK

**DELETE /api/templates/{id}**
- Purpose: Deactivate template
- Response: 204 No Content

## Audit Endpoints

**GET /api/audit**
- Purpose: Get audit trail (FR17)
- Query Parameters: `?entityType=WORKFLOW&entityId=uuid&page=0&size=50`
- Response: Paginated audit logs

## Health Check

**GET /api/health**
- Purpose: Health check endpoint
- Response:
```json
{
  "status": "UP",
  "timestamp": "2025-10-22T10:30:00Z"
}
```

## Error Responses

All errors follow standard format:
```json
{
  "timestamp": "2025-10-22T10:30:00Z",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed: All required checklist items must be checked",
  "path": "/api/tasks/123/complete"
}
```

---
