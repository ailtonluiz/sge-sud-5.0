package com.ailtonluiz.sgs.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

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
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "venda")
@DynamicUpdate
public class Venda {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;

	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@Column(name = "data_criacao")
	private LocalDateTime dataCriacao;

	@Column(name = "valor_total")
	private BigDecimal valorTotal = BigDecimal.ZERO;;

	@Column(name = "observacao", length = 200)
	private String observacao;

	@ManyToOne
	@JoinColumn(name = "codigo_cliente")
	private Cliente cliente;

	@ManyToOne
	@JoinColumn(name = "codigo_usuario")
	private Usuario usuario;

	@Enumerated(EnumType.STRING)
	private StatusVenda status = StatusVenda.ORCAMENTO;

	@OneToMany(mappedBy = "venda", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ItemVenda> itens = new ArrayList<>();

	@Transient
	private String uuid;
	
	
	@PrePersist
	@PreUpdate
	private void prePersistUpdate() {
		observacao = observacao.toUpperCase();
		

	}


	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public StatusVenda getStatus() {
		return status;
	}

	public void setStatus(StatusVenda status) {
		this.status = status;
	}

	public List<ItemVenda> getItens() {
		return itens;
	}

	public void setItens(List<ItemVenda> itens) {
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

	public void adicionarItens(List<ItemVenda> itens) {
		this.itens = itens;
		this.itens.forEach(i -> i.setVenda(this));

	}

	public void calcularValorTotal() {
		BigDecimal valorTotalItens = getItens().stream().map(ItemVenda::getValorTotal).reduce(BigDecimal::add)
				.orElse(BigDecimal.ZERO);

		this.valorTotal = calcularValorTotal(valorTotalItens);
	}

	public Long getDiasCriacao() {
		LocalDate inicio = dataCriacao != null ? dataCriacao.toLocalDate() : LocalDate.now();
		return ChronoUnit.DAYS.between(inicio, LocalDate.now());
	}

	public boolean isSalvarPermitido() {
		return status.equals(StatusVenda.ORCAMENTO) || status.equals(StatusVenda.EMITIDA)
				|| status.equals(StatusVenda.BAIXA);
	}

	public boolean isSalvarProibido() {
		return !isSalvarPermitido();
	}

	public boolean isCancelado() {
		return status.equals(StatusVenda.CANCELADA);
	}

	public boolean isEmitido() {
		return status.equals(StatusVenda.EMITIDA);
	}
	
	public boolean isOrcamento() {
		return status.equals(StatusVenda.ORCAMENTO);
	}

	private BigDecimal calcularValorTotal(BigDecimal valorTotalItens) {
		BigDecimal valorTotal = valorTotalItens;
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
		Venda other = (Venda) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}

}
