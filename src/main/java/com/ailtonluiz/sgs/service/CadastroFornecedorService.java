package com.ailtonluiz.sgs.service;

import java.util.Optional;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ailtonluiz.sgs.model.Fornecedor;
import com.ailtonluiz.sgs.repository.Fornecedores;
import com.ailtonluiz.sgs.service.exception.ImpossivelExcluirEntidadeException;
import com.ailtonluiz.sgs.service.exception.NomeFornecedorJaCadastradoException;

@Service
public class CadastroFornecedorService {

	@Autowired
	private Fornecedores fornecedores;

	@Transactional
	public Fornecedor salvar(Fornecedor fornecedor) {
		Optional<Fornecedor> fornecedorOptional = fornecedores.findByNomeIgnoreCase(fornecedor.getNome());
		if (fornecedorOptional.isPresent() && fornecedor.isNovo()) {
			throw new NomeFornecedorJaCadastradoException("Nombre de proveedor ya registrado");
		}

		return fornecedores.saveAndFlush(fornecedor);
	}

	@Transactional
	public void excluir(Fornecedor fornecedor) {
		try {
			fornecedores.delete(fornecedor);
			fornecedores.flush();
		} catch (PersistenceException e) {
			throw new ImpossivelExcluirEntidadeException("Imposible borrar este proveedor. Hay movimiento.");
		}
	}

}
