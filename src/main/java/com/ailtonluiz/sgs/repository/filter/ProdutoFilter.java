package com.ailtonluiz.sgs.repository.filter;

import java.math.BigDecimal;

import com.ailtonluiz.sgs.model.SubgrupoProdutos;
import com.ailtonluiz.sgs.model.GrupoProdutos;
import com.ailtonluiz.sgs.model.Marca;
import com.ailtonluiz.sgs.model.Unidade;

public class ProdutoFilter {

	private String nomeProduto;
	private String codigoBarras;
	private String referencia;
	private String referenciaFornecedor;
	private Marca marca;
	private GrupoProdutos grupoProdutos;
	private SubgrupoProdutos subgrupoProdutos;
	private boolean ativo = true;
	private BigDecimal valorDe;
	private BigDecimal valorAte;
	private Integer estoqueDe;
	private Integer estoqueAte;
	private Unidade unidade;
	private Long codigo;

	public String getNomeProduto() {
		return nomeProduto;
	}

	public void setNomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}

	public String getCodigoBarras() {
		return codigoBarras;
	}

	public void setCodigoBarras(String codigoBarras) {
		this.codigoBarras = codigoBarras;
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

	public SubgrupoProdutos getSubgrupoProdutos() {
		return subgrupoProdutos;
	}

	public void setSubgrupoProdutos(SubgrupoProdutos subgrupoProdutos) {
		this.subgrupoProdutos = subgrupoProdutos;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public BigDecimal getValorDe() {
		return valorDe;
	}

	public void setValorDe(BigDecimal valorDe) {
		this.valorDe = valorDe;
	}

	public BigDecimal getValorAte() {
		return valorAte;
	}

	public void setValorAte(BigDecimal valorAte) {
		this.valorAte = valorAte;
	}

	public Integer getEstoqueDe() {
		return estoqueDe;
	}

	public void setEstoqueDe(Integer estoqueDe) {
		this.estoqueDe = estoqueDe;
	}

	public Integer getEstoqueAte() {
		return estoqueAte;
	}

	public void setEstoqueAte(Integer estoqueAte) {
		this.estoqueAte = estoqueAte;
	}

	public Unidade getUnidade() {
		return unidade;
	}

	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

}
