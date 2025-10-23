# Testing Requirements

## Component Test Template

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

## Testing Best Practices

1. **Unit Tests**: Test individual components in isolation
2. **Integration Tests**: Test component interactions
3. **E2E Tests**: Test critical user flows (using Cypress/Playwright)
4. **Coverage Goals**: Aim for 80% code coverage
5. **Test Structure**: Arrange-Act-Assert pattern
6. **Mock External Dependencies**: API calls, routing, state management

## Test Setup Configuration

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

## Testing Hooks with React Query

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

## E2E Test Example (Playwright)

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
