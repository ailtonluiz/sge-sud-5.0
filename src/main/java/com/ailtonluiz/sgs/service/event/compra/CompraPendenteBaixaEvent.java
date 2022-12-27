package com.ailtonluiz.sgs.service.event.compra;

import com.ailtonluiz.sgs.model.Compra;

public class CompraPendenteBaixaEvent {

	private Compra compra;

	public CompraPendenteBaixaEvent(Compra compra) {
		this.compra = compra;
	}

	public Compra getCompra() {
		return compra;
	}
}
