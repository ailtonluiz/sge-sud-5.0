package com.ailtonluiz.sgs.service.event.venda;

import com.ailtonluiz.sgs.model.Venda;

public class CancelaVendaEvent {
	private Venda venda;

	public CancelaVendaEvent(Venda venda) {
		this.venda = venda;
	}

	public Venda getVenda() {
		return venda;
	}

}
