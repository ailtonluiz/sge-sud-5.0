package com.ailtonluiz.sgs.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ailtonluiz.sgs.model.Cliente;
import com.ailtonluiz.sgs.repository.helper.cliente.ClientesQueries;

@Repository
public interface Clientes extends JpaRepository<Cliente, Long>, ClientesQueries {

	public Optional<Cliente> findByNomeIgnoreCase(String nome);
	

	public List<Cliente> findByNomeStartingWithIgnoreCase(String nome);
}
