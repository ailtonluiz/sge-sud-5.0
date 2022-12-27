package com.ailtonluiz.sgs.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ailtonluiz.sgs.model.Parametro;
import com.ailtonluiz.sgs.repository.helper.parametro.ParametrosQueries;

@Repository
public interface Parametros extends JpaRepository<Parametro, Long>, ParametrosQueries {

public Optional<Parametro> findByNomeEmpresaIgnoreCase(String nomeEmpresa);
	

	public List<Parametro> findByNomeEmpresaStartingWithIgnoreCase(String nomeEmpresa);
}
