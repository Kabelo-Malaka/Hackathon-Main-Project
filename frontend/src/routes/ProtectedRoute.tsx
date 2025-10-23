import type { FC, ReactNode } from 'react';
import { Navigate, Outlet, useLocation } from 'react-router-dom';
import { useAuthStore } from '../store/authStore';

interface ProtectedRouteProps {
  children?: ReactNode;
  requiredRole?: string;
}

/**
 * Protected route component that redirects unauthenticated users to login.
 * Optionally checks for required role (for future role-based routing).
 */
export const ProtectedRoute: FC<ProtectedRouteProps> = ({ children, requiredRole }) => {
  const location = useLocation();
  const { isAuthenticated, user } = useAuthStore();

  if (!isAuthenticated) {
    // Redirect to login, saving attempted location
    return <Navigate to="/login" state={{ from: location }} replace />;
  }

  // Optional role-based access control
  if (requiredRole && user?.role !== requiredRole) {
    // Could redirect to unauthorized page or dashboard
    return <Navigate to="/dashboard" replace />;
  }

  return children ? <>{children}</> : <Outlet />;
};
