package com.ailtonluiz.sgs.service.event.venda;

import com.ailtonluiz.sgs.model.Venda;

public class BaixaVendaEvent {

	private Venda venda;

	public BaixaVendaEvent(Venda venda) {
		this.venda = venda;
	}

	public Venda getVenda() {
		return venda;
	}

}