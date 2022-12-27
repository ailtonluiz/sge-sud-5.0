package com.ailtonluiz.sgs.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "compra")
@DynamicUpdate
public class Compra {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;

	@Column(name = "data_entrada")
	private LocalDateTime dataEntrada;

	@NotNull(message = "Introduzca fecha Albará")
	@Column(name = "data_albara")
	private LocalDate dataAlbara;

	@Column(name = "valor_total")
	private BigDecimal valorTotal = BigDecimal.ZERO;

	@Column(name = "valor_igi")
	private BigDecimal valorIgi;

	@Column(name = "valor_desconto")
	private BigDecimal valorDesconto;

	@NotNull(message = "Introduzca el numero del Albarán")
	@Column(name = "nr_albara")
	private String nrAlbara;

	private String observacao;

	@ManyToOne
	@JoinColumn(name = "codigo_fornecedor")
	private Fornecedor fornecedor;

	@ManyToOne
	@JoinColumn(name = "codigo_usuario")
	private Usuario usuario;

	@Enumerated(EnumType.STRING)
	private StatusCompra status = StatusCompra.ORCAMENTO;

	@OneToMany(mappedBy = "compra", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ItemCompra> itens = new ArrayList<>();

	@Transient
	private String uuid;

	@PreUpdate
	private void prePersistUpdate() {
		nrAlbara = nrAlbara.toUpperCase();
		observacao = observacao.toUpperCase();

	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public LocalDateTime getDataEntrada() {
		return dataEntrada;
	}

	public void setDataEntrada(LocalDateTime dataEntrada) {
		this.dataEntrada = dataEntrada;
	}

	public LocalDate getDataAlbara() {
		return dataAlbara;
	}

	public void setDataAlbara(LocalDate dataAlbara) {
		this.dataAlbara = dataAlbara;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public BigDecimal getValorIgi() {
		return valorIgi;
	}

	public void setValorIgi(BigDecimal valorIgi) {
		this.valorIgi = valorIgi;
	}

	public BigDecimal getValorDesconto() {
		return valorDesconto;
	}

	public void setValorDesconto(BigDecimal valorDesconto) {
		this.valorDesconto = valorDesconto;
	}

	public String getNrAlbara() {
		return nrAlbara;
	}

	public void setNrAlbara(String nrAlbara) {
		this.nrAlbara = nrAlbara;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public StatusCompra getStatus() {
		return status;
	}

	public void setStatus(StatusCompra status) {
		this.status = status;
	}

	public List<ItemCompra> getItens() {
		return itens;
	}

	public void setItens(List<ItemCompra> itens) {
		this.itens = itens;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public boolean isNova() {
		return codigo == null;
	}

	public void adicionarItens(List<ItemCompra> itens) {
		this.itens = itens;
		this.itens.forEach(i -> i.setCompra(this));

	}

	public void calcularValorTotalCompra() {
		BigDecimal valorTotalItens = getItens().stream().map(ItemCompra::getValorTotalCompra).reduce(BigDecimal::add)
				.orElse(BigDecimal.ZERO);

		this.valorTotal = calcularValorTotalCompra(valorTotalItens, getValorIgi(), getValorDesconto());

	}

	public Long getDiasCriacao() {
		LocalDate inicio = dataEntrada != null ? dataEntrada.toLocalDate() : LocalDate.now();
		return ChronoUnit.DAYS.between(inicio, LocalDate.now());
	}

	public boolean isSalvarPermitido() {
		return status.equals(StatusCompra.ORCAMENTO) || status.equals(StatusCompra.PENDENTE)
				|| status.equals(StatusCompra.ENTREGUE);
	}

	public boolean isSalvarProibido() {
		return !isSalvarPermitido();
	}

	public boolean isPendente() {
		return status.equals(StatusCompra.PENDENTE);
	}

	public boolean isCancelado() {
		return status.equals(StatusCompra.CANCELADA);
	}

	private BigDecimal calcularValorTotalCompra(BigDecimal valorTotalItens, BigDecimal valorIgi,
			BigDecimal valorDesconto) {
		BigDecimal valorTotal = valorTotalItens.add(Optional.ofNullable(valorIgi).orElse(BigDecimal.ZERO))
				.subtract(Optional.ofNullable(valorDesconto).orElse(BigDecimal.ZERO));
		return valorTotal;
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
		Compra other = (Compra) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

}
