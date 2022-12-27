package com.ailtonluiz.sgs.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ailtonluiz.sgs.model.Marca;
import com.ailtonluiz.sgs.repository.helper.marca.MarcasQueries;

@Repository
public interface Marcas extends JpaRepository<Marca, Long>, MarcasQueries {

	public Optional<Marca> findByNomeIgnoreCase(String nome);
						   	
}
