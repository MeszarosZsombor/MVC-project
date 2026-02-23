CREATE TABLE pet_category (
    pet_category_id BIGSERIAL PRIMARY KEY,
    pet_type VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE owners (
    owner_id BIGSERIAL PRIMARY KEY,
    role VARCHAR(20) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(255),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE pets (
    pet_id BIGSERIAL PRIMARY KEY,
    pet_name VARCHAR(255) NOT NULL,
    weight INTEGER,
    age INTEGER,
    gender VARCHAR(20),
    pet_category_id BIGINT NOT NULL,
    adopted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_pet_category
        FOREIGN KEY (pet_category_id)
        REFERENCES pet_category(pet_category_id)
        ON DELETE RESTRICT
);

CREATE TABLE adoptions (
    adoption_id BIGSERIAL PRIMARY KEY,
    owner_id BIGINT NOT NULL,
    pet_id BIGINT NOT NULL,
    adoption_date TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_adoption_owner
        FOREIGN KEY (owner_id)
        REFERENCES owners(owner_id)
        ON DELETE CASCADE,

    CONSTRAINT fk_adoption_pet
        FOREIGN KEY (pet_id)
        REFERENCES pets(pet_id)
        ON DELETE CASCADE
);

CREATE INDEX idx_pets_category ON pets(pet_category_id);
CREATE INDEX idx_adoptions_owner ON adoptions(owner_id);
CREATE INDEX idx_adoptions_pet ON adoptions(pet_id);
