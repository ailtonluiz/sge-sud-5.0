package com.ailtonluiz.sgs.service;

import java.util.Optional;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ailtonluiz.sgs.model.GrupoProdutos;
import com.ailtonluiz.sgs.repository.GruposProdutos;
import com.ailtonluiz.sgs.service.exception.ImpossivelExcluirEntidadeException;
import com.ailtonluiz.sgs.service.exception.NomeGrupoProdutosJaCadastradoException;

@Service
public class CadastroGrupoProdutosService {

	@Autowired
	private GruposProdutos gruposProdutos;

	@Transactional
	public GrupoProdutos salvar(GrupoProdutos grupoProdutos) {

		Optional<GrupoProdutos> grupoProdutosOptional = gruposProdutos.findByNomeIgnoreCase(grupoProdutos.getNome());
		if (grupoProdutosOptional.isPresent() && grupoProdutos.isNovo()) {
			throw new NomeGrupoProdutosJaCadastradoException("Nombre del grupo ya registrado");
		}

		return gruposProdutos.saveAndFlush(grupoProdutos);
	}

	@Transactional
	public void excluir(GrupoProdutos grupoProdutos) {
		try {
			gruposProdutos.delete(grupoProdutos);
			gruposProdutos.flush();
		} catch (PersistenceException e) {
			throw new ImpossivelExcluirEntidadeException("Imposible borrar este grupo. Hay movimiento.");
		}
	}

}
