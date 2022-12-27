CREATE TABLE parametro (
    codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    nome_empresa VARCHAR(100) NOT NULL,
    email VARCHAR(80),
    responsavel VARCHAR(80),
    telefone VARCHAR(80),
    observacao VARCHAR(120),
    versao VARCHAR(45),
    ativo INT(11)
    
) ENGINE=InnoDB DEFAULT CHARSET=utf8;	


INSERT INTO parametro (nome_empresa, ativo) VALUES ('EMPRESA',1);