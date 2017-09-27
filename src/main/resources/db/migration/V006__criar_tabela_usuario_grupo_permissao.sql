CREATE SEQUENCE usuario_codigo_seq;
CREATE TABLE usuario (
    codigo BIGINT PRIMARY KEY NOT NULL DEFAULT nextval('usuario_codigo_seq'),
    nome VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL,
    senha VARCHAR(120) NOT NULL,
    ativo BOOLEAN DEFAULT true,
    data_nascimento DATE
);
ALTER SEQUENCE usuario_codigo_seq OWNED BY usuario.codigo;

CREATE SEQUENCE grupo_codigo_seq;
CREATE TABLE grupo (
    codigo BIGINT PRIMARY KEY NOT NULL DEFAULT nextval('grupo_codigo_seq'),
    nome VARCHAR(50) NOT NULL
);
ALTER SEQUENCE grupo_codigo_seq OWNED BY grupo.codigo;

CREATE SEQUENCE permissao_codigo_seq;
CREATE TABLE permissao (
    codigo BIGINT PRIMARY KEY NOT NULL DEFAULT nextval('permissao_codigo_seq'),
    nome VARCHAR(50) NOT NULL
);
ALTER SEQUENCE permissao_codigo_seq OWNED BY permissao.codigo;

CREATE TABLE usuario_grupo (
    codigo_usuario BIGINT NOT NULL,
    codigo_grupo BIGINT NOT NULL,
    PRIMARY KEY (codigo_usuario, codigo_grupo),
    FOREIGN KEY (codigo_usuario) REFERENCES usuario(codigo),
    FOREIGN KEY (codigo_grupo) REFERENCES grupo(codigo)
);

CREATE TABLE grupo_permissao (
    codigo_grupo BIGINT NOT NULL,
    codigo_permissao BIGINT NOT NULL,
    PRIMARY KEY (codigo_grupo, codigo_permissao),
    FOREIGN KEY (codigo_grupo) REFERENCES grupo(codigo),
    FOREIGN KEY (codigo_permissao) REFERENCES permissao(codigo)
);