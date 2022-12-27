package com.ailtonluiz.sgs.service;

import java.util.Optional;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ailtonluiz.sgs.model.Cliente;
import com.ailtonluiz.sgs.repository.Clientes;
import com.ailtonluiz.sgs.service.exception.ImpossivelExcluirEntidadeException;
import com.ailtonluiz.sgs.service.exception.NomeClienteJaCadastradoException;

@Service
public class CadastroClienteService {

	@Autowired
	private Clientes clientes;

	@Transactional
	public Cliente salvar(Cliente cliente) {
		Optional<Cliente> clienteOptional = clientes.findByNomeIgnoreCase(cliente.getNome());
		if (clienteOptional.isPresent()&& cliente.isNovo()) {
			throw new NomeClienteJaCadastradoException("Nombre de cliente ya registrado");
		}
		
		
		return clientes.saveAndFlush(cliente);
	}

	@Transactional
	public void excluir(Cliente cliente) {
		try {
			clientes.delete(cliente);
			clientes.flush();
		} catch (PersistenceException e) {
			throw new ImpossivelExcluirEntidadeException("Imposible borrar este cliente. Hay movimiento.");
		}
	}

}



	
	