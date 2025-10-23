package com.company.employeelifecycle.controller;

import com.company.employeelifecycle.entity.User;
import com.company.employeelifecycle.enums.UserRole;
import com.company.employeelifecycle.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private User testUser;

    @BeforeEach
    void setUp() {
        // Clear existing users
        userRepository.deleteAll();

        // Create test user
        testUser = User.builder()
                .email("test@example.com")
                .password(passwordEncoder.encode("password123"))
                .firstName("Test")
                .lastName("User")
                .role(UserRole.HR_ADMIN)
                .department("HR")
                .active(true)
                .build();
        userRepository.save(testUser);
    }

    @Test
    void testSuccessfulLogin() throws Exception {
        mockMvc.perform(post("/api/auth/login")
                        .with(csrf())
                        .param("email", "test@example.com")
                        .param("password", "password123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.user.email").value("test@example.com"))
                .andExpect(jsonPath("$.user.firstName").value("Test"))
                .andExpect(jsonPath("$.user.lastName").value("User"))
                .andExpect(jsonPath("$.user.role").value("HR_ADMIN"));
    }

    @Test
    void testFailedLoginWithInvalidCredentials() throws Exception {
        mockMvc.perform(post("/api/auth/login")
                        .with(csrf())
                        .param("email", "test@example.com")
                        .param("password", "wrongpassword"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.error").exists());
    }

    @Test
    void testFailedLoginWithNonexistentUser() throws Exception {
        mockMvc.perform(post("/api/auth/login")
                        .with(csrf())
                        .param("email", "nonexistent@example.com")
                        .param("password", "password123"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.success").value(false));
    }

    @Test
    @org.springframework.security.test.context.support.WithMockUser(username = "test@example.com")
    void testGetMeWhenAuthenticated() throws Exception {
        // Test /me endpoint with mocked authentication
        mockMvc.perform(get("/api/auth/me"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.user.email").value("test@example.com"))
                .andExpect(jsonPath("$.user.role").value("HR_ADMIN"));
    }

    @Test
    void testGetMeWhenNotAuthenticated() throws Exception {
        // Expect redirect to login (302) when not authenticated
        mockMvc.perform(get("/api/auth/me"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    @org.springframework.security.test.context.support.WithMockUser(username = "test@example.com")
    void testLogoutWithMockUser() throws Exception {
        // Logout with mocked authentication
        mockMvc.perform(post("/api/auth/logout")
                        .with(csrf()))
                .andExpect(status().isOk());
    }
}
