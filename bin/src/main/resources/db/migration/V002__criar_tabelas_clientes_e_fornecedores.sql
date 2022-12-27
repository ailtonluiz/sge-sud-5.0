CREATE TABLE cliente (
    codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(80),
    responsavel VARCHAR(80),
    telefone VARCHAR(80),
    observacao VARCHAR(120),
    ativo INT(11)
    
) ENGINE=InnoDB DEFAULT CHARSET=utf8;	

CREATE TABLE fornecedor (
    codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(80),
    telefone VARCHAR(80),
    observacao VARCHAR(120),
    ativo INT(11)
    
) ENGINE=InnoDB DEFAULT CHARSET=utf8;	