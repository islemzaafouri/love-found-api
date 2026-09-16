CREATE TABLE IF NOT EXISTS applications (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    adopter_id BIGINT NOT NULL,
    pet_id BIGINT NOT NULL,

    motivation TEXT NOT NULL,

    status VARCHAR(20) NOT NULL DEFAULT 'PENDING',

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_application_adopter
        FOREIGN KEY (adopter_id)
        REFERENCES adopter_profiles(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_application_pet
        FOREIGN KEY (pet_id)
        REFERENCES pets(id)
        ON DELETE CASCADE,

    CONSTRAINT uk_application_adopter_pet
        UNIQUE (adopter_id, pet_id),

    CONSTRAINT chk_application_status
        CHECK (status IN (
            'PENDING',
            'ACCEPTED',
            'REJECTED',
            'CANCELLED'
        ))
);