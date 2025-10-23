# Employee Lifecycle Frontend

React 18 + TypeScript 5 + Vite frontend application for the Employee Lifecycle Management System.

## Technology Stack

- React 18.3
- TypeScript 5.5
- Vite 5.4 (build tool)
- Material-UI (MUI) 5.16
- Axios 1.7 (HTTP client)
- Vitest (testing)

## Project Structure

```
frontend/
├── src/
│   ├── App.tsx              # Main application component
│   ├── main.tsx             # Application entry point
│   ├── assets/              # Images, fonts, etc.
│   ├── components/          # Reusable components
│   ├── pages/               # Page components
│   ├── services/            # API services
│   └── types/               # TypeScript type definitions
├── public/                  # Static assets
├── vite.config.ts           # Vite configuration
├── tsconfig.json            # TypeScript configuration
└── package.json             # Dependencies
```

## Running the Application

### Prerequisites
- Node.js 18+ installed
- Backend API running on `http://localhost:8080`

### Install Dependencies

```bash
npm install
```

### Development Server

```bash
npm run dev
```

The application will start on `http://localhost:5173`

## Available Scripts

- `npm run dev` - Start development server
- `npm run build` - Build for production
- `npm run preview` - Preview production build
- `npm run lint` - Run ESLint
- `npm test` - Run tests with Vitest

## API Proxy Configuration

The Vite dev server is configured to proxy `/api` requests to the backend:

```typescript
// vite.config.ts
server: {
  proxy: {
    '/api': {
      target: 'http://localhost:8080',
      changeOrigin: true
    }
  }
}
```

This allows the frontend to call `/api/health` which forwards to `http://localhost:8080/api/health`

## Building for Production

```bash
npm run build
```

The production-ready files will be in the `dist/` directory.

To preview the production build:
```bash
npm run preview
```

## Development Guidelines

### TypeScript
- All components should be typed
- No `any` types allowed
- Use interfaces for props

### Components
- Use functional components with hooks
- Use Material-UI components over HTML elements
- Follow naming convention: `ComponentName.tsx`

### State Management
- Local state: `useState`
- Server state: Will use React Query in later stories

### Code Style
- Use Prettier for formatting
- Follow ESLint rules
- Use path aliases: `@/` for imports (configured in tsconfig)

## Troubleshooting

### Development server won't start
- Ensure Node.js 18+ is installed: `node --version`
- Delete `node_modules` and run `npm install` again
- Check for port conflicts on 5173

### API calls failing
- Ensure backend is running on `http://localhost:8080`
- Check browser console for CORS errors
- Verify proxy configuration in `vite.config.ts`

### Build errors
- Run `npm run type-check` to check TypeScript errors
- Ensure all imports are correct
- Clear Vite cache: Delete `node_modules/.vite`

## IDE Setup

### VS Code (Recommended)
Install these extensions:
- ESLint
- Prettier
- TypeScript and JavaScript Language Features
- Vite

### Configuration
VS Code settings are in `.vscode/settings.json` (create if needed):
```json
{
  "editor.formatOnSave": true,
  "editor.defaultFormatter": "esbenp.prettier-vscode"
}
```
