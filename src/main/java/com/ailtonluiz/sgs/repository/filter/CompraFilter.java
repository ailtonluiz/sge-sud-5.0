package com.ailtonluiz.sgs.repository.filter;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.ailtonluiz.sgs.model.StatusCompra;

public class CompraFilter {

	private Long codigo;
	private StatusCompra status;

	private LocalDate desde;
	private LocalDate ate;
	private BigDecimal valorMinimo;
	private BigDecimal valorMaximo;

	private String nomeFornecedor;

	private String nrAlbara;

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public StatusCompra getStatus() {
		return status;
	}

	public void setStatus(StatusCompra status) {
		this.status = status;
	}

	public LocalDate getDesde() {
		return desde;
	}

	public void setDesde(LocalDate desde) {
		this.desde = desde;
	}

	public LocalDate getAte() {
		return ate;
	}

	public void setAte(LocalDate ate) {
		this.ate = ate;
	}

	public BigDecimal getValorMinimo() {
		return valorMinimo;
	}

	public void setValorMinimo(BigDecimal valorMinimo) {
		this.valorMinimo = valorMinimo;
	}

	public BigDecimal getValorMaximo() {
		return valorMaximo;
	}

	public void setValorMaximo(BigDecimal valorMaximo) {
		this.valorMaximo = valorMaximo;
	}

	public String getNomeFornecedor() {
		return nomeFornecedor;
	}

	public void setNomeFornecedor(String nomeFornecedor) {
		this.nomeFornecedor = nomeFornecedor;
	}

	public String getNrAlbara() {
		return nrAlbara;
	}

	public void setNrAlbara(String nrAlbara) {
		this.nrAlbara = nrAlbara;
	}

}
