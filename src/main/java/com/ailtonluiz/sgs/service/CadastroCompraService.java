package com.ailtonluiz.sgs.service;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ailtonluiz.sgs.model.Compra;
import com.ailtonluiz.sgs.model.StatusCompra;
import com.ailtonluiz.sgs.repository.Compras;
import com.ailtonluiz.sgs.service.event.compra.CompraCancelaEvent;
import com.ailtonluiz.sgs.service.event.compra.CompraEvent;
import com.ailtonluiz.sgs.service.event.compra.CompraPendenteEvent;

@Service
public class CadastroCompraService {

	@Autowired
	private Compras compras;

	@Autowired
	private ApplicationEventPublisher publisher;

	@Transactional
	public void salvar(Compra compra) {

		if (compra.isSalvarProibido()) {
			throw new RuntimeException("Usuario que intenta guardar una compra prohibida");
		}
		if (compra.isNova()) {
			compra.setDataEntrada(LocalDateTime.now());

		} else {
			Compra compraExistente = compras.findOne(compra.getCodigo());
			compra.setDataEntrada(compraExistente.getDataEntrada());
		}

		compras.save(compra);
	}

	@Transactional
	public void emitir(Compra compra) {
		if (compra.isNova() || compra.getStatus().equals(StatusCompra.ORCAMENTO)) {
			compra.setStatus(StatusCompra.ENTREGUE);
			salvar(compra);
			publisher.publishEvent(new CompraEvent(compra));
		} 
		else {
			compra.setStatus(StatusCompra.ENTREGUE);
			salvar(compra);
		}
	}

	@Transactional
	public void pendente(Compra compra) {
		compra.setStatus(StatusCompra.PENDENTE);
		if (compra.isNova()) {
			compra.setDataEntrada(LocalDateTime.now());
			compra.setDataAlbara(LocalDate.now());
		} else {
			compra.setDataAlbara(compra.getDataAlbara());
		}
		salvar(compra);

		publisher.publishEvent(new CompraPendenteEvent(compra));

	}

	@PreAuthorize("#compra.usuario == principal.usuario or hasRole('CANCELAR_COMPRA')")
	@Transactional
	public void cancelar(Compra compra) {
		Compra compraExistente = compras.findOne(compra.getCodigo());

		compraExistente.setStatus(StatusCompra.CANCELADA);
		compras.save(compraExistente);

		publisher.publishEvent(new CompraCancelaEvent(compraExistente));
	}
}
