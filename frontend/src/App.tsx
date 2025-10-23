import { useEffect, useState } from 'react';
import { Box, Typography, CircularProgress, Alert } from '@mui/material';
import axios from 'axios';

interface HealthResponse {
  status: string;
}

function App() {
  const [health, setHealth] = useState<HealthResponse | null>(null);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchHealth = async () => {
      try {
        const response = await axios.get<HealthResponse>('/api/health');
        setHealth(response.data);
        setError(null);
      } catch (err) {
        setError(err instanceof Error ? err.message : 'Failed to fetch health status');
      } finally {
        setLoading(false);
      }
    };

    fetchHealth();
  }, []);

  return (
    <Box
      sx={{
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
        justifyContent: 'center',
        minHeight: '100vh',
        gap: 2
      }}
    >
      <Typography variant="h3" component="h1">
        Employee Lifecycle Management System
      </Typography>

      {loading && <CircularProgress />}

      {error && (
        <Alert severity="error">
          Error connecting to backend: {error}
        </Alert>
      )}

      {health && (
        <Alert severity="success">
          Backend Status: {health.status}
        </Alert>
      )}
    </Box>
  );
}

export default App;
