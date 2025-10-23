# Component Standards

## Component Template

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

## Naming Conventions

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
