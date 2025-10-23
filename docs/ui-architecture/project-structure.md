# Project Structure

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

## Key Organizational Principles:

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
