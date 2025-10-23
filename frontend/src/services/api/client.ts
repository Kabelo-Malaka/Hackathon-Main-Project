import axios from 'axios';

/**
 * Axios client configured for API requests.
 * withCredentials enables cookies for session-based authentication.
 */
export const apiClient = axios.create({
  baseURL: '/api', // Uses Vite proxy to forward to backend
  timeout: 30000,
  headers: {
    'Content-Type': 'application/json',
  },
  withCredentials: true, // CRITICAL: Include cookies in requests
});

// Axios automatically reads XSRF-TOKEN cookie and sends it as X-XSRF-TOKEN header
// for CSRF protection on non-GET requests
