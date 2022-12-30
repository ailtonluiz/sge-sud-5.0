package com.ailtonluiz.sgs.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ailtonluiz.sgs.model.Produto;
import com.ailtonluiz.sgs.repository.helper.produto.ProdutosQueries;

@Repository
public interface Produtos extends JpaRepository<Produto, Long>, ProdutosQueries {

	public Optional<Produto> findByCodigoBarras (String codigoBarras);

	
}
