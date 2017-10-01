CREATE SEQUENCE venda_codigo_seq;
CREATE TABLE venda (
    codigo BIGINT PRIMARY KEY NOT NULL DEFAULT nextval('venda_codigo_seq'),
    data_criacao TIMESTAMP NOT NULL,
    valor_frete DECIMAL(10,2),
    valor_desconto DECIMAL(10,2),
    valor_total DECIMAL(10,2) NOT NULL,
    status VARCHAR(30) NOT NULL,
    observacao VARCHAR(200),
    data_hora_entrega TIMESTAMP,
    codigo_cliente BIGINT NOT NULL,
    codigo_usuario BIGINT NOT NULL,
    FOREIGN KEY (codigo_cliente) REFERENCES cliente(codigo),
    FOREIGN KEY (codigo_usuario) REFERENCES usuario(codigo)
);
ALTER SEQUENCE venda_codigo_seq OWNED BY venda.codigo;

CREATE SEQUENCE item_venda_codigo_seq;
CREATE TABLE item_venda (
    codigo BIGINT PRIMARY KEY NOT NULL DEFAULT nextval('item_venda_codigo_seq'),
    quantidade INTEGER NOT NULL,
    valor_unitario DECIMAL(10,2) NOT NULL,
    codigo_cerveja BIGINT NOT NULL,
    codigo_venda BIGINT NOT NULL,
    FOREIGN KEY (codigo_cerveja) REFERENCES cerveja(codigo),
    FOREIGN KEY (codigo_venda) REFERENCES venda(codigo)
);
ALTER SEQUENCE item_venda_codigo_seq OWNED BY item_venda.codigo;