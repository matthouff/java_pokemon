CREATE TABLE pokemon (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    hp INT NOT NULL,
    cp INT NOT NULL,
    picture VARCHAR(255) NOT NULL,
    created_at DATE DEFAULT NOW(),
    updated_at DATE DEFAULT NOW()
);

CREATE TABLE type (
    id INT PRIMARY KEY,
    name VARCHAR(20) NOT NULL,
    color VARCHAR(7) NOT NULL
);

CREATE TABLE pokemon_type (
    pokemon_id BIGINT NOT NULL,
    type_id SERIAL NOT NULL,
    PRIMARY KEY (pokemon_id, type_id),
    CONSTRAINT fk_pokemon_type FOREIGN KEY (pokemon_id) REFERENCES pokemon(id),
    CONSTRAINT fk_type_pokemon FOREIGN KEY (type_id) REFERENCES type(id)
);