# Routing

## Route Configuration

```typescript
// src/routes/AppRoutes.tsx
import { Routes, Route, Navigate } from 'react-router-dom';
import { ProtectedRoute } from './ProtectedRoute';
import { MainLayout } from '@/components/layout/MainLayout';

// Pages
import { LoginPage } from '@/pages/LoginPage';
import { DashboardPage } from '@/pages/DashboardPage';
import { OnboardingPage } from '@/pages/OnboardingPage';
import { OffboardingPage } from '@/pages/OffboardingPage';
import { TemplateDesignerPage } from '@/pages/TemplateDesignerPage';
import { TaskDetailPage } from '@/pages/TaskDetailPage';
import { NotFoundPage } from '@/pages/NotFoundPage';

export const AppRoutes = () => {
  return (
    <Routes>
      {/* Public routes */}
      <Route path="/login" element={<LoginPage />} />

      {/* Protected routes with layout */}
      <Route element={<ProtectedRoute />}>
        <Route element={<MainLayout />}>
          <Route path="/" element={<Navigate to="/dashboard" replace />} />
          <Route path="/dashboard" element={<DashboardPage />} />

          {/* Onboarding routes */}
          <Route path="/onboarding" element={<OnboardingPage />} />
          <Route path="/onboarding/:workflowId" element={<OnboardingPage />} />

          {/* Offboarding routes */}
          <Route path="/offboarding" element={<OffboardingPage />} />
          <Route path="/offboarding/:workflowId" element={<OffboardingPage />} />

          {/* Template designer - HR Admin only */}
          <Route
            path="/templates"
            element={
              <ProtectedRoute requiredPermission="manage_templates">
                <TemplateDesignerPage />
              </ProtectedRoute>
            }
          />

          {/* Task detail */}
          <Route path="/tasks/:taskId" element={<TaskDetailPage />} />
        </Route>
      </Route>

      {/* 404 catch-all */}
      <Route path="*" element={<NotFoundPage />} />
    </Routes>
  );
};
```

## Protected Route Component

```typescript
// src/routes/ProtectedRoute.tsx
import { FC, ReactNode } from 'react';
import { Navigate, Outlet, useLocation } from 'react-router-dom';
import { useAuthStore } from '@/store';

interface ProtectedRouteProps {
  children?: ReactNode;
  requiredPermission?: string;
}

/**
 * ProtectedRoute - Wrapper for routes that require authentication
 * Optionally checks for specific permissions
 */
export const ProtectedRoute: FC<ProtectedRouteProps> = ({
  children,
  requiredPermission
}) => {
  const location = useLocation();
  const { isAuthenticated, checkPermission } = useAuthStore();

  // Check if user is authenticated
  if (!isAuthenticated) {
    // Redirect to login, but save the attempted location
    return <Navigate to="/login" state={{ from: location }} replace />;
  }

  // Check for required permission if specified
  if (requiredPermission && !checkPermission(requiredPermission)) {
    // Redirect to dashboard if user lacks permission
    return <Navigate to="/dashboard" replace />;
  }

  // Render children if provided, otherwise render nested routes via Outlet
  return children ? <>{children}</> : <Outlet />;
};
```

## Route Configuration Types

```typescript
// src/routes/routes.config.ts

/**
 * Application route paths
 * Centralized route definitions for type-safe navigation
 */
export const ROUTES = {
  LOGIN: '/login',
  DASHBOARD: '/dashboard',
  ONBOARDING: '/onboarding',
  ONBOARDING_DETAIL: (id: string) => `/onboarding/${id}`,
  OFFBOARDING: '/offboarding',
  OFFBOARDING_DETAIL: (id: string) => `/offboarding/${id}`,
  TEMPLATES: '/templates',
  TASK_DETAIL: (id: string) => `/tasks/${id}`,
} as const;

/**
 * Navigation helper with type-safe routes
 */
export const navigate = {
  toDashboard: () => ROUTES.DASHBOARD,
  toOnboarding: (id?: string) => id ? ROUTES.ONBOARDING_DETAIL(id) : ROUTES.ONBOARDING,
  toOffboarding: (id?: string) => id ? ROUTES.OFFBOARDING_DETAIL(id) : ROUTES.OFFBOARDING,
  toTemplates: () => ROUTES.TEMPLATES,
  toTask: (id: string) => ROUTES.TASK_DETAIL(id),
};
```

## Usage in Components

```typescript
// Using programmatic navigation
import { useNavigate } from 'react-router-dom';
import { navigate } from '@/routes/routes.config';

function MyComponent() {
  const navigateTo = useNavigate();

  const handleViewWorkflow = (workflowId: string) => {
    navigateTo(navigate.toOnboarding(workflowId));
  };

  return <button onClick={() => handleViewWorkflow('123')}>View Workflow</button>;
}
```

---
