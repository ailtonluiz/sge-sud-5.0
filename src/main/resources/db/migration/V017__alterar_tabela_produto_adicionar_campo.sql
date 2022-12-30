	
ALTER TABLE produto
   ADD codigo_subgrupo_produtos BIGINT(20);	
   
   
   
ALTER TABLE produto
    ADD CONSTRAINT fk_produto_subgrupo
        FOREIGN KEY (codigo_subgrupo_produtos) REFERENCES subgrupo_produtos (codigo);

