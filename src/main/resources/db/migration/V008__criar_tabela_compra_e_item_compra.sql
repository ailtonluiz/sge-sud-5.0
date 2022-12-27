CREATE TABLE compra (
    codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    data_entrada DATETIME NOT NULL,
    data_albara DATETIME NOT NULL,
    valor_igi DECIMAL(10,2),
    valor_desconto DECIMAL(10,2),
    valor_total DECIMAL(10,2) NOT NULL,
    status VARCHAR(30) NOT NULL,
    nr_albara VARCHAR(30) NOT NULL,
    observacao VARCHAR(200),
    codigo_fornecedor BIGINT(20) NOT NULL,
    codigo_usuario BIGINT(20) NOT NULL,
    FOREIGN KEY (codigo_fornecedor) REFERENCES fornecedor(codigo),
    FOREIGN KEY (codigo_usuario) REFERENCES usuario(codigo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE item_compra (
    codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    quantidade INTEGER NOT NULL,
    valor_unitario DECIMAL(10,2) NOT NULL,
    codigo_produto BIGINT(20) NOT NULL,
    codigo_compra BIGINT(20) NOT NULL,
    FOREIGN KEY (codigo_produto) REFERENCES produto(codigo),
    FOREIGN KEY (codigo_compra) REFERENCES compra(codigo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

