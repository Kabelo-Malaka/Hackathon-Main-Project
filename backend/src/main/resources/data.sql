-- Test users for development and testing
-- All users have the password: password123
-- BCrypt hash (strength 12): $2a$12$A5rjKaGD1Tg5MSX/UnVXZ.kiPs2Ej20VdMX0kIx4pAozH5a54jcaa

INSERT INTO users (id, email, password, first_name, last_name, role, department, active, created_at, updated_at)
VALUES
  (gen_random_uuid(), 'hr@test.com', '$2a$12$A5rjKaGD1Tg5MSX/UnVXZ.kiPs2Ej20VdMX0kIx4pAozH5a54jcaa', 'HR', 'Admin', 'HR_ADMIN', 'Human Resources', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  (gen_random_uuid(), 'manager@test.com', '$2a$12$A5rjKaGD1Tg5MSX/UnVXZ.kiPs2Ej20VdMX0kIx4pAozH5a54jcaa', 'Manager', 'User', 'MANAGER', 'Engineering', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  (gen_random_uuid(), 'tech@test.com', '$2a$12$A5rjKaGD1Tg5MSX/UnVXZ.kiPs2Ej20VdMX0kIx4pAozH5a54jcaa', 'Tech', 'Support', 'TECH_SUPPORT', 'IT', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  (gen_random_uuid(), 'finance@test.com', '$2a$12$A5rjKaGD1Tg5MSX/UnVXZ.kiPs2Ej20VdMX0kIx4pAozH5a54jcaa', 'Finance', 'User', 'FINANCE', 'Finance', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
  (gen_random_uuid(), 'admin@test.com', '$2a$12$A5rjKaGD1Tg5MSX/UnVXZ.kiPs2Ej20VdMX0kIx4pAozH5a54jcaa', 'System', 'Admin', 'SYSTEM_ADMIN', 'IT', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)
ON CONFLICT (email) DO NOTHING;
