package com.ailtonluiz.sgs.dto;

import java.math.BigDecimal;

import org.springframework.util.StringUtils;

public class ProdutoDTO {

	private Long codigo;
	private String codigoBarras;
	private String nomeProduto;
	private BigDecimal custoVenda;
	private BigDecimal custoCompra;
	private String foto;
	private String referencia;
	private String referenciaFornecedor;
	private String marca;
	private Integer quantidadeEstoque;
	private Integer quantidadeEstoquePendente;
	private Integer quantidadeCaixa;
	private boolean ativo;

	public ProdutoDTO(Long codigo, String codigoBarras, String nomeProduto, BigDecimal custoVenda,
			BigDecimal custoCompra, String foto, String referencia, String referenciaFornecedor, String marca, Integer quantidadeEstoque,
			Integer quantidadeEstoquePendente, Integer quantidadeCaixa, boolean ativo) {

		this.codigo = codigo;
		this.codigoBarras = codigoBarras;
		this.nomeProduto = nomeProduto;
		this.custoVenda = custoVenda;
		this.custoCompra = custoCompra;
		this.foto = StringUtils.isEmpty(foto) ? "login.png" : foto;
		this.referencia = referencia;
		this.referenciaFornecedor = referenciaFornecedor;
		this.marca = marca;
		this.quantidadeEstoque = quantidadeEstoque;
		this.quantidadeEstoquePendente = quantidadeEstoquePendente;
		this.quantidadeCaixa = quantidadeCaixa;
		this.ativo = ativo;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getCodigoBarras() {
		return codigoBarras;
	}

	public void setCodigoBarras(String codigoBarras) {
		this.codigoBarras = codigoBarras;
	}

	public String getNomeProduto() {
		return nomeProduto;
	}

	public void setNomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}

	public BigDecimal getCustoVenda() {
		return custoVenda;
	}

	public void setCustoVenda(BigDecimal custoVenda) {
		this.custoVenda = custoVenda;
	}

	public BigDecimal getCustoCompra() {
		return custoCompra;
	}

	public void setCustoCompra(BigDecimal custoCompra) {
		this.custoCompra = custoCompra;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}
	
	

	public String getReferenciaFornecedor() {
		return referenciaFornecedor;
	}

	public void setReferenciaFornecedor(String referenciaFornecedor) {
		this.referenciaFornecedor = referenciaFornecedor;
	}
	
	

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public Integer getQuantidadeEstoque() {
		return quantidadeEstoque;
	}

	public void setQuantidadeEstoque(Integer quantidadeEstoque) {
		this.quantidadeEstoque = quantidadeEstoque;
	}

	public Integer getQuantidadeEstoquePendente() {
		return quantidadeEstoquePendente;
	}

	public void setQuantidadeEstoquePendente(Integer quantidadeEstoquePendente) {
		this.quantidadeEstoquePendente = quantidadeEstoquePendente;
	}

	public Integer getQuantidadeCaixa() {
		return quantidadeCaixa;
	}

	public void setQuantidadeCaixa(Integer quantidadeCaixa) {
		this.quantidadeCaixa = quantidadeCaixa;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

}