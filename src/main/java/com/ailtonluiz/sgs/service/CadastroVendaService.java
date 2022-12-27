package com.ailtonluiz.sgs.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ailtonluiz.sgs.model.StatusVenda;
import com.ailtonluiz.sgs.model.Venda;
import com.ailtonluiz.sgs.repository.Vendas;
import com.ailtonluiz.sgs.service.event.venda.BaixaVendaEvent;
import com.ailtonluiz.sgs.service.event.venda.CancelaVendaEvent;
import com.ailtonluiz.sgs.service.event.venda.VendaEvent;

@Service
public class CadastroVendaService {

	@Autowired
	private Vendas vendas;

	@Autowired
	private ApplicationEventPublisher publisher;

	@Transactional
	public Venda salvar(Venda venda) {
		if (venda.isSalvarProibido()) {
			throw new RuntimeException("Usuario que intenta guardar una venta prohibida");
		}
		if (venda.isNova()) {
			venda.setDataCriacao(LocalDateTime.now());
		} else if (!venda.isNova()) {
			Venda vendaExistente = vendas.findOne(venda.getCodigo());
			venda.setDataCriacao(vendaExistente.getDataCriacao());
		} else if (!venda.isNova() && venda.getStatus().equals(StatusVenda.EMITIDA)) {
			Venda vendaExistente = vendas.findOne(venda.getCodigo());
			vendaExistente.setStatus(StatusVenda.ORCAMENTO);
			vendas.save(vendaExistente);

			publisher.publishEvent(new CancelaVendaEvent(vendaExistente));
		}

		return vendas.saveAndFlush(venda);
	}

	@Transactional
	public void emitir(Venda venda) {
		if (venda.isNova()) {
			venda.setStatus(StatusVenda.EMITIDA);
			salvar(venda);

			publisher.publishEvent(new VendaEvent(venda));

		} else if (venda.getStatus().equals(StatusVenda.EMITIDA)) {
			venda.setStatus(StatusVenda.EMITIDA);
			salvar(venda);
		} else if (venda.getStatus().equals(StatusVenda.ORCAMENTO)) {
			venda.setStatus(StatusVenda.EMITIDA);
			salvar(venda);
			publisher.publishEvent(new VendaEvent(venda));
		}
	}

	@Transactional
	public void baixaMercadoria(Venda venda) {
		if (venda.isNova()) {
			venda.setDataCriacao(LocalDateTime.now());
			venda.setStatus(StatusVenda.BAIXA);
			salvar(venda);

			publisher.publishEvent(new BaixaVendaEvent(venda));

		} else if (venda.getStatus().equals(StatusVenda.ORCAMENTO)) {
			venda.setStatus(StatusVenda.BAIXA);
			salvar(venda);
			publisher.publishEvent(new BaixaVendaEvent(venda));
		}
	}

	@PreAuthorize("#venda.usuario == principal.usuario or hasRole('CANCELAR_VENDA')")
	@Transactional
	public void cancelar(Venda venda) {
		Venda vendaExistente = vendas.findOne(venda.getCodigo());

		vendaExistente.setStatus(StatusVenda.CANCELADA);
		vendas.save(vendaExistente);

		publisher.publishEvent(new CancelaVendaEvent(vendaExistente));
	}
}
