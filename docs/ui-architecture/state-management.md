# State Management

## Store Structure

```
src/store/
├── authStore.ts         # Authentication state (user, token, permissions)
├── uiStore.ts           # UI state (sidebar, modals, theme)
└── index.ts             # Central export
```

## State Management Template - Zustand Store

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

## UI Store Example

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

## Server State - React Query Configuration

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

## Usage Patterns

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
