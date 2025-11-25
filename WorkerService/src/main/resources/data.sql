-- Insert sample workers
INSERT INTO
    workers (
        worker_name,
        worker_email,
        worker_phone,
        worker_password_hash,
        nfc_card_id,
        aadhaar_number,
        pan_number,
        birth_date,
        preferred_travel_distance,
        work_category,
        location,
        gig_level,
        min_wage_range,
        max_wage_range,
        is_verified,
        registration_source,
        kiosk_id_of_registration
    )
VALUES (
        'Rajesh Kumar',
        'rajesh@example.com',
        '9876543210',
        '$2a$12$LQF6Ik7zXv.H4HE8UZX3O.miPO5.d7CMZhFgqxGPxUZqz3ZbTYBpu',
        'NFC001',
        '123456789012',
        'ABCPK1234X',
        '1990-05-15',
        10,
        'Carpentry',
        'Mumbai',
        4.5,
        500,
        1000,
        true,
        'KIOSK',
        1
    ),
    (
        'Priya Singh',
        'priya@example.com',
        '8765432109',
        '$2a$12$LQF6Ik7zXv.H4HE8UZX3O.miPO5.d7CMZhFgqxGPxUZqz3ZbTYBpu',
        'NFC002',
        '123456789013',
        'XYZPS5678Y',
        '1995-08-22',
        15,
        'Plumbing',
        'Delhi',
        3.8,
        400,
        800,
        true,
        'SELF',
        2
    );

-- Insert worker skills
INSERT INTO
    worker_skills (worker_id, skill)
VALUES (1, 'Furniture Making'),
    (1, 'Wood Carving'),
    (1, 'Cabinet Installation'),
    (2, 'Pipe Fitting'),
    (2, 'Bathroom Installation'),
    (2, 'Water Heater Repair');

-- Insert worker applied jobs history
INSERT INTO
    worker_applied_jobs_history (
        worker_id,
        job_id,
        application_status,
        applied_at
    )
VALUES (
        1,
        101,
        'PENDING',
        '2025-09-15 10:00:00'
    ),
    (
        2,
        102,
        'SHORTLISTED',
        '2025-09-14 11:30:00'
    );

-- Insert worker assigned jobs history
INSERT INTO
    worker_assigned_jobs_history (
        worker_id,
        job_id,
        start_date,
        end_date,
        completion_status,
        employer_rating,
        feedback,
        wage_paid,
        attendance_status
    )
VALUES (
        1,
        201,
        '2025-09-01 09:00:00',
        '2025-09-01 17:00:00',
        'COMPLETED',
        4.5,
        'Excellent work on furniture repair',
        800,
        'PRESENT'
    ),
    (
        2,
        202,
        '2025-09-02 10:00:00',
        '2025-09-02 16:00:00',
        'COMPLETED',
        4.0,
        'Good plumbing work, fixed the leak efficiently',
        600,
        'PRESENT'
    );

-- Insert worker documents
INSERT INTO
    worker_documents (
        worker_id,
        document_type,
        document_number,
        verification_status,
        verified_by,
        verified_at
    )
VALUES (
        1,
        'AADHAAR',
        '123456789012',
        'VERIFIED',
        101,
        '2025-08-15 10:00:00'
    ),
    (
        2,
        'AADHAAR',
        '123456789013',
        'VERIFIED',
        102,
        '2025-08-16 11:00:00'
    );

-- Insert worker location history
INSERT INTO
    worker_location_history (
        worker_id,
        location,
        timestamp
    )
VALUES (
        1,
        'Mumbai-Andheri',
        '2025-09-15 09:00:00'
    ),
    (
        2,
        'Delhi-Rohini',
        '2025-09-15 10:30:00'
    );