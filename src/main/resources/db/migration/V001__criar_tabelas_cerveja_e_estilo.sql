CREATE SEQUENCE estilo_codigo_seq;
CREATE TABLE estilo (
    codigo BIGINT PRIMARY KEY NOT NULL DEFAULT nextval('estilo_codigo_seq'),
    nome VARCHAR(50) NOT NULL
);
ALTER SEQUENCE estilo_codigo_seq OWNED BY estilo.codigo;

CREATE SEQUENCE cerveja_codigo_seq;
CREATE TABLE cerveja (
    codigo BIGINT PRIMARY KEY NOT NULL DEFAULT nextval('cerveja_codigo_seq'),
    sku VARCHAR(50) NOT NULL,
    nome VARCHAR(80) NOT NULL,
    descricao TEXT NOT NULL,
    valor DECIMAL(10, 2) NOT NULL,
    teor_alcoolico DECIMAL(10, 2) NOT NULL,
    comissao DECIMAL(10, 2) NOT NULL,
    sabor VARCHAR(50) NOT NULL,
    origem VARCHAR(50) NOT NULL,
    codigo_estilo BIGINT NOT NULL REFERENCES estilo(codigo)
);
ALTER SEQUENCE cerveja_codigo_seq OWNED BY cerveja.codigo;

INSERT INTO estilo (nome) VALUES ('Amber Lager');
INSERT INTO estilo (nome) VALUES ('Dark Lager');
INSERT INTO estilo (nome) VALUES ('Pale Lager');
INSERT INTO estilo (nome) VALUES ('Pilsner');