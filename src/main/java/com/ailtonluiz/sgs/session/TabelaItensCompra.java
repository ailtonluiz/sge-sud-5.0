package com.ailtonluiz.sgs.session;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import com.ailtonluiz.sgs.model.ItemCompra;
import com.ailtonluiz.sgs.model.Produto;

class TabelaItensCompra {

	private String uuid;
	private List<ItemCompra> itens = new ArrayList<>();

	public TabelaItensCompra(String uuid) {
		this.uuid = uuid;
	}

	public BigDecimal getValorTotalCompra() {
		return itens.stream().map(ItemCompra::getValorTotalCompra).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
	}

	public void adicionarItemCompra(Produto produto, Integer quantidade) {
		Optional<ItemCompra> itemCompraOptional = buscarItemPorProdutoCompra(produto);

		ItemCompra itemCompra = null;
		if (itemCompraOptional.isPresent()) {
			itemCompra = itemCompraOptional.get();
			itemCompra.setQuantidade(itemCompra.getQuantidade() + quantidade);
		} else {
			itemCompra = new ItemCompra();
			itemCompra.setProduto(produto);
			itemCompra.setQuantidade(quantidade);
			itemCompra.setValorUnitario(produto.getCustoCompra());
			itens.add(0, itemCompra);
		}
	}

	public void alterarQuantidadeItensCompra(Produto produto, Integer quantidade) {
		ItemCompra itemCompra = buscarItemPorProdutoCompra(produto).get();
		itemCompra.setQuantidade(quantidade);
	}

	public void excluirItemCompra(Produto produto) {
		int indice = IntStream.range(0, itens.size()).filter(i -> itens.get(i).getProduto().equals(produto)).findAny()
				.getAsInt();
		itens.remove(indice);
	}

	public int total() {
		return itens.size();
	}

	public List<ItemCompra> getItensCompra() {
		return itens;
	}

	private Optional<ItemCompra> buscarItemPorProdutoCompra(Produto produto) {
		return itens.stream().filter(i -> i.getProduto().equals(produto)).findAny();
	}

	public String getUuid() {
		return uuid;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TabelaItensCompra other = (TabelaItensCompra) obj;
		if (uuid == null) {
			if (other.uuid != null)
				return false;
		} else if (!uuid.equals(other.uuid))
			return false;
		return true;
	}


}