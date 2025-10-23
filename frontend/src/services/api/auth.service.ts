import { apiClient } from './client';
import type { LoginRequest, LoginResponse, User } from '../../types/auth.types';

/**
 * Authentication service for login, logout, and user session management.
 */
export const authService = {
  /**
   * Login with email and password.
   * Spring Security will create a session and return user details.
   */
  login: async (credentials: LoginRequest): Promise<LoginResponse> => {
    // Convert to form data for Spring Security form login
    const formData = new URLSearchParams();
    formData.append('email', credentials.email);
    formData.append('password', credentials.password);

    const response = await apiClient.post<LoginResponse>('/auth/login', formData, {
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded',
      },
    });
    return response.data;
  },

  /**
   * Logout current user and invalidate session.
   */
  logout: async (): Promise<void> => {
    await apiClient.post('/auth/logout');
  },

  /**
   * Get current authenticated user profile.
   * Returns user data if session is valid, throws 401 if not authenticated.
   */
  getMe: async (): Promise<User> => {
    const response = await apiClient.get<LoginResponse>('/auth/me');
    return response.data.user;
  },
};
