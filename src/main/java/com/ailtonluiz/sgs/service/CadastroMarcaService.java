package com.ailtonluiz.sgs.service;

import java.util.Optional;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ailtonluiz.sgs.model.Marca;
import com.ailtonluiz.sgs.repository.Marcas;
import com.ailtonluiz.sgs.service.exception.ImpossivelExcluirEntidadeException;
import com.ailtonluiz.sgs.service.exception.NomeMarcaJaCadastradoException;

@Service
public class CadastroMarcaService {

	@Autowired
	private Marcas marcas;

	@Transactional
	public Marca salvar(Marca marca) {
		Optional<Marca> marcaOptional = marcas.findByNomeIgnoreCase(marca.getNome());
		if (marcaOptional.isPresent() && marca.isNovo()) {
			throw new NomeMarcaJaCadastradoException("Nombre de marca ya registrado");
		}
		
		
		return marcas.saveAndFlush(marca);
	}

	@Transactional
	public void excluir(Marca marca) {
		try {
			marcas.delete(marca);
			marcas.flush();
		} catch (PersistenceException e) {
			throw new ImpossivelExcluirEntidadeException("Imposible borrar esta marca. Hay movimiento.");
		}
	}

}



	
	