package com.ailtonluiz.sgs.service.event.compra;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.ailtonluiz.sgs.model.ItemCompra;
import com.ailtonluiz.sgs.model.Produto;
import com.ailtonluiz.sgs.repository.Produtos;

@Component
public class CompraListener {

	@Autowired
	private Produtos produtos;
	
	@EventListener
	public void compraEmitida(CompraEvent compraEvent) {
		for (ItemCompra item : compraEvent.getCompra().getItens()) {
			Produto produto = produtos.findOne(item.getProduto().getCodigo());
			produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() + item.getQuantidade());
			produto.setDataUltimaEntrada(LocalDateTime.now());
			produtos.save(produto);
		}
	}
	
	@EventListener
	public void compraPendente(CompraPendenteEvent compraPendenteEvent) {
		for (ItemCompra item : compraPendenteEvent.getCompra().getItens()) {
			Produto produto = produtos.findOne(item.getProduto().getCodigo());
			produto.setQuantidadeEstoquePendente(produto.getQuantidadeEstoquePendente() + item.getQuantidade());
			produtos.save(produto);
		}
	}
	@EventListener
	public void compraPendenteBaixa(CompraPendenteBaixaEvent compraPendenteBaixaEvent) {
		for (ItemCompra item : compraPendenteBaixaEvent.getCompra().getItens()) {
			Produto produto = produtos.findOne(item.getProduto().getCodigo());
			produto.setQuantidadeEstoquePendente(produto.getQuantidadeEstoque() + item.getQuantidade());
			produtos.save(produto);
		}
	}
	
	@EventListener
	public void compraCancelada(CompraCancelaEvent compraCancelaEvent){
		for (ItemCompra item : compraCancelaEvent.getCompra().getItens()){
			Produto produto = produtos.findOne(item.getProduto().getCodigo());
			produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - item.getQuantidade());
			produtos.save(produto);
		}
	}
}
