# Styling Guidelines

## Styling Approach

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

## Global Theme Variables

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

## MUI Theme Configuration

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

## Component Styling Examples

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

## Styling Best Practices

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
