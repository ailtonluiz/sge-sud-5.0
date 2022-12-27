package com.ailtonluiz.sgs.service.event.venda;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.ailtonluiz.sgs.model.ItemVenda;
import com.ailtonluiz.sgs.model.Produto;
import com.ailtonluiz.sgs.repository.Produtos;

@Component
public class VendaListener {

	@Autowired
	private Produtos produtos;

	@EventListener
	public void vendaEmitida(VendaEvent vendaEvent) {
		for (ItemVenda item : vendaEvent.getVenda().getItens()) {
			Produto produto = produtos.findOne(item.getProduto().getCodigo());
			produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - item.getQuantidade());
			produto.setDataUltimaSaida(LocalDateTime.now());
			produtos.save(produto);
		}
	}

	@EventListener
	public void vendaCancelada(CancelaVendaEvent cancelaVendaEvent) {
		for (ItemVenda item : cancelaVendaEvent.getVenda().getItens()) {
			Produto produto = produtos.findOne(item.getProduto().getCodigo());
			produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() + item.getQuantidade());
			produtos.save(produto);
		}
	}

	@EventListener
	public void vendaBaixa(BaixaVendaEvent baixaVendaEvent) {
		for (ItemVenda item : baixaVendaEvent.getVenda().getItens()) {
			Produto produto = produtos.findOne(item.getProduto().getCodigo());
			produto.setQuantidadeEstoquePendente(produto.getQuantidadeEstoquePendente() - item.getQuantidade());
			produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - item.getQuantidade());
			produtos.save(produto);
		}
	}

}