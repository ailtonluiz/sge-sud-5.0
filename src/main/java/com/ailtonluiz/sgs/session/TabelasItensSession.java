package com.ailtonluiz.sgs.session;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import com.ailtonluiz.sgs.model.ItemCompra;
import com.ailtonluiz.sgs.model.ItemVenda;
import com.ailtonluiz.sgs.model.Produto;

@SessionScope
@Component
public class TabelasItensSession {

	/*
	 * Referente a tabela de vendas
	 * 
	 */

	private Set<TabelaItensVenda> tabelas = new HashSet<>();

	public void adicionarItem(String uuid, Produto produto, int quantidade) {
		TabelaItensVenda tabela = buscarTabelaPorUuid(uuid);
		tabela.adicionarItem(produto, quantidade);
		tabelas.add(tabela);

	}

	public void alterarQuantidadeItens(String uuid, Produto produto, Integer quantidade) {
		TabelaItensVenda tabela = buscarTabelaPorUuid(uuid);
		tabela.alterarQuantidadeItens(produto, quantidade);
	}

	public void excluirItem(String uuid, Produto produto) {
		TabelaItensVenda tabela = buscarTabelaPorUuid(uuid);
		tabela.excluirItem(produto);

	}

	public List<ItemVenda> getItens(String uuid) {
		return buscarTabelaPorUuid(uuid).getItens();
	}

	public Object getValorTotal(String uuid) {
		return buscarTabelaPorUuid(uuid).getValorTotal();
	}

	private TabelaItensVenda buscarTabelaPorUuid(String uuid) {
		TabelaItensVenda tabela = tabelas.stream().filter(t -> t.getUuid().equals(uuid)).findAny()
				.orElse(new TabelaItensVenda(uuid));
		return tabela;
	}

	/*
	 * Refente a tabela de Compras
	 * 
	 */

	private Set<TabelaItensCompra> tabelasCompras = new HashSet<>();

	public void adicionarItemCompra(String uuid, Produto produto, int quantidade) {
		TabelaItensCompra tabelaCompra = buscarTabelaPorUuidCompra(uuid);
		tabelaCompra.adicionarItemCompra(produto, quantidade);
		tabelasCompras.add(tabelaCompra);

	}

	public void alterarQuantidadeItensCompra(String uuid, Produto produto, Integer quantidade) {
		TabelaItensCompra tabelaCompra = buscarTabelaPorUuidCompra(uuid);
		tabelaCompra.alterarQuantidadeItensCompra(produto, quantidade);

	}

	public void excluirItemCompra(String uuid, Produto produto) {
		TabelaItensCompra tabelaCompra = buscarTabelaPorUuidCompra(uuid);
		tabelaCompra.excluirItemCompra(produto);

	}

	public List<ItemCompra> getItensCompra(String uuid) {
		return buscarTabelaPorUuidCompra(uuid).getItensCompra();
	}

	public Object getValorTotalCompra(String uuid) {
		return buscarTabelaPorUuidCompra(uuid).getValorTotalCompra();
	}

	private TabelaItensCompra buscarTabelaPorUuidCompra(String uuid) {
		TabelaItensCompra tabelaCompra = tabelasCompras.stream().filter(t -> t.getUuid().equals(uuid)).findAny()
				.orElse(new TabelaItensCompra(uuid));
		return tabelaCompra;
	}

}
