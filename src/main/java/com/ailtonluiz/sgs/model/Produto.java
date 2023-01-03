
package com.ailtonluiz.sgs.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.util.StringUtils;

@Entity
@Table(name = "produto")
public class Produto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;

	// @EAN(message = "Código de barras no válido", type = Type.EAN13)
	@Column(name = "codigo_barras")
	@NotBlank(message = "Introduzca el código de barras")
	private String codigoBarras;

	@NotBlank(message = "Introduzca el nombre del producto")
	@Column(name = "nome_produto")
	private String nomeProduto;
	

	private String referencia;
	
	@Column(name = "referencia_fornecedor")
	private String referenciaFornecedor;
	

	@Column(name = "custo_venda")
	private BigDecimal custoVenda;

	@Column(name = "custo_compra")
	private BigDecimal custoCompra;

	@Column(name = "quantidade_estoque")
	private Integer quantidadeEstoque = 0;

	@NotNull(message = "Introduzca la cantidad mínima de stock")
	@Column(name = "quantidade_estoque_minimo")
	private Integer quantidadeEstoqueMinimo = 0;

	@Column(name = "quantidade_estoque_pendente")
	private Integer quantidadeEstoquePendente = 0;

	@Column(name = "quantidade_caixa")
	private Integer quantidadeCaixa = 1;

	@Column(name = "quantidade_quilo")
	private Double quantidadeQuilo;
	
	@Column(name = "quantidade_palet")
	private Double quantidadePalet;

	@NotNull(message = "Introduzca el grupo de productos")
	@ManyToOne
	@JoinColumn(name = "codigo_grupo")
	private GrupoProdutos grupoProdutos;
	
	
	@NotNull(message = "Introduzca el subgrupo de productos")
	@ManyToOne
	@JoinColumn(name = "codigo_subgrupo_produtos")
	private SubgrupoProdutos subgrupoProdutos;

	@NotNull(message = "Introduzca la marca")
	@ManyToOne
	@JoinColumn(name = "codigo_marca")
	private Marca marca;

	private boolean ativo = true;

	private String foto;

	@Column(name = "content_type")
	private String contentType;

	@Enumerated(EnumType.STRING)
	@NotNull(message = "Introduzca la unidad del producto")
	private Unidade unidade;

	@Transient
	private boolean novaFoto;

	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@Column(name = "data_ultima_saida")
	private LocalDateTime dataUltimaSaida;

	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@Column(name = "data_ultima_entrada")
	private LocalDateTime dataUltimaEntrada;

	@PrePersist
	@PreUpdate
	private void prePersistUpdate() {
		
		nomeProduto = nomeProduto.toUpperCase();
		referencia = referencia.toUpperCase();

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

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
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

	public Integer getQuantidadeEstoque() {
		return quantidadeEstoque;
	}

	public void setQuantidadeEstoque(Integer quantidadeEstoque) {
		this.quantidadeEstoque = quantidadeEstoque;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public Integer getQuantidadeEstoqueMinimo() {
		return quantidadeEstoqueMinimo;
	}

	public void setQuantidadeEstoqueMinimo(Integer quantidadeEstoqueMinimo) {
		this.quantidadeEstoqueMinimo = quantidadeEstoqueMinimo;
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

	public Double getQuantidadeQuilo() {
		return quantidadeQuilo;
	}

	public void setQuantidadeQuilo(Double quantidadeQuilo) {
		this.quantidadeQuilo = quantidadeQuilo;
	}
	
	

	public Double getQuantidadePalet() {
		return quantidadePalet;
	}

	public void setQuantidadePalet(Double quantidadePalet) {
		this.quantidadePalet = quantidadePalet;
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

	public Marca getMarca() {
		return marca;
	}

	public void setMarca(Marca marca) {
		this.marca = marca;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	

	public String getReferenciaFornecedor() {
		return referenciaFornecedor;
	}

	public void setReferenciaFornecedor(String referenciaFornecedor) {
		this.referenciaFornecedor = referenciaFornecedor;
	}

	public String getFotoOuMock() {
		return !StringUtils.isEmpty(foto) ? foto : "login.png";
	}

	public boolean temFoto() {
		return !StringUtils.isEmpty(this.foto);
	}

	public boolean isNovo() {
		return codigo == null;
	}

	public boolean isNovaFoto() {
		return novaFoto;
	}

	public void setNovaFoto(boolean novaFoto) {
		this.novaFoto = novaFoto;
	}

	public Unidade getUnidade() {
		return unidade;
	}

	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}

	public LocalDateTime getDataUltimaSaida() {
		return dataUltimaSaida;
	}

	public void setDataUltimaSaida(LocalDateTime dataUltimaSaida) {
		this.dataUltimaSaida = dataUltimaSaida;
	}

	public LocalDateTime getDataUltimaEntrada() {
		return dataUltimaEntrada;
	}

	public void setDataUltimaEntrada(LocalDateTime dataUltimaEntrada) {
		this.dataUltimaEntrada = dataUltimaEntrada;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
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
		Produto other = (Produto) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
//js-img-loading  #subgrupoProdutos
}
