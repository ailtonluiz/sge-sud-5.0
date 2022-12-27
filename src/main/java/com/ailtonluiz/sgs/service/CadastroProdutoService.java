package com.ailtonluiz.sgs.service;

import java.util.Optional;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ailtonluiz.sgs.model.Produto;
import com.ailtonluiz.sgs.repository.Produtos;
import com.ailtonluiz.sgs.service.event.produto.ProdutoSalvaEvent;
import com.ailtonluiz.sgs.service.exception.CodigoBarrasJaCadastradoException;
import com.ailtonluiz.sgs.service.exception.ImpossivelExcluirEntidadeException;
import com.ailtonluiz.sgs.storage.FotoStorage;

@Service
public class CadastroProdutoService {

	@Autowired
	private Produtos produtos;

	@Autowired
	private FotoStorage fotoStorage;
	
	@Autowired
	private ApplicationEventPublisher publisher;

	@Transactional
	public void salvar(Produto produto) {
		Optional<Produto> codigoBarrasExistente = produtos.findByCodigoBarras(produto.getCodigoBarras());
		if (codigoBarrasExistente.isPresent() && produto.isNovo()) {
			throw new CodigoBarrasJaCadastradoException("Código de barras ya registrado ya registrado");
		}

		produtos.save(produto);

		publisher.publishEvent(new ProdutoSalvaEvent(produto));
	}
	
	@Transactional
	public void excluir(Produto produto) {
		try {
			String foto = produto.getFoto();
			produtos.delete(produto);
			produtos.flush();
			fotoStorage.excluir(foto);
		} catch (PersistenceException e) {
			throw new ImpossivelExcluirEntidadeException("Imposible borrar este artículo. Hay movimiento.");
		}
	}
}
