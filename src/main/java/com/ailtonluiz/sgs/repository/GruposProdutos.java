package com.ailtonluiz.sgs.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ailtonluiz.sgs.model.GrupoProdutos;
import com.ailtonluiz.sgs.repository.helper.grupo.GruposProdutosQueries;

@Repository
public interface GruposProdutos extends JpaRepository<GrupoProdutos, Long>, GruposProdutosQueries {
	
	public Optional<GrupoProdutos> findByNomeIgnoreCase(String nome);

}
