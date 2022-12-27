INSERT INTO usuario (nome, email, senha,telefone, ativo) VALUES ('Ailton Luiz', 'contato@ailtonluiz.com', '$2a$10$ypCO1ozteY19XsuwxtVwh.DvbWeD3Ei61w3m9CeCpEiWhVPjLGQxW','(376) 610-680', 1);
INSERT INTO usuario (nome, email, senha,telefone, ativo) VALUES ('Administrador', 'administrador', '$2a$10$ypCO1ozteY19XsuwxtVwh.DvbWeD3Ei61w3m9CeCpEiWhVPjLGQxW','(376) 610-680', 1);

INSERT INTO permissao VALUES (1, 'ROLE_CADASTRAR_USUARIO');
INSERT INTO permissao VALUES (2, 'ROLE_CADASTRAR_PRODUTO');
INSERT INTO permissao VALUES (3, 'ROLE_CADASTRAR_COMPRA');
INSERT INTO permissao VALUES (4, 'ROLE_CADASTRAR_VENDA');
INSERT INTO permissao VALUES (5, 'ROLE_CADASTRAR_GRUPO');
INSERT INTO permissao VALUES (6, 'ROLE_CADASTRAR_ESTOQUE');
INSERT INTO permissao VALUES (7, 'ROLE_CADASTRAR_MARCA');
INSERT INTO permissao VALUES (8, 'ROLE_CANCELAR_VENDA');
INSERT INTO permissao VALUES (9, 'ROLE_CANCELAR_COMPRA');
INSERT INTO permissao VALUES (10, 'ROLE_ACESSAR_RELATORIO');
INSERT INTO permissao VALUES (11, 'ROLE_ACESSAR_PARAMETRO');

INSERT INTO grupo_permissao (codigo_grupo, codigo_permissao) VALUES (1, 1);
INSERT INTO grupo_permissao (codigo_grupo, codigo_permissao) VALUES (1, 2);
INSERT INTO grupo_permissao (codigo_grupo, codigo_permissao) VALUES (1, 3);
INSERT INTO grupo_permissao (codigo_grupo, codigo_permissao) VALUES (2, 3);
INSERT INTO grupo_permissao (codigo_grupo, codigo_permissao) VALUES (1, 4);
INSERT INTO grupo_permissao (codigo_grupo, codigo_permissao) VALUES (2, 4);
INSERT INTO grupo_permissao (codigo_grupo, codigo_permissao) VALUES (1, 5);
INSERT INTO grupo_permissao (codigo_grupo, codigo_permissao) VALUES (1, 6);
INSERT INTO grupo_permissao (codigo_grupo, codigo_permissao) VALUES (1, 7);
INSERT INTO grupo_permissao (codigo_grupo, codigo_permissao) VALUES (1, 8);
INSERT INTO grupo_permissao (codigo_grupo, codigo_permissao) VALUES (1, 9);
INSERT INTO grupo_permissao (codigo_grupo, codigo_permissao) VALUES (1, 10);
INSERT INTO grupo_permissao (codigo_grupo, codigo_permissao) VALUES (1, 11);


INSERT INTO usuario_grupo (codigo_usuario, codigo_grupo) VALUES (
	(SELECT codigo FROM usuario WHERE email = 'contato@ailtonluiz.com'), 1);