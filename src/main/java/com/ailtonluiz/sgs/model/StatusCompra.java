package com.ailtonluiz.sgs.model;

public enum StatusCompra {


	ENTREGUE("Entregado"),
	ORCAMENTO("Presupuesto"), 
	PENDENTE("Mercancías Rota"), 
	CANCELADA("Cancelado");

	private String descricao;

	StatusCompra(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
}
