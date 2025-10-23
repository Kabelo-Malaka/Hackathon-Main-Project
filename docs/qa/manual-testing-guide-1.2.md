# Manual Testing Guide - Story 1.2: User Authentication

## Prerequisites

- Backend running on http://localhost:8080
- Frontend running on http://localhost:5174
- PostgreSQL database running with test users seeded

## Test Credentials

All test users have password: `password123`

- **HR Admin:** hr@test.com
- **Manager:** manager@test.com
- **Tech Support:** tech@test.com
- **Finance:** finance@test.com
- **System Admin:** admin@test.com

## Test Cases

### Test 1: Basic Login Functionality ✓

**Objective:** Verify user can log in with valid credentials

**Steps:**
1. Navigate to http://localhost:5174
2. You should be redirected to /login page
3. Enter email: `hr@test.com`
4. Enter password: `password123`
5. Click "Login" button

**Expected Results:**
- Login successful
- Redirected to /dashboard
- User information displayed (HR Admin)

**Status:** ✓ VERIFIED (QA tested successfully)

---

### Test 2: Session Persistence on Browser Refresh ⚠️

**Objective:** Verify session persists across browser refresh (AC12)

**Steps:**
1. Complete Test 1 (log in successfully)
2. Wait 2-3 seconds for session to stabilize
3. Press F5 or click browser refresh button
4. Observe the behavior

**Expected Results:**
- Page refreshes successfully
- User remains logged in
- Dashboard is displayed (no redirect to login)
- User information still visible

**How to Verify Session Cookie:**
1. Open browser DevTools (F12)
2. Go to Application tab → Cookies → http://localhost:5174
3. Look for JSESSIONID cookie
4. Verify properties:
   - HttpOnly: true
   - SameSite: Lax
   - Secure: false (dev environment)
   - Has expiration time

**Status:** ⚠️ PENDING MANUAL TEST

---

### Test 3: Logout Functionality

**Objective:** Verify logout clears session and redirects to login

**Steps:**
1. Log in as any user (hr@test.com / password123)
2. Navigate to dashboard
3. Click "Logout" button (if UI exists) or call logout endpoint
4. Observe behavior

**Expected Results:**
- Session is cleared
- JSESSIONID cookie is deleted
- Redirected to /login page
- Attempting to access /dashboard redirects to /login

**Status:** ✓ VERIFIED (automated test passing)

---

### Test 4: 30-Minute Session Timeout ⚠️

**Objective:** Verify session expires after 30 minutes of inactivity (AC12)

**Steps:**
1. Log in as any user (hr@test.com / password123)
2. Navigate to dashboard
3. **Do NOT interact with the application for 30 minutes**
4. After 30+ minutes, try to navigate to a protected route or refresh the page

**Expected Results:**
- After 30 minutes of inactivity, session expires
- User is redirected to /login page
- Message indicating session expired (if implemented)
- JSESSIONID cookie is invalidated

**Shortened Test (for quick validation):**
If you need faster testing, temporarily modify application.properties:
```properties
server.servlet.session.timeout=2m
```
Then test with 2-minute timeout instead of 30 minutes.
**Remember to revert this change after testing!**

**Status:** ⚠️ PENDING MANUAL TEST

---

### Test 5: Invalid Credentials

**Objective:** Verify proper error handling for invalid credentials

**Steps:**
1. Navigate to /login
2. Enter email: `hr@test.com`
3. Enter password: `wrongpassword`
4. Click "Login"

**Expected Results:**
- Login fails
- Error message displayed: "Invalid email or password"
- User remains on login page
- No session created

**Status:** ✓ VERIFIED (automated test passing)

---

### Test 6: Protected Route Access Without Authentication

**Objective:** Verify unauthenticated users cannot access protected routes

**Steps:**
1. Open browser in incognito/private mode
2. Navigate directly to http://localhost:5174/dashboard
3. Observe behavior

**Expected Results:**
- User is redirected to /login page
- After login, user is redirected back to originally requested page (/dashboard)

**Status:** ✓ VERIFIED (automated test passing)

---

### Test 7: Multiple Sessions / Concurrent Login

**Objective:** Verify maximum 1 session per user (new login invalidates old session)

**Steps:**
1. Log in as hr@test.com in Chrome
2. Verify dashboard is accessible
3. Open Firefox (or another browser)
4. Log in as hr@test.com in Firefox
5. Return to Chrome
6. Try to navigate or refresh page

**Expected Results:**
- New login in Firefox creates new session
- Old session in Chrome is invalidated
- Chrome user should be redirected to login

**Configuration:** `maximumSessions(1)` in SecurityConfig.java

**Status:** ⚠️ PENDING MANUAL TEST

---

## Test Results Summary

| Test Case | Status | Notes |
|-----------|--------|-------|
| Test 1: Basic Login | ✓ PASS | Verified during QA |
| Test 2: Browser Refresh | ⚠️ PENDING | Requires manual verification |
| Test 3: Logout | ✓ PASS | Automated test passing |
| Test 4: 30-Min Timeout | ⚠️ PENDING | Requires 30+ minutes of inactivity |
| Test 5: Invalid Credentials | ✓ PASS | Automated test passing |
| Test 6: Protected Routes | ✓ PASS | Automated test passing |
| Test 7: Multiple Sessions | ⚠️ PENDING | Optional - verify session limit |

## Notes for Testers

1. **Browser DevTools:** Keep DevTools open (F12) → Network tab to observe:
   - Session cookies being set/cleared
   - API requests with credentials
   - CSRF tokens in headers

2. **Backend Logs:** Monitor backend.log for authentication events:
   ```bash
   tail -f backend.log | grep -i "authentication\|session"
   ```

3. **Database Verification:** Check active sessions:
   ```sql
   SELECT * FROM spring_session WHERE principal_name = 'hr@test.com';
   ```
   Note: This requires Spring Session JDBC (not currently configured)

4. **Quick Smoke Test:** If time is limited, prioritize Test 2 (browser refresh)

## Reporting Issues

If any test fails, report to Quinn (QA) or James (Developer) with:
- Test case number
- Steps to reproduce
- Expected vs actual behavior
- Screenshots if applicable
- Browser/environment details
