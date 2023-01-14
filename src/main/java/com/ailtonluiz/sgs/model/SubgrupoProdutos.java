package com.ailtonluiz.sgs.model;

import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "subgrupo_produtos")
public class SubgrupoProdutos {

	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;

	@NotBlank(message = "Introduzca el nombre del subgrupo de productos")
	private String nome;

	
	@NotNull(message = "El Grupo es obligatório")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "codigo_grupo_produto")
	@JsonIgnore
	private GrupoProdutos grupoProdutos;
	
	

	@OneToMany(mappedBy = "subgrupoProdutos")
	@JsonIgnore
	private List<Produto> produtos;


	private boolean ativo = true;
	
	@PrePersist
	@PreUpdate
	public void prePersistUpdate() {
		
		nome = nome.toUpperCase();
		
	}

	
	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public GrupoProdutos getGrupoProdutos() {
		return grupoProdutos;
	}

	public void setGrupoProdutos(GrupoProdutos grupoProdutos) {
		this.grupoProdutos = grupoProdutos;
	}
	
	

	public List<Produto> getProdutos() {
		return produtos;
	}


	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}


	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public boolean temGrupoProdutos() {
		return grupoProdutos != null;
	
	}
	
	public boolean isNovo() {
		return codigo == null;
	}

	public String getNomeSubgrupoProdutosGrupo() {
		if(this.grupoProdutos != null ) {
			return this.grupoProdutos.getNome();
		}
		return null;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(codigo, grupoProdutos, nome);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SubgrupoProdutos other = (SubgrupoProdutos) obj;
		return Objects.equals(codigo, other.codigo) && Objects.equals(grupoProdutos, other.grupoProdutos)
				&& Objects.equals(nome, other.nome);
	}
	
	

	
	

}
