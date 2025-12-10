-- ============================================
-- INSERT sample worker
-- ============================================
INSERT INTO workers (
    worker_name,
    birth_date,
    worker_phone,
    worker_email,
    worker_password_hash,
    preferred_language,
    preferred_travel_distance,
    is_verified,
    work_category,
    min_wage_range,
    max_wage_range,
    work_acceptance_rate,
    work_completion_rate,
    worker_trust_score,
    worker_level,
    created_by
)
VALUES (
    'Rajesh Kumar',
    '1990-05-15',
    '9876543210',
    'rajesh@example.com',
    'hashed_pw_123',
    'HINDI',
    10,
    FALSE,
    'PLUMBING',
    300,       -- min_wage_range
    700,       -- max_wage_range
    0,         -- work_acceptance_rate default 0 to avoid NPE
    0,         -- work_completion_rate default 0
    50,        -- worker_trust_score
    2,         -- worker_level
    'SELF'
);

-- ============================================
-- INSERT sample skills
-- ============================================
INSERT INTO worker_skills (skill, worker_id)
VALUES 
    ('PLUMBING', 1),
    ('PIPE_FITTING', 1);

-- ============================================
-- INSERT sample documents
-- ============================================
INSERT INTO worker_documents (
    worker_id,
    document_name,
    document_issue_number,
    issuing_authority,
    issue_date,
    document_validity,
    document_verification_status
)
VALUES 
    (1, 'Aadhaar', '1234-5678-9012', 'UIDAI', '2015-01-01', '2030-01-01', 'PENDING'),
    (1, 'VoterID', 'DL1234567', 'Election Commission', '2012-01-01', '2030-01-01', 'APPROVED');

-- ============================================
-- INSERT sample education details
-- ============================================
INSERT INTO worker_education_details (
    worker_id,
    qualification,
    issuing_authority,
    institute_name,
    id_number,
    marks_obtained,
    total_marks,
    percentage,
    is_verified
)
VALUES 
    (1, 'TENTH', 'CBSE', 'DAV School', 'ID1001', 400, 500, 80.0, TRUE),
    (1, 'TWELFTH', 'CBSE', 'DAV School', 'ID1002', 350, 500, 70.0, FALSE);
