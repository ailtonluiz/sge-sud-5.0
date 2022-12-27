package com.ailtonluiz.sgs.repository.filter;

public class GrupoProdutosFilter {

	private String nome;
	
	private boolean ativo = true;

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
