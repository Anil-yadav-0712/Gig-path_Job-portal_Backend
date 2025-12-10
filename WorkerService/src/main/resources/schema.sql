SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS worker_location_history;
DROP TABLE IF EXISTS worker_skills;
DROP TABLE IF EXISTS worker_assigned_jobs_history;
DROP TABLE IF EXISTS worker_applied_jobs_history;
DROP TABLE IF EXISTS worker_education_details;
DROP TABLE IF EXISTS worker_documents;
DROP TABLE IF EXISTS workers;

SET FOREIGN_KEY_CHECKS = 1;

-- ============================================
-- TABLE: workers
-- ============================================
CREATE TABLE workers (
    worker_id BIGINT PRIMARY KEY AUTO_INCREMENT,

    worker_name VARCHAR(255) NOT NULL,
    birth_date DATE NOT NULL,

    file_data LONGBLOB DEFAULT NULL,

    worker_phone VARCHAR(20) NOT NULL,
    worker_email VARCHAR(255) DEFAULT NULL,
    worker_password_hash VARCHAR(100) NOT NULL,

    -- Embedded AddressDetailsObject fields
    worker_address_house_no VARCHAR(255) DEFAULT NULL,
    worker_address_street_no VARCHAR(255) DEFAULT NULL,
    worker_address_landmark VARCHAR(255) DEFAULT NULL,
    worker_address_city VARCHAR(255) DEFAULT NULL,
    worker_address_district VARCHAR(255) DEFAULT NULL,
    worker_address_state VARCHAR(255) DEFAULT NULL,
    worker_address_country VARCHAR(255) DEFAULT NULL,
    worker_address_pincode VARCHAR(50) DEFAULT NULL,

    latitude DOUBLE DEFAULT NULL,
    longitude DOUBLE DEFAULT NULL,
    preferred_travel_distance INT NOT NULL,
    preferred_language VARCHAR(50) DEFAULT NULL,

    is_verified BOOLEAN NOT NULL DEFAULT FALSE,
    nfc_card_status VARCHAR(50) DEFAULT NULL,
    nfc_card_id VARCHAR(100) UNIQUE DEFAULT NULL,

    work_category VARCHAR(25) NOT NULL,

    min_wage_range DOUBLE DEFAULT 0,
    max_wage_range DOUBLE DEFAULT 0,

    work_acceptance_rate DOUBLE DEFAULT NULL,
    work_completion_rate DOUBLE DEFAULT NULL,

    worker_trust_score INT NOT NULL DEFAULT 0,
    worker_level INT NOT NULL DEFAULT 0,

    created_at_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted_at_time DATETIME DEFAULT NULL,

    created_by VARCHAR(50) NOT NULL,
    kiosk_id_of_registration VARCHAR(255) DEFAULT NULL,

    updated_by VARCHAR(50) DEFAULT NULL,
    kiosk_id_of_updation VARCHAR(255) DEFAULT NULL,

    deleted_by VARCHAR(50) DEFAULT NULL,
    kiosk_id_of_deletion VARCHAR(255) DEFAULT NULL
);

-- ============================================
-- TABLE: worker_documents
-- ============================================
CREATE TABLE worker_documents (
    document_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    worker_id BIGINT NOT NULL,
    document_name VARCHAR(50) NOT NULL,
    document_issue_number VARCHAR(100) DEFAULT NULL,
    issuing_authority VARCHAR(255) NOT NULL,
    issue_date DATE DEFAULT NULL,
    document_validity DATE DEFAULT NULL,
    file_data LONGBLOB DEFAULT NULL,
    document_verification_status VARCHAR(50) NOT NULL,
    verified_at DATETIME DEFAULT NULL,
    verified_by BIGINT DEFAULT NULL,
    rejection_reason TEXT DEFAULT NULL,
    created_at_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted_at_time DATETIME DEFAULT NULL,
    deletion_reason TEXT DEFAULT NULL,
    CONSTRAINT fk_worker_document FOREIGN KEY (worker_id) REFERENCES workers (worker_id) ON DELETE CASCADE
);

-- ============================================
-- TABLE: worker_skills
-- ============================================
CREATE TABLE worker_skills (
    skill_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    skill VARCHAR(255) NOT NULL,
    worker_id BIGINT NOT NULL,
    CONSTRAINT fk_worker_skill FOREIGN KEY (worker_id) REFERENCES workers (worker_id) ON DELETE CASCADE,
    CONSTRAINT uc_worker_skill UNIQUE (worker_id, skill)
);

-- ============================================
-- TABLE: worker_education_details
-- ============================================
CREATE TABLE worker_education_details (
    worker_id BIGINT NOT NULL,
    qualification VARCHAR(100) DEFAULT NULL,
    issuing_authority VARCHAR(255) DEFAULT NULL,
    institute_name VARCHAR(255) DEFAULT NULL,
    id_number VARCHAR(255) DEFAULT NULL,
    marks_obtained INT DEFAULT NULL,
    total_marks INT DEFAULT NULL,
    percentage FLOAT DEFAULT NULL,
    is_verified BOOLEAN DEFAULT NULL,
    CONSTRAINT fk_worker_education FOREIGN KEY (worker_id) REFERENCES workers (worker_id) ON DELETE CASCADE
);

SET FOREIGN_KEY_CHECKS = 1;