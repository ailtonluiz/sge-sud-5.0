INSERT INTO grupo_permissao (codigo_grupo, codigo_permissao) VALUES (2, 4);
INSERT INTO usuario (nome, email, senha,telefone, ativo) VALUES ('Administrador', 'administrador', '$2a$10$gT7rwpRMSJBxJtgyGRTRqOZNPZQWjrCaExHAx/42sMmknWtfAKQJq','(376) 610-680', 1);

INSERT INTO usuario_grupo (codigo_usuario, codigo_grupo) VALUES (
	(SELECT codigo FROM usuario WHERE email = 'contato@ailtonluiz.com'), 1);
	
	
--Alterar o campo quantidade_pendente para quantidade_estoque_pendente
	