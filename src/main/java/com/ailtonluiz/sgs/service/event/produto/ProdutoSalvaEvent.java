package com.ailtonluiz.sgs.service.event.produto;

import org.springframework.util.StringUtils;

import com.ailtonluiz.sgs.model.Produto;

public class ProdutoSalvaEvent {

	private Produto produto;

	public ProdutoSalvaEvent(Produto produto) {
		this.produto = produto;
	}

	public Produto getProduto() {
		return produto;
	}

	public boolean temFoto() {
		return !StringUtils.isEmpty(produto.getFoto());
	}

	public boolean isNovaFoto() {
		return produto.isNovaFoto();
	}
}
