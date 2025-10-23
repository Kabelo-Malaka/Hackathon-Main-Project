package com.company.employeelifecycle;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordHashTest {

    @Test
    public void testPasswordHash() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

        // Generate correct hash for "password123"
        String correctHash = encoder.encode("password123");
        System.out.println("Correct BCrypt hash for 'password123': " + correctHash);

        // Verify it works
        boolean matches = encoder.matches("password123", correctHash);
        System.out.println("Verification: password123 matches new hash: " + matches);

        // Test the old hash
        String oldHash = "$2a$12$R9h/cIPz0gi.URNNX3kh2OPST9/PgBkqquzi.Ss7KIUgO2t0jWMUW";
        System.out.println("\nTesting old hash: " + oldHash);
        String[] passwords = {"password123", "Password123", "password", "test123"};
        for (String pwd : passwords) {
            boolean match = encoder.matches(pwd, oldHash);
            System.out.println("Password '" + pwd + "' matches old hash: " + match);
        }
    }
}
