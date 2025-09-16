-- Ensures the patient_information table exists on startup
CREATE TABLE IF NOT EXISTS patient_information (
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    email VARCHAR(255),
    phone_number VARCHAR(255),
    date_of_birth DATE,
    id_number VARCHAR(255),
    address VARCHAR(255),
    medical_aid VARCHAR(255),
    medical_history VARCHAR(4000),
    allergies VARCHAR(1000),
    current_medication VARCHAR(2000),
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ
);
