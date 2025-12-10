-- Jobs table
CREATE TABLE jobs (
    job_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    employer_id BIGINT NOT NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    work_category VARCHAR(100) NOT NULL,
    location VARCHAR(255) NOT NULL,
    min_budget double NOT NULL,
    max_budget double NOT NULL,
    required_no_of_workers INT NOT NULL,
    min_gig_level double DEFAULT 1.0,
    status ENUM(
        'OPEN',
        'ASSIGNED',
        'IN_PROGRESS',
        'COMPLETED',
        'CANCELLED'
    ) DEFAULT 'OPEN',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    completed_at TIMESTAMP NULL,
    estimated_duration INT,
    start_date DATE,
    end_date DATE,
    created_by VARCHAR(50),
    updated_by VARCHAR(50),
    CONSTRAINT chk_budget_range CHECK (min_budget < max_budget),
    CONSTRAINT chk_job_dates CHECK (start_date <= end_date)
);

-- Job required skills
CREATE TABLE job_required_skills (
    job_id BIGINT NOT NULL,
    skill VARCHAR(255) NOT NULL,
    FOREIGN KEY (job_id) REFERENCES jobs (job_id) ON DELETE CASCADE,
    PRIMARY KEY (job_id, skill)
);

-- Job applications
CREATE TABLE job_applications (
    application_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    job_id BIGINT NOT NULL,
    worker_id BIGINT NOT NULL,
    status ENUM(
        'PENDING',
        'SHORTLISTED',
        'APPROVED',
        'REJECTED',
        'COMPLETED',
        'CANCELLED'
    ) DEFAULT 'PENDING',
    applied_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (job_id) REFERENCES jobs (job_id),
    UNIQUE KEY unique_job_worker (job_id, worker_id)
);

-- Job assignments
CREATE TABLE job_assignments (
    assignment_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    job_id BIGINT NOT NULL,
    worker_id BIGINT NOT NULL,
    application_id BIGINT NOT NULL,
    status ENUM(
        'ASSIGNED',
        'IN_PROGRESS',
        'COMPLETED',
        'CANCELLED'
    ) DEFAULT 'ASSIGNED',
    assigned_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    completed_at TIMESTAMP NULL,
    FOREIGN KEY (job_id) REFERENCES jobs (job_id),
    FOREIGN KEY (application_id) REFERENCES job_applications (application_id),
    UNIQUE KEY unique_job_worker_assignment (job_id, worker_id)
);