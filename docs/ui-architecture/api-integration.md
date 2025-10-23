# API Integration

## Service Template

```typescript
// src/services/api/workflow.service.ts
import { apiClient } from './client';
import type { Workflow, CreateWorkflowDTO, UpdateWorkflowDTO } from '@/types/workflow.types';
import type { ApiResponse, PaginatedResponse } from '@/types/api.types';

/**
 * Workflow API Service
 * Handles all workflow-related API calls
 */
export const workflowService = {
  /**
   * Get all workflows with optional filtering
   */
  getAll: async (params?: {
    status?: string;
    page?: number;
    limit?: number;
  }): Promise<PaginatedResponse<Workflow>> => {
    const response = await apiClient.get<PaginatedResponse<Workflow>>('/workflows', {
      params,
    });
    return response.data;
  },

  /**
   * Get a single workflow by ID
   */
  getById: async (id: string): Promise<Workflow> => {
    const response = await apiClient.get<ApiResponse<Workflow>>(`/workflows/${id}`);
    return response.data.data;
  },

  /**
   * Create a new workflow
   */
  create: async (data: CreateWorkflowDTO): Promise<Workflow> => {
    const response = await apiClient.post<ApiResponse<Workflow>>('/workflows', data);
    return response.data.data;
  },

  /**
   * Update an existing workflow
   */
  update: async (id: string, data: UpdateWorkflowDTO): Promise<Workflow> => {
    const response = await apiClient.patch<ApiResponse<Workflow>>(`/workflows/${id}`, data);
    return response.data.data;
  },

  /**
   * Delete a workflow
   */
  delete: async (id: string): Promise<void> => {
    await apiClient.delete(`/workflows/${id}`);
  },

  /**
   * Initiate a workflow for an employee
   */
  initiate: async (workflowId: string, employeeId: string): Promise<Workflow> => {
    const response = await apiClient.post<ApiResponse<Workflow>>(
      `/workflows/${workflowId}/initiate`,
      { employeeId }
    );
    return response.data.data;
  },
};
```

## API Client Configuration

```typescript
// src/services/api/client.ts
import axios, { AxiosError, InternalAxiosRequestConfig } from 'axios';
import { useAuthStore } from '@/store';

/**
 * Base API URL from environment variables
 */
const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:3000/api';

/**
 * Axios instance with default configuration
 */
export const apiClient = axios.create({
  baseURL: API_BASE_URL,
  timeout: 30000, // 30 seconds
  headers: {
    'Content-Type': 'application/json',
  },
});

/**
 * Request interceptor - Add auth token to all requests
 */
apiClient.interceptors.request.use(
  (config: InternalAxiosRequestConfig) => {
    const token = useAuthStore.getState().token;

    if (token && config.headers) {
      config.headers.Authorization = `Bearer ${token}`;
    }

    return config;
  },
  (error: AxiosError) => {
    return Promise.reject(error);
  }
);

/**
 * Response interceptor - Handle common errors
 */
apiClient.interceptors.response.use(
  (response) => response,
  (error: AxiosError) => {
    // Handle 401 Unauthorized - logout user
    if (error.response?.status === 401) {
      useAuthStore.getState().logout();
      window.location.href = '/login';
    }

    // Handle 403 Forbidden
    if (error.response?.status === 403) {
      console.error('Insufficient permissions');
    }

    // Handle 500 Server Error
    if (error.response?.status === 500) {
      console.error('Server error occurred');
    }

    return Promise.reject(error);
  }
);
```

## TypeScript API Types

```typescript
// src/types/api.types.ts

/**
 * Standard API response wrapper
 */
export interface ApiResponse<T> {
  success: boolean;
  data: T;
  message?: string;
  timestamp: string;
}

/**
 * Paginated response for list endpoints
 */
export interface PaginatedResponse<T> {
  data: T[];
  pagination: {
    page: number;
    limit: number;
    total: number;
    totalPages: number;
  };
}

/**
 * API error response
 */
export interface ApiError {
  success: false;
  error: {
    code: string;
    message: string;
    details?: Record<string, string[]>;
  };
  timestamp: string;
}
```

## React Query Integration Example

```typescript
// Custom hook for workflows
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { workflowService } from '@/services/api/workflow.service';

export const useWorkflows = (params?: { status?: string }) => {
  return useQuery({
    queryKey: ['workflows', params],
    queryFn: () => workflowService.getAll(params),
  });
};

export const useWorkflow = (id: string) => {
  return useQuery({
    queryKey: ['workflows', id],
    queryFn: () => workflowService.getById(id),
    enabled: !!id, // Only run if id exists
  });
};

export const useCreateWorkflow = () => {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: workflowService.create,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['workflows'] });
    },
  });
};
```

---
