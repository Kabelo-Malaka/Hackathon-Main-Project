# Frontend Developer Standards

## Critical Coding Rules

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

## Quick Reference

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

