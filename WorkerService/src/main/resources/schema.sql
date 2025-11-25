DROP TABLE IF EXISTS worker_location_history;

DROP TABLE IF EXISTS worker_documents;

DROP TABLE IF EXISTS worker_assigned_jobs_history;

DROP TABLE IF EXISTS worker_applied_jobs_history;

DROP TABLE IF EXISTS worker_skills;

DROP TABLE IF EXISTS workers;

CREATE TABLE workers (
    worker_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    worker_name VARCHAR(255) NOT NULL,
    birth_date DATE NOT NULL,
    worker_email VARCHAR(255),
    worker_phone VARCHAR(20) NOT NULL,
    worker_password_hash VARCHAR(60) NOT NULL,
    nfc_card_id VARCHAR(100) UNIQUE,
    nfc_card_status ENUM(
        'PENDING',
        'GENERATED',
        'ACTIVE',
        'BLOCKED'
    ) DEFAULT 'PENDING',
    aadhaar_number VARCHAR(12) NOT NULL UNIQUE,
    pan_number VARCHAR(10),
    voter_card_number VARCHAR(20),
    is_verified BOOLEAN DEFAULT FALSE,
    preferred_language VARCHAR(50) DEFAULT 'ENGLISH',
    location VARCHAR(255) NOT NULL,
    preferred_travel_distance INT,
    work_category VARCHAR(100) NOT NULL,
    gig_level DOUBLE DEFAULT 1.0,
    min_wage_range DOUBLE NOT NULL,
    max_wage_range DOUBLE NOT NULL,
    job_acceptance_rate DOUBLE DEFAULT 0.0,
    job_completion_rate DOUBLE DEFAULT 0.0,
    registration_source ENUM('SELF', 'KIOSK') NOT NULL,
    kiosk_id_of_registration VARCHAR(16),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by VARCHAR(50),
    updated_by VARCHAR(50),
    CONSTRAINT chk_wage_range CHECK (
        min_wage_range < max_wage_range
    )
);

CREATE TABLE worker_skills (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    worker_id BIGINT NOT NULL,
    skill VARCHAR(255) NOT NULL,
    FOREIGN KEY (worker_id) REFERENCES workers (worker_id) ON DELETE CASCADE,
    UNIQUE KEY uk_worker_skill (worker_id, skill)
);

CREATE TABLE worker_applied_jobs_history (
    applied_history_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    worker_id BIGINT NOT NULL,
    job_id BIGINT NOT NULL,
    application_status ENUM(
        'PENDING',
        'SHORTLISTED',
        'APPROVED',
        'REJECTED',
        'CANCELLED'
    ) NOT NULL,
    applied_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    rejection_reason TEXT,
    created_by VARCHAR(50),
    updated_by VARCHAR(50),
    FOREIGN KEY (worker_id) REFERENCES workers (worker_id)
);

CREATE TABLE worker_assigned_jobs_history (
    assigned_history_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    worker_id BIGINT NOT NULL,
    job_id BIGINT NOT NULL,
    start_date TIMESTAMP,
    end_date TIMESTAMP,
    completion_status ENUM(
        'ASSIGNED',
        'IN_PROGRESS',
        'COMPLETED',
        'CANCELLED'
    ) NOT NULL,
    employer_rating DOUBLE,
    feedback TEXT,
    wage_paid DOUBLE,
    attendance_status ENUM('PRESENT', 'ABSENT', 'LATE') DEFAULT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    created_by VARCHAR(50),
    updated_by VARCHAR(50),
    FOREIGN KEY (worker_id) REFERENCES workers (worker_id),
    CONSTRAINT chk_assigned_dates CHECK (start_date <= end_date)
);

CREATE TABLE worker_documents (
    document_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    worker_id BIGINT NOT NULL,
    document_type VARCHAR(50) NOT NULL,
    document_number VARCHAR(100),
    verification_status ENUM(
        'PENDING',
        'VERIFIED',
        'REJECTED'
    ) DEFAULT 'PENDING',
    verified_by BIGINT,
    verified_at TIMESTAMP,
    rejection_reason TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (worker_id) REFERENCES workers (worker_id) ON DELETE CASCADE
);

-- Worker location history
CREATE TABLE worker_location_history (
    location_history_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    worker_id BIGINT NOT NULL,
    location VARCHAR(255) NOT NULL,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_updated_at TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (worker_id) REFERENCES workers (worker_id) ON DELETE CASCADE
);