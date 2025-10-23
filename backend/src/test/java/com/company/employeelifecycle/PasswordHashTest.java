package com.company.employeelifecycle;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Validates that BCrypt hashes in data.sql match the expected password "password123".
 * This test prevents regressions where invalid hashes are committed to the seed data.
 */
public class PasswordHashTest {

    private static final String EXPECTED_PASSWORD = "password123";
    private static final Pattern HASH_PATTERN = Pattern.compile("'(\\$2[aby]\\$\\d{2}\\$[./A-Za-z0-9]{53})'");

    @Test
    public void testDataSqlBCryptHashesAreValid() throws Exception {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

        // Read data.sql from classpath
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("data.sql");
        assertNotNull(inputStream, "data.sql not found in classpath");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }

            // Extract all BCrypt hashes from data.sql
            Matcher matcher = HASH_PATTERN.matcher(content.toString());
            int hashCount = 0;

            while (matcher.find()) {
                String hash = matcher.group(1);
                hashCount++;

                // Verify each hash matches "password123"
                boolean matches = encoder.matches(EXPECTED_PASSWORD, hash);
                assertTrue(matches,
                    String.format("BCrypt hash #%d in data.sql does not match expected password '%s'. Hash: %s",
                        hashCount, EXPECTED_PASSWORD, hash));
            }

            // Ensure we found at least one hash (5 test users expected)
            assertTrue(hashCount >= 5,
                String.format("Expected at least 5 BCrypt hashes in data.sql, found %d", hashCount));

            System.out.println("✓ Validated " + hashCount + " BCrypt hashes in data.sql - all match '" + EXPECTED_PASSWORD + "'");
        }
    }
}
