package com.ailtonluiz.sgs.service;

import java.util.Optional;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ailtonluiz.sgs.model.SubgrupoProdutos;
import com.ailtonluiz.sgs.repository.SubgruposProdutos;
import com.ailtonluiz.sgs.service.exception.ImpossivelExcluirEntidadeException;
import com.ailtonluiz.sgs.service.exception.NomeSubgrupoProdutosJaCadastradoException;

@Service
public class CadastroSubgruposProdutosService {

	@Autowired
	private SubgruposProdutos subgruposProdutos;

	@Transactional
	public void salvar(SubgrupoProdutos subgrupoProdutos) {

		Optional<SubgrupoProdutos> subgrupoExistente = subgruposProdutos.findByNomeAndGrupoProdutos(subgrupoProdutos.getNome(),
				subgrupoProdutos.getGrupoProdutos());
		if (subgrupoExistente.isPresent() && subgrupoProdutos.isNovo()) {
			throw new NomeSubgrupoProdutosJaCadastradoException("Nombre del subgrupo ya registrado");
		}

		subgruposProdutos.save(subgrupoProdutos);

	}
	
	@Transactional
	public void excluir(SubgrupoProdutos subgrupoProdutos) {
		try {
			subgruposProdutos.delete(subgrupoProdutos);
			subgruposProdutos.flush();
		} catch (PersistenceException e) {
			throw new ImpossivelExcluirEntidadeException("Imposible borrar este subgrupo. Hay movimiento.");
		}
	}

}
