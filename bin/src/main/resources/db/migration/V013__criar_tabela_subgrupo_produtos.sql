CREATE TABLE subgrupo_produtos (
    codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(50) NOT NULL,
    codigo_grupo_produto BIGINT(20) NOT NULL,
    FOREIGN KEY (codigo_grupo_produto) REFERENCES grupo_produtos(codigo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;