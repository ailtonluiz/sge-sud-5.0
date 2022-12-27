package com.ailtonluiz.sgs.service.event.compra;

import com.ailtonluiz.sgs.model.Compra;

public class CompraPendenteEvent {

	private Compra compra;

	public CompraPendenteEvent(Compra compra) {
		this.compra = compra;
	}

	public Compra getCompra() {
		return compra;
	}
}
