package com.ailtonluiz.sgs.service.event.compra;

import com.ailtonluiz.sgs.model.Compra;

public class CompraEvent {

	private Compra Compra;

	public CompraEvent(Compra Compra) {
		this.Compra = Compra;
	}

	public Compra getCompra() {
		return Compra;
	}
}
