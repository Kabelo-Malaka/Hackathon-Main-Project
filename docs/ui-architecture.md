# Employee Lifecycle Management System Frontend Architecture Document

## Change Log

| Date | Version | Description | Author |
|------|---------|-------------|--------|
| 2025-10-22 | 1.0 | Initial frontend architecture document | Winston (Architect) |

---

## Template and Framework Selection

**Selected Framework:** Vite + React + TypeScript

**Rationale:**
This project is a web-based Employee Lifecycle Management System requiring rich interactive UI components (drag-and-drop workflow designer, real-time dashboards, complex forms with conditional logic), role-based access control, and responsive design for desktop and tablet devices.

**Why Vite + React + TypeScript:**
- **Vite:** Modern build tool with lightning-fast HMR (Hot Module Replacement), optimized development experience, and efficient production builds
- **React:** Mature ecosystem with excellent libraries for drag-and-drop (dnd-kit), form handling (React Hook Form), real-time data (React Query), and rich component libraries (Material-UI, Ant Design)
- **TypeScript:** Type safety critical for complex workflow logic, task orchestration, and role-based permissions; reduces runtime errors and improves developer experience

**Alternative Considerations:**
- Next.js was considered but SSR/SEO not required for internal enterprise tool
- Vue.js was considered but React has stronger enterprise tooling ecosystem for required features
- Create React App was considered but Vite offers superior build performance

**Starter Template:** None - building from Vite's official React-TypeScript template (`npm create vite@latest`)

---

## Frontend Tech Stack

### Technology Stack Table

| Category | Technology | Version | Purpose | Rationale |
|----------|-----------|---------|---------|-----------|
| **Framework** | React | ^18.3.0 | UI component framework | Industry standard, mature ecosystem, excellent TypeScript support |
| **Language** | TypeScript | ^5.5.0 | Type-safe development | Prevents runtime errors, improves IDE support, critical for complex workflows |
| **Build Tool** | Vite | ^5.4.0 | Development server & bundler | Fast HMR, optimized builds, modern ESM-first approach |
| **UI Library** | Material-UI (MUI) | ^5.16.0 | Component library | Professional enterprise components, accessibility built-in, customizable theming |
| **State Management** | Zustand | ^4.5.0 | Global state management | Lightweight, TypeScript-first, simpler than Redux for this scale |
| **Server State** | TanStack Query (React Query) | ^5.51.0 | Server state caching & synchronization | Automatic refetching, caching, real-time updates, perfect for dashboard |
| **Routing** | React Router | ^6.26.0 | Client-side routing | Standard routing solution, supports protected routes |
| **Form Handling** | React Hook Form | ^7.52.0 | Form state & validation | Performant, minimal re-renders, excellent TypeScript support |
| **Validation** | Zod | ^3.23.0 | Schema validation | Type-safe validation, integrates with React Hook Form |
| **Drag & Drop** | dnd-kit | ^6.1.0 | Drag-and-drop for workflow designer | Modern, accessible, touch-friendly, better than react-dnd |
| **HTTP Client** | Axios | ^1.7.0 | API requests | Interceptors for auth, better error handling than fetch |
| **Date Handling** | date-fns | ^3.6.0 | Date manipulation & formatting | Lightweight, tree-shakeable, TypeScript support |
| **Icons** | MUI Icons | ^5.16.0 | Icon set | Matches Material-UI, comprehensive library |
| **Testing Framework** | Vitest | ^2.0.0 | Unit testing | Native Vite integration, Jest-compatible API, faster |
| **Testing Library** | React Testing Library | ^16.0.0 | Component testing | Best practices for user-centric tests |
| **E2E Testing** | Playwright | ^1.46.0 | End-to-end testing | Cross-browser, reliable, modern alternative to Cypress |
| **Code Quality** | ESLint | ^9.9.0 | Linting | Code quality enforcement |
| **Code Formatting** | Prettier | ^3.3.0 | Code formatting | Consistent code style |

---

## Project Structure

```
employee-lifecycle-frontend/
├── public/                          # Static assets
│   └── favicon.ico
├── src/
│   ├── assets/                      # Images, fonts, static files
│   │   ├── images/
│   │   └── icons/
│   ├── components/                  # Reusable UI components
│   │   ├── common/                  # Generic reusable components
│   │   │   ├── Button/
│   │   │   │   ├── Button.tsx
│   │   │   │   ├── Button.test.tsx
│   │   │   │   └── index.ts
│   │   │   ├── DataTable/
│   │   │   ├── Modal/
│   │   │   └── FormField/
│   │   ├── layout/                  # Layout components
│   │   │   ├── Header/
│   │   │   ├── Sidebar/
│   │   │   ├── Footer/
│   │   │   └── MainLayout/
│   │   └── workflow/                # Domain-specific components
│   │       ├── WorkflowCard/
│   │       ├── TaskChecklist/
│   │       ├── WorkflowDesigner/
│   │       └── StatusBadge/
│   ├── features/                    # Feature-based modules
│   │   ├── auth/                    # Authentication
│   │   │   ├── components/
│   │   │   ├── hooks/
│   │   │   ├── services/
│   │   │   └── types/
│   │   ├── dashboard/               # Dashboard views
│   │   │   ├── components/
│   │   │   ├── hooks/
│   │   │   └── types/
│   │   ├── onboarding/              # Onboarding workflows
│   │   │   ├── components/
│   │   │   ├── hooks/
│   │   │   ├── services/
│   │   │   └── types/
│   │   ├── offboarding/             # Offboarding workflows
│   │   ├── templates/               # Workflow template designer
│   │   └── tasks/                   # Task management
│   ├── hooks/                       # Shared custom hooks
│   │   ├── useAuth.ts
│   │   ├── useDebounce.ts
│   │   └── usePermissions.ts
│   ├── pages/                       # Page components (route views)
│   │   ├── DashboardPage.tsx
│   │   ├── LoginPage.tsx
│   │   ├── OnboardingPage.tsx
│   │   ├── OffboardingPage.tsx
│   │   ├── TemplateDesignerPage.tsx
│   │   ├── TaskDetailPage.tsx
│   │   └── NotFoundPage.tsx
│   ├── services/                    # API services
│   │   ├── api/
│   │   │   ├── client.ts            # Axios configuration
│   │   │   ├── auth.service.ts
│   │   │   ├── workflow.service.ts
│   │   │   ├── task.service.ts
│   │   │   └── employee.service.ts
│   │   └── queryClient.ts           # React Query configuration
│   ├── store/                       # Zustand stores
│   │   ├── authStore.ts
│   │   ├── uiStore.ts
│   │   └── index.ts
│   ├── routes/                      # Routing configuration
│   │   ├── AppRoutes.tsx
│   │   ├── ProtectedRoute.tsx
│   │   └── routes.config.ts
│   ├── types/                       # Shared TypeScript types
│   │   ├── workflow.types.ts
│   │   ├── task.types.ts
│   │   ├── employee.types.ts
│   │   ├── user.types.ts
│   │   └── api.types.ts
│   ├── utils/                       # Utility functions
│   │   ├── formatters.ts
│   │   ├── validators.ts
│   │   ├── constants.ts
│   │   └── helpers.ts
│   ├── theme/                       # MUI theme configuration
│   │   ├── theme.ts
│   │   ├── colors.ts
│   │   └── typography.ts
│   ├── App.tsx                      # Root component
│   ├── main.tsx                     # Entry point
│   └── vite-env.d.ts                # Vite type definitions
├── tests/                           # Test utilities and setup
│   ├── setup.ts
│   └── mocks/
├── .env.example                     # Environment variables template
├── .eslintrc.cjs                    # ESLint configuration
├── .prettierrc                      # Prettier configuration
├── tsconfig.json                    # TypeScript configuration
├── vite.config.ts                   # Vite configuration
├── vitest.config.ts                 # Vitest configuration
├── playwright.config.ts             # Playwright configuration
└── package.json                     # Dependencies
```

### Key Organizational Principles:

**1. Feature-Based Organization:**
- Large domains (auth, onboarding, offboarding) live in `features/`
- Each feature is self-contained with its own components, hooks, services, and types
- Promotes code locality and easier feature deletion/modification

**2. Shared vs. Domain-Specific:**
- `components/common/` - truly generic, reusable across any project
- `components/workflow/` - specific to this domain but used across features
- `features/*/components/` - specific to one feature only

**3. Colocation:**
- Components include their tests in the same directory
- Each component folder has index.ts for clean imports

**4. Separation of Concerns:**
- `pages/` - thin route components, composition only
- `features/` - business logic and feature-specific UI
- `services/` - API communication
- `store/` - client state
- `utils/` - pure functions

---

## Component Standards

### Component Template

```typescript
import { FC } from 'react';

/**
 * Props for the ComponentName component
 */
export interface ComponentNameProps {
  /** Description of prop */
  title: string;
  /** Optional description */
  subtitle?: string;
  /** Callback description */
  onAction?: (id: string) => void;
  /** Child elements */
  children?: React.ReactNode;
}

/**
 * ComponentName - Brief description of what this component does
 *
 * @example
 * ```tsx
 * <ComponentName
 *   title="Hello"
 *   subtitle="World"
 *   onAction={(id) => console.log(id)}
 * />
 * ```
 */
export const ComponentName: FC<ComponentNameProps> = ({
  title,
  subtitle,
  onAction,
  children,
}) => {
  // Event handlers
  const handleClick = () => {
    onAction?.('example-id');
  };

  // Render
  return (
    <div>
      <h2>{title}</h2>
      {subtitle && <p>{subtitle}</p>}
      <button onClick={handleClick}>Action</button>
      {children}
    </div>
  );
};
```

### Naming Conventions

**Files and Folders:**
- **Components:** PascalCase for both file and component name
  - `Button.tsx` exports `Button`
  - `WorkflowCard.tsx` exports `WorkflowCard`
  - Folder: `Button/` contains `Button.tsx`, `Button.test.tsx`, `index.ts`

- **Hooks:** camelCase starting with "use"
  - `useAuth.ts` exports `useAuth`
  - `useWorkflowStatus.ts` exports `useWorkflowStatus`

- **Services:** camelCase with ".service.ts" suffix
  - `auth.service.ts` exports auth-related API functions
  - `workflow.service.ts` exports workflow API functions

- **Types:** PascalCase with ".types.ts" suffix
  - `workflow.types.ts` exports `Workflow`, `WorkflowStatus`, etc.
  - Interface names: `User`, `Employee`, `Task` (no "I" prefix)
  - Type names: `WorkflowType`, `TaskStatus` (descriptive)

- **Stores:** camelCase with "Store" suffix
  - `authStore.ts` exports `useAuthStore`
  - `uiStore.ts` exports `useUIStore`

- **Pages:** PascalCase with "Page" suffix
  - `DashboardPage.tsx` exports `DashboardPage`
  - `OnboardingPage.tsx` exports `OnboardingPage`

- **Utils:** camelCase, descriptive names
  - `formatters.ts` exports `formatDate`, `formatCurrency`
  - `validators.ts` exports `validateEmail`, `validateWorkflow`

**Code Naming:**
- **Props interfaces:** `ComponentNameProps`
- **Event handlers:** `handleActionName` (e.g., `handleSubmit`, `handleClick`)
- **Boolean props/variables:** `isLoading`, `hasError`, `canEdit`, `shouldShow`
- **Constants:** UPPER_SNAKE_CASE for true constants
  - `const MAX_RETRIES = 3;`
  - `const API_BASE_URL = import.meta.env.VITE_API_URL;`

**Component Organization Pattern:**
```
Button/
├── Button.tsx           # Main component
├── Button.test.tsx      # Tests
├── Button.styles.ts     # Styles (if using styled-components, optional with MUI)
└── index.ts             # Clean export (export { Button } from './Button')
```

---

## State Management

### Store Structure

```
src/store/
├── authStore.ts         # Authentication state (user, token, permissions)
├── uiStore.ts           # UI state (sidebar, modals, theme)
└── index.ts             # Central export
```

### State Management Template - Zustand Store

```typescript
import { create } from 'zustand';
import { devtools, persist } from 'zustand/middleware';

/**
 * Auth store state interface
 */
interface AuthState {
  // State
  user: User | null;
  token: string | null;
  isAuthenticated: boolean;

  // Actions
  setUser: (user: User) => void;
  setToken: (token: string) => void;
  logout: () => void;
  checkPermission: (permission: string) => boolean;
}

/**
 * Authentication store
 * Manages user authentication state and permissions
 */
export const useAuthStore = create<AuthState>()(
  devtools(
    persist(
      (set, get) => ({
        // Initial state
        user: null,
        token: null,
        isAuthenticated: false,

        // Actions
        setUser: (user) =>
          set({ user, isAuthenticated: true }, false, 'auth/setUser'),

        setToken: (token) =>
          set({ token }, false, 'auth/setToken'),

        logout: () =>
          set(
            { user: null, token: null, isAuthenticated: false },
            false,
            'auth/logout'
          ),

        checkPermission: (permission) => {
          const { user } = get();
          return user?.permissions?.includes(permission) ?? false;
        },
      }),
      {
        name: 'auth-storage', // localStorage key
        partialize: (state) => ({
          token: state.token, // Only persist token
        }),
      }
    ),
    { name: 'AuthStore' }
  )
);
```

### UI Store Example

```typescript
import { create } from 'zustand';
import { devtools } from 'zustand/middleware';

interface UIState {
  // State
  sidebarOpen: boolean;
  theme: 'light' | 'dark';
  activeModal: string | null;

  // Actions
  toggleSidebar: () => void;
  setSidebarOpen: (open: boolean) => void;
  setTheme: (theme: 'light' | 'dark') => void;
  openModal: (modalId: string) => void;
  closeModal: () => void;
}

export const useUIStore = create<UIState>()(
  devtools(
    (set) => ({
      // Initial state
      sidebarOpen: true,
      theme: 'light',
      activeModal: null,

      // Actions
      toggleSidebar: () =>
        set((state) => ({ sidebarOpen: !state.sidebarOpen }), false, 'ui/toggleSidebar'),

      setSidebarOpen: (open) =>
        set({ sidebarOpen: open }, false, 'ui/setSidebarOpen'),

      setTheme: (theme) =>
        set({ theme }, false, 'ui/setTheme'),

      openModal: (modalId) =>
        set({ activeModal: modalId }, false, 'ui/openModal'),

      closeModal: () =>
        set({ activeModal: null }, false, 'ui/closeModal'),
    }),
    { name: 'UIStore' }
  )
);
```

### Server State - React Query Configuration

```typescript
// src/services/queryClient.ts
import { QueryClient } from '@tanstack/react-query';

export const queryClient = new QueryClient({
  defaultOptions: {
    queries: {
      staleTime: 1000 * 60 * 5, // 5 minutes
      gcTime: 1000 * 60 * 10, // 10 minutes (formerly cacheTime)
      retry: 3,
      refetchOnWindowFocus: true,
      refetchOnReconnect: true,
    },
    mutations: {
      retry: 1,
    },
  },
});
```

### Usage Patterns

**Using Zustand Store:**
```typescript
// In a component
import { useAuthStore } from '@/store';

function MyComponent() {
  // Select specific state (causes re-render only when this changes)
  const user = useAuthStore((state) => state.user);
  const logout = useAuthStore((state) => state.logout);

  // Or destructure multiple values
  const { isAuthenticated, checkPermission } = useAuthStore();

  return (
    <div>
      {isAuthenticated && <p>Welcome {user?.name}</p>}
      <button onClick={logout}>Logout</button>
    </div>
  );
}
```

**Using React Query for Server State:**
```typescript
// In a component
import { useQuery, useMutation } from '@tanstack/react-query';
import { workflowService } from '@/services/api/workflow.service';

function WorkflowList() {
  // Fetch workflows
  const { data: workflows, isLoading, error } = useQuery({
    queryKey: ['workflows'],
    queryFn: workflowService.getAll,
  });

  // Mutation for creating workflow
  const createMutation = useMutation({
    mutationFn: workflowService.create,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['workflows'] });
    },
  });

  if (isLoading) return <div>Loading...</div>;
  if (error) return <div>Error: {error.message}</div>;

  return (
    <div>
      {workflows?.map(workflow => (
        <div key={workflow.id}>{workflow.name}</div>
      ))}
    </div>
  );
}
```

**State Separation Pattern:**
- **Zustand:** UI state, auth state, client-only data
- **React Query:** All server data (workflows, tasks, employees)
- Never duplicate server data in Zustand

---

## API Integration

### Service Template

```typescript
// src/services/api/workflow.service.ts
import { apiClient } from './client';
import type { Workflow, CreateWorkflowDTO, UpdateWorkflowDTO } from '@/types/workflow.types';
import type { ApiResponse, PaginatedResponse } from '@/types/api.types';

/**
 * Workflow API Service
 * Handles all workflow-related API calls
 */
export const workflowService = {
  /**
   * Get all workflows with optional filtering
   */
  getAll: async (params?: {
    status?: string;
    page?: number;
    limit?: number;
  }): Promise<PaginatedResponse<Workflow>> => {
    const response = await apiClient.get<PaginatedResponse<Workflow>>('/workflows', {
      params,
    });
    return response.data;
  },

  /**
   * Get a single workflow by ID
   */
  getById: async (id: string): Promise<Workflow> => {
    const response = await apiClient.get<ApiResponse<Workflow>>(`/workflows/${id}`);
    return response.data.data;
  },

  /**
   * Create a new workflow
   */
  create: async (data: CreateWorkflowDTO): Promise<Workflow> => {
    const response = await apiClient.post<ApiResponse<Workflow>>('/workflows', data);
    return response.data.data;
  },

  /**
   * Update an existing workflow
   */
  update: async (id: string, data: UpdateWorkflowDTO): Promise<Workflow> => {
    const response = await apiClient.patch<ApiResponse<Workflow>>(`/workflows/${id}`, data);
    return response.data.data;
  },

  /**
   * Delete a workflow
   */
  delete: async (id: string): Promise<void> => {
    await apiClient.delete(`/workflows/${id}`);
  },

  /**
   * Initiate a workflow for an employee
   */
  initiate: async (workflowId: string, employeeId: string): Promise<Workflow> => {
    const response = await apiClient.post<ApiResponse<Workflow>>(
      `/workflows/${workflowId}/initiate`,
      { employeeId }
    );
    return response.data.data;
  },
};
```

### API Client Configuration

```typescript
// src/services/api/client.ts
import axios, { AxiosError, InternalAxiosRequestConfig } from 'axios';
import { useAuthStore } from '@/store';

/**
 * Base API URL from environment variables
 */
const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:3000/api';

/**
 * Axios instance with default configuration
 */
export const apiClient = axios.create({
  baseURL: API_BASE_URL,
  timeout: 30000, // 30 seconds
  headers: {
    'Content-Type': 'application/json',
  },
});

/**
 * Request interceptor - Add auth token to all requests
 */
apiClient.interceptors.request.use(
  (config: InternalAxiosRequestConfig) => {
    const token = useAuthStore.getState().token;

    if (token && config.headers) {
      config.headers.Authorization = `Bearer ${token}`;
    }

    return config;
  },
  (error: AxiosError) => {
    return Promise.reject(error);
  }
);

/**
 * Response interceptor - Handle common errors
 */
apiClient.interceptors.response.use(
  (response) => response,
  (error: AxiosError) => {
    // Handle 401 Unauthorized - logout user
    if (error.response?.status === 401) {
      useAuthStore.getState().logout();
      window.location.href = '/login';
    }

    // Handle 403 Forbidden
    if (error.response?.status === 403) {
      console.error('Insufficient permissions');
    }

    // Handle 500 Server Error
    if (error.response?.status === 500) {
      console.error('Server error occurred');
    }

    return Promise.reject(error);
  }
);
```

### TypeScript API Types

```typescript
// src/types/api.types.ts

/**
 * Standard API response wrapper
 */
export interface ApiResponse<T> {
  success: boolean;
  data: T;
  message?: string;
  timestamp: string;
}

/**
 * Paginated response for list endpoints
 */
export interface PaginatedResponse<T> {
  data: T[];
  pagination: {
    page: number;
    limit: number;
    total: number;
    totalPages: number;
  };
}

/**
 * API error response
 */
export interface ApiError {
  success: false;
  error: {
    code: string;
    message: string;
    details?: Record<string, string[]>;
  };
  timestamp: string;
}
```

### React Query Integration Example

```typescript
// Custom hook for workflows
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { workflowService } from '@/services/api/workflow.service';

export const useWorkflows = (params?: { status?: string }) => {
  return useQuery({
    queryKey: ['workflows', params],
    queryFn: () => workflowService.getAll(params),
  });
};

export const useWorkflow = (id: string) => {
  return useQuery({
    queryKey: ['workflows', id],
    queryFn: () => workflowService.getById(id),
    enabled: !!id, // Only run if id exists
  });
};

export const useCreateWorkflow = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: workflowService.create,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['workflows'] });
    },
  });
};
```

---

## Routing

### Route Configuration

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

### Protected Route Component

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

### Route Configuration Types

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

### Usage in Components

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

## Styling Guidelines

### Styling Approach

**Primary Method: Material-UI (MUI) with sx prop and styled components**

Material-UI provides a complete design system with pre-built accessible components, built-in theming, responsive utilities, and TypeScript support.

**When to use each approach:**

1. **MUI `sx` prop** - For simple, one-off styles and responsive designs
   ```typescript
   <Box sx={{ padding: 2, backgroundColor: 'primary.main' }}>Content</Box>
   ```

2. **MUI `styled()` API** - For reusable styled components
   ```typescript
   const StyledCard = styled(Card)(({ theme }) => ({
     padding: theme.spacing(2),
     borderRadius: theme.shape.borderRadius,
   }));
   ```

3. **CSS Modules** - For complex component-specific styles (optional, rarely needed with MUI)

### Global Theme Variables

```css
/* src/theme/globals.css */
:root {
  /* Brand Colors */
  --color-primary-main: #1976d2;
  --color-primary-light: #42a5f5;
  --color-primary-dark: #1565c0;
  --color-secondary-main: #9c27b0;
  --color-secondary-light: #ba68c8;
  --color-secondary-dark: #7b1fa2;

  /* Status Colors */
  --color-success: #2e7d32;
  --color-warning: #ed6c02;
  --color-error: #d32f2f;
  --color-info: #0288d1;

  /* Workflow Status Colors */
  --color-status-not-started: #9e9e9e;
  --color-status-in-progress: #1976d2;
  --color-status-blocked: #ed6c02;
  --color-status-complete: #2e7d32;

  /* Neutral Colors */
  --color-grey-50: #fafafa;
  --color-grey-100: #f5f5f5;
  --color-grey-200: #eeeeee;
  --color-grey-300: #e0e0e0;
  --color-grey-400: #bdbdbd;
  --color-grey-500: #9e9e9e;
  --color-grey-600: #757575;
  --color-grey-700: #616161;
  --color-grey-800: #424242;
  --color-grey-900: #212121;

  /* Spacing (8px base) */
  --spacing-xs: 4px;
  --spacing-sm: 8px;
  --spacing-md: 16px;
  --spacing-lg: 24px;
  --spacing-xl: 32px;
  --spacing-xxl: 48px;

  /* Typography */
  --font-family: 'Roboto', 'Helvetica', 'Arial', sans-serif;
  --font-size-xs: 0.75rem;    /* 12px */
  --font-size-sm: 0.875rem;   /* 14px */
  --font-size-base: 1rem;      /* 16px */
  --font-size-lg: 1.125rem;    /* 18px */
  --font-size-xl: 1.25rem;     /* 20px */
  --font-size-xxl: 1.5rem;     /* 24px */
  --font-size-xxxl: 2rem;      /* 32px */

  /* Shadows */
  --shadow-sm: 0 1px 2px 0 rgba(0, 0, 0, 0.05);
  --shadow-md: 0 4px 6px -1px rgba(0, 0, 0, 0.1);
  --shadow-lg: 0 10px 15px -3px rgba(0, 0, 0, 0.1);
  --shadow-xl: 0 20px 25px -5px rgba(0, 0, 0, 0.1);

  /* Border Radius */
  --radius-sm: 4px;
  --radius-md: 8px;
  --radius-lg: 12px;
  --radius-xl: 16px;

  /* Transitions */
  --transition-fast: 150ms ease-in-out;
  --transition-base: 250ms ease-in-out;
  --transition-slow: 350ms ease-in-out;
}

/* Dark mode variables */
[data-theme='dark'] {
  --color-primary-main: #90caf9;
  --color-primary-light: #e3f2fd;
  --color-primary-dark: #42a5f5;

  --color-grey-50: #212121;
  --color-grey-100: #424242;
  --color-grey-200: #616161;
  --color-grey-300: #757575;
  --color-grey-400: #9e9e9e;
  --color-grey-500: #bdbdbd;
  --color-grey-600: #e0e0e0;
  --color-grey-700: #eeeeee;
  --color-grey-800: #f5f5f5;
  --color-grey-900: #fafafa;
}
```

### MUI Theme Configuration

```typescript
// src/theme/theme.ts
import { createTheme } from '@mui/material/styles';

export const theme = createTheme({
  palette: {
    primary: {
      main: '#1976d2',
      light: '#42a5f5',
      dark: '#1565c0',
    },
    secondary: {
      main: '#9c27b0',
      light: '#ba68c8',
      dark: '#7b1fa2',
    },
    success: {
      main: '#2e7d32',
    },
    warning: {
      main: '#ed6c02',
    },
    error: {
      main: '#d32f2f',
    },
    grey: {
      50: '#fafafa',
      100: '#f5f5f5',
      200: '#eeeeee',
      300: '#e0e0e0',
      400: '#bdbdbd',
      500: '#9e9e9e',
      600: '#757575',
      700: '#616161',
      800: '#424242',
      900: '#212121',
    },
  },
  typography: {
    fontFamily: '"Roboto", "Helvetica", "Arial", sans-serif',
    h1: {
      fontSize: '2rem',
      fontWeight: 600,
    },
    h2: {
      fontSize: '1.5rem',
      fontWeight: 600,
    },
    h3: {
      fontSize: '1.25rem',
      fontWeight: 600,
    },
    body1: {
      fontSize: '1rem',
    },
    body2: {
      fontSize: '0.875rem',
    },
  },
  spacing: 8, // Base spacing unit (8px)
  shape: {
    borderRadius: 8,
  },
  components: {
    MuiButton: {
      styleOverrides: {
        root: {
          textTransform: 'none', // Disable uppercase text
          fontWeight: 500,
        },
      },
    },
    MuiCard: {
      styleOverrides: {
        root: {
          borderRadius: 12,
        },
      },
    },
  },
});
```

### Component Styling Examples

```typescript
// Example 1: Using sx prop
import { Box, Typography } from '@mui/material';

export const SimpleComponent = () => (
  <Box
    sx={{
      padding: 2,
      backgroundColor: 'grey.100',
      borderRadius: 2,
      '&:hover': {
        backgroundColor: 'grey.200',
      },
    }}
  >
    <Typography variant="h3">Title</Typography>
  </Box>
);

// Example 2: Using styled API
import { styled } from '@mui/material/styles';
import { Card } from '@mui/material';

const WorkflowCard = styled(Card)(({ theme }) => ({
  padding: theme.spacing(2),
  marginBottom: theme.spacing(2),
  transition: theme.transitions.create(['box-shadow', 'transform']),
  '&:hover': {
    boxShadow: theme.shadows[4],
    transform: 'translateY(-2px)',
  },
}));
```

### Styling Best Practices

1. **Use MUI theme values** - Always reference theme colors, spacing, and breakpoints
2. **Avoid inline pixel values** - Use spacing multipliers: `padding: 2` = 16px
3. **Leverage theme variants** - Use `variant` prop on Typography, Button, etc.
4. **Responsive design** - Use MUI breakpoints:
   ```typescript
   sx={{
     fontSize: { xs: '0.875rem', md: '1rem' },
     padding: { xs: 1, sm: 2, md: 3 },
   }}
   ```
5. **Status colors** - Use consistent colors for workflow statuses
6. **Accessibility** - MUI components are accessible by default, maintain this

---

## Testing Requirements

### Component Test Template

```typescript
// src/components/workflow/WorkflowCard/WorkflowCard.test.tsx
import { describe, it, expect, vi } from 'vitest';
import { render, screen, fireEvent } from '@testing-library/react';
import { WorkflowCard } from './WorkflowCard';
import type { Workflow } from '@/types/workflow.types';

// Mock workflow data
const mockWorkflow: Workflow = {
  id: '123',
  name: 'Developer Onboarding',
  status: 'in_progress',
  employeeName: 'John Doe',
  startDate: '2025-10-22',
  completionPercentage: 65,
};

describe('WorkflowCard', () => {
  it('renders workflow information correctly', () => {
    render(<WorkflowCard workflow={mockWorkflow} />);

    expect(screen.getByText('Developer Onboarding')).toBeInTheDocument();
    expect(screen.getByText('John Doe')).toBeInTheDocument();
    expect(screen.getByText('65%')).toBeInTheDocument();
  });

  it('displays correct status badge', () => {
    render(<WorkflowCard workflow={mockWorkflow} />);

    const statusBadge = screen.getByText('In Progress');
    expect(statusBadge).toBeInTheDocument();
  });

  it('calls onClick handler when card is clicked', () => {
    const handleClick = vi.fn();
    render(<WorkflowCard workflow={mockWorkflow} onClick={handleClick} />);

    const card = screen.getByRole('button');
    fireEvent.click(card);

    expect(handleClick).toHaveBeenCalledWith('123');
  });

  it('applies hover state styles', () => {
    render(<WorkflowCard workflow={mockWorkflow} />);

    const card = screen.getByRole('button');
    fireEvent.mouseEnter(card);

    expect(card).toHaveClass('hover');
  });

  it('renders correctly with different workflow statuses', () => {
    const completedWorkflow = { ...mockWorkflow, status: 'completed' as const };
    const { rerender } = render(<WorkflowCard workflow={completedWorkflow} />);

    expect(screen.getByText('Completed')).toBeInTheDocument();

    const blockedWorkflow = { ...mockWorkflow, status: 'blocked' as const };
    rerender(<WorkflowCard workflow={blockedWorkflow} />);

    expect(screen.getByText('Blocked')).toBeInTheDocument();
  });
});
```

### Testing Best Practices

1. **Unit Tests**: Test individual components in isolation
2. **Integration Tests**: Test component interactions
3. **E2E Tests**: Test critical user flows (using Cypress/Playwright)
4. **Coverage Goals**: Aim for 80% code coverage
5. **Test Structure**: Arrange-Act-Assert pattern
6. **Mock External Dependencies**: API calls, routing, state management

### Test Setup Configuration

```typescript
// tests/setup.ts
import { expect, afterEach } from 'vitest';
import { cleanup } from '@testing-library/react';
import matchers from '@testing-library/jest-dom/matchers';

// Extend Vitest's expect with jest-dom matchers
expect.extend(matchers);

// Cleanup after each test
afterEach(() => {
  cleanup();
});

// Mock window.matchMedia for responsive tests
Object.defineProperty(window, 'matchMedia', {
  writable: true,
  value: vi.fn().mockImplementation((query) => ({
    matches: false,
    media: query,
    onchange: null,
    addListener: vi.fn(),
    removeListener: vi.fn(),
    addEventListener: vi.fn(),
    removeEventListener: vi.fn(),
    dispatchEvent: vi.fn(),
  })),
});
```

### Testing Hooks with React Query

```typescript
// tests/hooks/useWorkflows.test.ts
import { describe, it, expect, vi } from 'vitest';
import { renderHook, waitFor } from '@testing-library/react';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import { useWorkflows } from '@/features/dashboard/hooks/useWorkflows';
import { workflowService } from '@/services/api/workflow.service';

// Create wrapper for React Query
const createWrapper = () => {
  const queryClient = new QueryClient({
    defaultOptions: {
      queries: { retry: false },
    },
  });
  return ({ children }: { children: React.ReactNode }) => (
    <QueryClientProvider client={queryClient}>{children}</QueryClientProvider>
  );
};

vi.mock('@/services/api/workflow.service');

describe('useWorkflows', () => {
  it('fetches workflows successfully', async () => {
    const mockWorkflows = [
      { id: '1', name: 'Workflow 1', status: 'in_progress' },
      { id: '2', name: 'Workflow 2', status: 'completed' },
    ];

    vi.mocked(workflowService.getAll).mockResolvedValue({
      data: mockWorkflows,
      pagination: { page: 1, limit: 10, total: 2, totalPages: 1 },
    });

    const { result } = renderHook(() => useWorkflows(), {
      wrapper: createWrapper(),
    });

    await waitFor(() => expect(result.current.isSuccess).toBe(true));

    expect(result.current.data?.data).toEqual(mockWorkflows);
  });

  it('handles errors correctly', async () => {
    vi.mocked(workflowService.getAll).mockRejectedValue(new Error('API Error'));

    const { result } = renderHook(() => useWorkflows(), {
      wrapper: createWrapper(),
    });

    await waitFor(() => expect(result.current.isError).toBe(true));

    expect(result.current.error).toBeDefined();
  });
});
```

### E2E Test Example (Playwright)

```typescript
// e2e/onboarding.spec.ts
import { test, expect } from '@playwright/test';

test.describe('Onboarding Workflow', () => {
  test.beforeEach(async ({ page }) => {
    // Login before each test
    await page.goto('/login');
    await page.fill('[name="email"]', 'admin@example.com');
    await page.fill('[name="password"]', 'password123');
    await page.click('button[type="submit"]');
    await expect(page).toHaveURL('/dashboard');
  });

  test('creates new onboarding workflow', async ({ page }) => {
    // Navigate to onboarding page
    await page.click('text=New Onboarding');

    // Fill out onboarding form
    await page.fill('[name="employeeName"]', 'Jane Smith');
    await page.fill('[name="role"]', 'Software Engineer');
    await page.fill('[name="startDate"]', '2025-11-01');
    await page.selectOption('[name="department"]', 'Engineering');

    // Submit form
    await page.click('button:has-text("Create Workflow")');

    // Verify workflow was created
    await expect(page).toHaveURL(/\/onboarding\/\w+/);
    await expect(page.locator('h1')).toContainText('Jane Smith');
  });

  test('completes task checklist', async ({ page }) => {
    // Navigate to existing workflow
    await page.goto('/onboarding/workflow-123');

    // Find and check first unchecked task
    const firstCheckbox = page.locator('input[type="checkbox"]').first();
    await firstCheckbox.check();

    // Verify task is checked
    await expect(firstCheckbox).toBeChecked();

    // Verify progress updated
    const progressBar = page.locator('[role="progressbar"]');
    await expect(progressBar).toHaveAttribute('aria-valuenow', '20');
  });
});
```

---

## Environment Configuration

### Environment Variables

```bash
# .env.example - Copy this to .env.local and fill in values

# API Configuration
VITE_API_BASE_URL=http://localhost:3000/api
VITE_API_TIMEOUT=30000

# Authentication
VITE_AUTH_TOKEN_KEY=auth_token
VITE_TOKEN_REFRESH_ENABLED=true

# Feature Flags
VITE_ENABLE_DARK_MODE=true
VITE_ENABLE_ANALYTICS=false

# Environment
VITE_APP_ENV=development
VITE_APP_VERSION=1.0.0

# Optional: Third-party integrations
VITE_SENTRY_DSN=
VITE_ANALYTICS_ID=
```

### Environment-Specific Files

**Development** (`.env.development`)
```bash
VITE_API_BASE_URL=http://localhost:3000/api
VITE_APP_ENV=development
VITE_ENABLE_ANALYTICS=false
```

**Production** (`.env.production`)
```bash
VITE_API_BASE_URL=https://api.yourcompany.com/api
VITE_APP_ENV=production
VITE_ENABLE_ANALYTICS=true
```

### Accessing Environment Variables

```typescript
// src/utils/env.ts

/**
 * Type-safe environment variable access
 */
export const env = {
  apiBaseUrl: import.meta.env.VITE_API_BASE_URL || 'http://localhost:3000/api',
  apiTimeout: Number(import.meta.env.VITE_API_TIMEOUT) || 30000,
  authTokenKey: import.meta.env.VITE_AUTH_TOKEN_KEY || 'auth_token',
  appEnv: import.meta.env.VITE_APP_ENV || 'development',
  appVersion: import.meta.env.VITE_APP_VERSION || '1.0.0',
  isDevelopment: import.meta.env.DEV,
  isProduction: import.meta.env.PROD,
  enableDarkMode: import.meta.env.VITE_ENABLE_DARK_MODE === 'true',
  enableAnalytics: import.meta.env.VITE_ENABLE_ANALYTICS === 'true',
} as const;

/**
 * Validate required environment variables
 */
export const validateEnv = () => {
  const required = ['VITE_API_BASE_URL'];
  const missing = required.filter((key) => !import.meta.env[key]);

  if (missing.length > 0) {
    throw new Error(
      `Missing required environment variables: ${missing.join(', ')}`
    );
  }
};
```

### Usage in Application

```typescript
// src/main.tsx
import { validateEnv } from '@/utils/env';

// Validate environment variables on startup
try {
  validateEnv();
} catch (error) {
  console.error('Environment validation failed:', error);
}
```

### Important Notes:

1. **Vite Prefix**: All custom environment variables must start with `VITE_` to be exposed to client-side code
2. **Security**: Never commit `.env.local` or any file with secrets to version control
3. **Type Safety**: Use the centralized `env` object for type-safe access
4. **Defaults**: Provide sensible defaults for non-critical variables
5. **Validation**: Validate required variables on app startup to fail fast

---

## Frontend Developer Standards

### Critical Coding Rules

**Rules that prevent common AI and developer mistakes:**

1. **NEVER mutate state directly** - Always use state setters or immutable updates
   ```typescript
   // ❌ BAD
   user.name = 'New Name';
   setUser(user);

   // ✅ GOOD
   setUser({ ...user, name: 'New Name' });
   ```

2. **ALWAYS handle loading and error states** - Every data fetch must handle all states
   ```typescript
   // ❌ BAD
   const { data } = useQuery({ queryKey: ['workflows'], queryFn: getWorkflows });
   return <div>{data.map(...)}</div>; // Will crash if data is undefined

   // ✅ GOOD
   const { data, isLoading, error } = useQuery({ queryKey: ['workflows'], queryFn: getWorkflows });
   if (isLoading) return <Loading />;
   if (error) return <Error message={error.message} />;
   return <div>{data?.map(...)}</div>;
   ```

3. **ALWAYS use optional chaining for nested properties**
   ```typescript
   // ❌ BAD
   const email = user.profile.contact.email;

   // ✅ GOOD
   const email = user?.profile?.contact?.email;
   ```

4. **NEVER use `any` type** - Use `unknown` or proper types
   ```typescript
   // ❌ BAD
   const handleData = (data: any) => { ... }

   // ✅ GOOD
   const handleData = (data: Workflow) => { ... }
   // or
   const handleData = (data: unknown) => {
     if (isWorkflow(data)) { ... }
   }
   ```

5. **ALWAYS clean up side effects** - Return cleanup functions from useEffect
   ```typescript
   // ❌ BAD
   useEffect(() => {
     const interval = setInterval(() => fetchData(), 5000);
   }, []);

   // ✅ GOOD
   useEffect(() => {
     const interval = setInterval(() => fetchData(), 5000);
     return () => clearInterval(interval);
   }, []);
   ```

6. **NEVER use array index as key** - Use unique identifiers
   ```typescript
   // ❌ BAD
   {items.map((item, index) => <Item key={index} {...item} />)}

   // ✅ GOOD
   {items.map((item) => <Item key={item.id} {...item} />)}
   ```

7. **ALWAYS define prop types with interfaces** - Export interfaces for reusability
   ```typescript
   // ❌ BAD
   const Button = ({ label, onClick }: { label: string; onClick: () => void }) => { ... }

   // ✅ GOOD
   export interface ButtonProps {
     label: string;
     onClick: () => void;
   }
   export const Button: FC<ButtonProps> = ({ label, onClick }) => { ... }
   ```

8. **NEVER fetch data in components** - Use React Query hooks
   ```typescript
   // ❌ BAD
   const [workflows, setWorkflows] = useState([]);
   useEffect(() => {
     fetch('/api/workflows').then(res => res.json()).then(setWorkflows);
   }, []);

   // ✅ GOOD
   const { data: workflows } = useQuery({
     queryKey: ['workflows'],
     queryFn: workflowService.getAll,
   });
   ```

9. **ALWAYS use path aliases (@/) for imports** - Never use relative paths beyond parent
   ```typescript
   // ❌ BAD
   import { Button } from '../../../components/common/Button';

   // ✅ GOOD
   import { Button } from '@/components/common/Button';
   ```

10. **NEVER hardcode API URLs** - Use environment variables
    ```typescript
    // ❌ BAD
    axios.get('http://localhost:3000/api/workflows');

    // ✅ GOOD
    import { env } from '@/utils/env';
    axios.get(`${env.apiBaseUrl}/workflows`);
    ```

11. **ALWAYS use MUI components over HTML elements** - Leverage the component library
    ```typescript
    // ❌ BAD
    <div><button onClick={handleClick}>Click</button></div>

    // ✅ GOOD
    <Box><Button onClick={handleClick}>Click</Button></Box>
    ```

12. **NEVER commit console.log statements** - Use proper debugging or remove before commit
    ```typescript
    // ❌ BAD
    console.log('User data:', user);

    // ✅ GOOD
    // Remove debug statements, or use:
    if (env.isDevelopment) console.debug('User data:', user);
    ```

### Quick Reference

**Common Commands:**
```bash
# Development
npm run dev              # Start dev server (http://localhost:5173)
npm run build            # Build for production
npm run preview          # Preview production build

# Testing
npm run test             # Run unit tests
npm run test:watch       # Run tests in watch mode
npm run test:coverage    # Generate coverage report
npm run test:e2e         # Run E2E tests with Playwright

# Code Quality
npm run lint             # Run ESLint
npm run lint:fix         # Fix ESLint errors
npm run format           # Format code with Prettier
npm run type-check       # TypeScript type checking
```

**Key Import Patterns:**
```typescript
// Components
import { Button, TextField, Box, Typography } from '@mui/material';
import { MyComponent } from '@/components/common/MyComponent';

// Hooks
import { useState, useEffect } from 'react';
import { useQuery, useMutation } from '@tanstack/react-query';
import { useAuthStore } from '@/store';

// Services
import { workflowService } from '@/services/api/workflow.service';

// Types
import type { Workflow } from '@/types/workflow.types';

// Routing
import { useNavigate, useParams } from 'react-router-dom';
import { navigate } from '@/routes/routes.config';
```

**File Naming Conventions:**
- Components: `PascalCase.tsx` (Button.tsx, WorkflowCard.tsx)
- Hooks: `camelCase.ts` (useAuth.ts, useWorkflows.ts)
- Services: `camelCase.service.ts` (workflow.service.ts)
- Types: `PascalCase.types.ts` (workflow.types.ts)
- Pages: `PascalCasePage.tsx` (DashboardPage.tsx)

**Project-Specific Patterns:**

1. **Creating a new page:**
   - Add page component to `src/pages/`
   - Add route to `src/routes/AppRoutes.tsx`
   - Add route path to `src/routes/routes.config.ts`

2. **Creating a new API service:**
   - Create service file in `src/services/api/`
   - Define types in `src/types/`
   - Create React Query hook in feature folder

3. **Adding a new feature:**
   - Create folder in `src/features/`
   - Include: components/, hooks/, services/, types/
   - Export main components via index.ts

4. **Creating reusable components:**
   - Add to `src/components/common/` if generic
   - Add to `src/components/workflow/` if domain-specific
   - Include: Component.tsx, Component.test.tsx, index.ts

---

**End of Document**

