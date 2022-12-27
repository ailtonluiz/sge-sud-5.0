package com.ailtonluiz.sgs.service.event.compra;

import com.ailtonluiz.sgs.model.Compra;

public class CompraCancelaEvent {
	private Compra compra;

	public CompraCancelaEvent(Compra compra) {
		this.compra = compra;
	}

	public Compra getCompra() {
		return compra;
	}
}
