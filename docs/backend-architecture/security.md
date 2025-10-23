# Security

Comprehensive security implementation using Spring Security.

## Authentication

**Session-Based Authentication:**
```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/login", "/api/health").permitAll()
                .requestMatchers("/api/templates/**").hasRole("HR_ADMIN")
                .requestMatchers("/api/**").authenticated()
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .maximumSessions(1)
                .maxSessionsPreventsLogin(false)
            )
            .sessionManagement(session -> session
                .sessionFixation().changeSessionId()
                .invalidSessionUrl("/api/auth/login")
                .sessionAuthenticationErrorUrl("/api/auth/login")
            )
            .formLogin(form -> form
                .loginProcessingUrl("/api/auth/login")
                .successHandler(authenticationSuccessHandler())
                .failureHandler(authenticationFailureHandler())
            )
            .logout(logout -> logout
                .logoutUrl("/api/auth/logout")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
            )
            .csrf(csrf -> csrf
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
            );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12); // Strength factor 12
    }
}
```

## Authorization (RBAC)

**Role-Based Access Control:**
- `HR_ADMIN`: Full access, template management
- `MANAGER`: View team workflows, provide employee details
- `TECH_SUPPORT`: Complete provisioning tasks, view assigned tasks
- `FINANCE`: Complete finance tasks, view assigned tasks
- `SYSTEM_ADMIN`: Full system access, user management

**Method-Level Security:**
```java
@Service
public class TemplateService {

    @PreAuthorize("hasRole('HR_ADMIN')")
    public WorkflowTemplate createTemplate(CreateTemplateRequest request) {
        // Only HR admins can create templates
    }

    @PreAuthorize("hasAnyRole('HR_ADMIN', 'MANAGER')")
    public Workflow createWorkflow(CreateWorkflowRequest request) {
        // HR and managers can create workflows
    }
}
```

## Security Measures

1. **Password Security**
   - BCrypt hashing with salt (strength factor 12)
   - Minimum password requirements enforced
   - No password storage in logs or audit trails

2. **Session Management**
   - HTTP-only session cookies
   - 30-minute inactivity timeout
   - Session fixation protection
   - Single session per user

3. **CSRF Protection**
   - Enabled for all state-changing operations
   - CSRF token in cookie (accessible to frontend)
   - Validated on POST/PUT/PATCH/DELETE

4. **HTTPS Enforcement**
   - Production requires HTTPS
   - HTTP Strict Transport Security (HSTS) headers
   - Secure cookies in production

5. **SQL Injection Prevention**
   - Parameterized queries via JPA/Hibernate
   - No raw SQL string concatenation

6. **XSS Prevention**
   - Input validation and sanitization
   - Output encoding in responses
   - Content Security Policy headers

7. **Rate Limiting**
   - Max 5 login attempts per IP per minute
   - API rate limiting (100 requests/minute per user)

---
