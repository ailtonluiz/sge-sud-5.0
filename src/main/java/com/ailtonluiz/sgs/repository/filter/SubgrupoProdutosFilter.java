package com.ailtonluiz.sgs.repository.filter;

import com.ailtonluiz.sgs.model.GrupoProdutos;

public class SubgrupoProdutosFilter {

	private GrupoProdutos grupoProdutos;

	private String nome;
	
	private boolean ativo = true;


	public GrupoProdutos getGrupoProdutos() {
		return grupoProdutos;
	}

	public void setGrupoProdutos(GrupoProdutos grupoProdutos) {
		this.grupoProdutos = grupoProdutos;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

}
