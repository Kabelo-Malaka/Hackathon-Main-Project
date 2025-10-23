import { Box, Container, Typography, Paper, Button } from '@mui/material';
import { useNavigate } from 'react-router-dom';
import { useAuthStore } from '../store/authStore';
import { authService } from '../services/api/auth.service';

export default function DashboardPage() {
  const navigate = useNavigate();
  const { user, logout } = useAuthStore();

  const handleLogout = async () => {
    try {
      await authService.logout();
      logout();
      navigate('/login');
    } catch (err) {
      console.error('Logout failed:', err);
      // Still logout on frontend even if API call fails
      logout();
      navigate('/login');
    }
  };

  return (
    <Container maxWidth="lg" sx={{ mt: 4, mb: 4 }}>
      <Paper sx={{ p: 3 }}>
        <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', mb: 3 }}>
          <Typography variant="h4" component="h1">
            Dashboard
          </Typography>
          <Button variant="outlined" onClick={handleLogout}>
            Logout
          </Button>
        </Box>

        <Box>
          <Typography variant="h6" gutterBottom>
            Welcome, {user?.firstName} {user?.lastName}!
          </Typography>
          <Typography variant="body1" color="text.secondary" gutterBottom>
            Email: {user?.email}
          </Typography>
          <Typography variant="body1" color="text.secondary">
            Role: {user?.role}
          </Typography>
        </Box>

        <Box sx={{ mt: 4 }}>
          <Typography variant="body2" color="text.secondary">
            This is a placeholder dashboard. The employee lifecycle management features will be added in future stories.
          </Typography>
        </Box>
      </Paper>
    </Container>
  );
}
