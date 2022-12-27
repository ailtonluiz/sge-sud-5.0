package com.ailtonluiz.sgs.model;

public enum StatusVenda {

	EMITIDA("Emitido"),
	ORCAMENTO("Presupuesto"),
	CANCELADA("Cancelado"),
	BAIXA("Mercancías rota");

	private String descricao;

	StatusVenda(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

}