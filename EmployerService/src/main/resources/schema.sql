-- Employers table
CREATE TABLE employers (
    employer_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    phone VARCHAR(20) NOT NULL,
    password_hash VARCHAR(60) NOT NULL,
    type ENUM('INDIVIDUAL', 'BUSINESS') NOT NULL,
    aadhaar_number VARCHAR(12),
    pan_number VARCHAR(10),
    gstin VARCHAR(15),
    cin VARCHAR(21),
    business_name VARCHAR(255),
    location VARCHAR(255) NOT NULL,
    is_verified BOOLEAN DEFAULT FALSE,
    rating DOUBLE DEFAULT 0.0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    registration_source ENUM('SELF', 'KIOSK') NOT NULL,
    created_by VARCHAR(50),
    updated_by VARCHAR(50)
);

-- Employer documents
CREATE TABLE employer_documents (
    document_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    employer_id BIGINT NOT NULL,
    document_type VARCHAR(50) NOT NULL,
    document_number VARCHAR(100),
    verification_status ENUM('PENDING', 'VERIFIED', 'REJECTED') DEFAULT 'PENDING',
    verified_by BIGINT,
    verified_at TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (employer_id) REFERENCES employers(employer_id) ON DELETE CASCADE
);