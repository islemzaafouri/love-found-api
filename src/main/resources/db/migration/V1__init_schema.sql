-- 1. Users Table
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50),
    created_at DATETIME
);

-- 2. Adopter Profiles Table
CREATE TABLE IF NOT EXISTS adopter_profiles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL UNIQUE,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    phone_number VARCHAR(50),
    city VARCHAR(255) NOT NULL,
    lifestyle_activity VARCHAR(50),
    experience VARCHAR(50),
    home_type VARCHAR(50),
    has_other_pets BOOLEAN,
    has_children BOOLEAN,
    work_schedule VARCHAR(50),
    preferred_species VARCHAR(50),
    has_allergies BOOLEAN,
    CONSTRAINT fk_adopter_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- 3. Shelter Profiles Table
CREATE TABLE IF NOT EXISTS shelter_profiles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL UNIQUE,
    shelter_name VARCHAR(255) NOT NULL,
    phone_number VARCHAR(50) NOT NULL,
    address VARCHAR(255),
    city VARCHAR(255),
    description VARCHAR(1000),
    CONSTRAINT fk_shelter_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- 4. Pets Table
CREATE TABLE IF NOT EXISTS pets (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    shelter_id BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    species VARCHAR(50) NOT NULL,
    age INT NOT NULL,
    gender VARCHAR(50) NOT NULL,
    breed VARCHAR(255),
    photo_url VARCHAR(255),
    created_at DATETIME NOT NULL,
    rescue_story TEXT,
    description VARCHAR(500),
    status VARCHAR(50) NOT NULL DEFAULT 'AVAILABLE',
    CONSTRAINT fk_pet_shelter FOREIGN KEY (shelter_id) REFERENCES shelter_profiles(id) ON DELETE CASCADE
);

-- 5. Pet Personality Traits (@ElementCollection Join Table)
CREATE TABLE IF NOT EXISTS pet_personality_traits (
    pet_id BIGINT NOT NULL,
    trait VARCHAR(50) NOT NULL,
    CONSTRAINT fk_traits_pet FOREIGN KEY (pet_id) REFERENCES pets(id) ON DELETE CASCADE
);