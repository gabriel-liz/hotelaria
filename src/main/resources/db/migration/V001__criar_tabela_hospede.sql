CREATE TABLE hospede (

    id BIGSERIAL PRIMARY KEY,


    nome VARCHAR(255) NOT NULL,
    documento VARCHAR(255) NOT NULL,
    telefone VARCHAR(255)
);