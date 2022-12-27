package com.ailtonluiz.sgs.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ailtonluiz.sgs.model.Fornecedor;
import com.ailtonluiz.sgs.repository.helper.fornecedor.FornecedoresQueries;

@Repository
public interface Fornecedores extends JpaRepository<Fornecedor, Long>, FornecedoresQueries {

	public Optional<Fornecedor> findByNomeIgnoreCase(String nome);
	
	public List<Fornecedor> findByNomeStartingWithIgnoreCase(String nome);
}
