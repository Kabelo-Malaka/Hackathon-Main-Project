# Environment Configuration

## Environment Variables

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

## Environment-Specific Files

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

## Accessing Environment Variables

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

## Usage in Application

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

## Important Notes:

1. **Vite Prefix**: All custom environment variables must start with `VITE_` to be exposed to client-side code
2. **Security**: Never commit `.env.local` or any file with secrets to version control
3. **Type Safety**: Use the centralized `env` object for type-safe access
4. **Defaults**: Provide sensible defaults for non-critical variables
5. **Validation**: Validate required variables on app startup to fail fast

---
