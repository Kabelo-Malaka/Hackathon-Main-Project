package com.company.employeelifecycle.controller;

import com.company.employeelifecycle.dto.response.LoginResponse;
import com.company.employeelifecycle.entity.User;
import com.company.employeelifecycle.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * Authentication REST controller handling login, logout, and user session endpoints.
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;

    /**
     * Get current authenticated user profile.
     * Note: Actual login is handled by Spring Security form login at /api/auth/login
     */
    @GetMapping("/me")
    public ResponseEntity<LoginResponse> getCurrentUser(Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String email = principal.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        LoginResponse.UserData userData = LoginResponse.UserData.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .role(user.getRole().name())
                .build();

        LoginResponse response = LoginResponse.builder()
                .success(true)
                .user(userData)
                .build();

        return ResponseEntity.ok(response);
    }

    /**
     * POST /api/auth/login is handled by Spring Security form login configuration.
     * See SecurityConfig.java for configuration.
     */

    /**
     * POST /api/auth/logout is handled by Spring Security logout configuration.
     * See SecurityConfig.java for configuration.
     */
}
