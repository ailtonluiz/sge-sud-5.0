CREATE TABLE grupo_produtos(
	codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT, 
	nome VARCHAR(50) NOT NULL,
	ativo INT(11)
	) ENGINE=InnoDB DEFAULT CHARSET= utf8;
	
	
CREATE TABLE marca(
	codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT, 
	nome VARCHAR(50) NOT NULL,
	ativo INT(11)
	) ENGINE=InnoDB DEFAULT CHARSET= utf8;

	
CREATE TABLE produto (
    codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    codigo_barras VARCHAR(14),
    nome_produto VARCHAR(100) NOT NULL,
    referencia VARCHAR(15),
    custo_venda DECIMAL(10, 2),
    custo_compra DECIMAL(10, 2),
    quantidade_estoque INTEGER,
    quantidade_estoque_minimo INTEGER,
    quantidade_estoque_pendente INTEGER,
    ativo INT(11),
    foto VARCHAR(100),
    content_type VARCHAR(100),
    codigo_grupo BIGINT(20) NOT NULL,
    codigo_marca BIGINT(20) NOT NULL,
    FOREIGN KEY (codigo_grupo) REFERENCES grupo_produtos(codigo),
    FOREIGN KEY (codigo_marca) REFERENCES marca(codigo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;	
	

INSERT INTO grupo_produtos VALUES (0, 'Tabaco', 1);
INSERT INTO marca VALUES (0, 'VANISH', 1);

