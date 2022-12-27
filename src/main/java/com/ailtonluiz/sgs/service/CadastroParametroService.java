package com.ailtonluiz.sgs.service;

import java.util.Optional;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ailtonluiz.sgs.model.Parametro;
import com.ailtonluiz.sgs.repository.Parametros;
import com.ailtonluiz.sgs.service.exception.ImpossivelExcluirEntidadeException;
import com.ailtonluiz.sgs.service.exception.NomeEmpresaJaCadastradoException;

@Service
public class CadastroParametroService {

	@Autowired
	private Parametros parametros;

	@Transactional
	public Parametro salvar(Parametro parametro) {
		Optional<Parametro> parametroOptional = parametros.findByNomeEmpresaIgnoreCase(parametro.getNomeEmpresa());
		if (parametroOptional.isPresent( )&& parametro.isNovo()) {
			throw new NomeEmpresaJaCadastradoException("Nombre de empresa ya registrado");
		}
		
		
		return parametros.saveAndFlush(parametro);
	}

	@Transactional
	public void excluir(Parametro parametro) {
		try {
			parametros.delete(parametro);
			parametros.flush();
		} catch (PersistenceException e) {
			throw new ImpossivelExcluirEntidadeException("Imposible borrar este parametro. Hay movimiento.");
		}
	}

}



	
	