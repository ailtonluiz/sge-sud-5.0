package com.ailtonluiz.sgs.dto;

import java.time.LocalDate;

import com.ailtonluiz.sgs.model.GrupoProdutos;
import com.ailtonluiz.sgs.model.Marca;
import com.ailtonluiz.sgs.model.StatusCompra;
import com.ailtonluiz.sgs.model.StatusVenda;

public class FiltrosRelatorio {

	private LocalDate dataInicio;
	private LocalDate dataFim;
	private Marca marca;
	private GrupoProdutos grupoProdutos;
	private boolean ativo = true;
	private StatusVenda statusVenda;
	private StatusCompra statusCompra;
	private boolean stock = true;
	private boolean tabaco = true;

	public LocalDate getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(LocalDate dataInicio) {
		this.dataInicio = dataInicio;
	}

	public LocalDate getDataFim() {
		return dataFim;
	}

	public void setDataFim(LocalDate dataFim) {
		this.dataFim = dataFim;
	}

	public Marca getMarca() {
		return marca;
	}

	public void setMarca(Marca marca) {
		this.marca = marca;
	}

	public GrupoProdutos getGrupoProdutos() {
		return grupoProdutos;
	}

	public void setGrupoProdutos(GrupoProdutos grupoProdutos) {
		this.grupoProdutos = grupoProdutos;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public StatusVenda getStatusVenda() {
		return statusVenda;
	}

	public void setStatusVenda(StatusVenda statusVenda) {
		this.statusVenda = statusVenda;
	}

	public StatusCompra getStatusCompra() {
		return statusCompra;
	}

	public void setStatusCompra(StatusCompra statusCompra) {
		this.statusCompra = statusCompra;
	}

	public boolean isStock() {
		return stock;
	}

	public void setStock(boolean stock) {
		this.stock = stock;
	}

	public boolean isTabaco() {
		return tabaco;
	}

	public void setTabaco(boolean tabaco) {
		this.tabaco = tabaco;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (ativo ? 1231 : 1237);
		result = prime * result + ((dataFim == null) ? 0 : dataFim.hashCode());
		result = prime * result + ((dataInicio == null) ? 0 : dataInicio.hashCode());
		result = prime * result + ((grupoProdutos == null) ? 0 : grupoProdutos.hashCode());
		result = prime * result + ((marca == null) ? 0 : marca.hashCode());
		result = prime * result + ((statusCompra == null) ? 0 : statusCompra.hashCode());
		result = prime * result + ((statusVenda == null) ? 0 : statusVenda.hashCode());
		result = prime * result + (stock ? 1231 : 1237);
		result = prime * result + (tabaco ? 1231 : 1237);
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
		FiltrosRelatorio other = (FiltrosRelatorio) obj;
		if (ativo != other.ativo)
			return false;
		if (dataFim == null) {
			if (other.dataFim != null)
				return false;
		} else if (!dataFim.equals(other.dataFim))
			return false;
		if (dataInicio == null) {
			if (other.dataInicio != null)
				return false;
		} else if (!dataInicio.equals(other.dataInicio))
			return false;
		if (grupoProdutos == null) {
			if (other.grupoProdutos != null)
				return false;
		} else if (!grupoProdutos.equals(other.grupoProdutos))
			return false;
		if (marca == null) {
			if (other.marca != null)
				return false;
		} else if (!marca.equals(other.marca))
			return false;
		if (statusCompra != other.statusCompra)
			return false;
		if (statusVenda != other.statusVenda)
			return false;
		if (stock != other.stock)
			return false;
		if (tabaco != other.tabaco)
			return false;
		return true;
	}

}
