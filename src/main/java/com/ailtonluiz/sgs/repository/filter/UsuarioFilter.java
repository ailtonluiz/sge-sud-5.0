package com.ailtonluiz.sgs.repository.filter;

import java.util.List;

import com.ailtonluiz.sgs.model.GrupoUsuario;

public class UsuarioFilter {

	private String nome;
	private String email;
	private String telefone;
	private boolean ativo = true;

	private List<GrupoUsuario> gruposUsuarios;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public List<GrupoUsuario> getGruposUsuarios() {
		return gruposUsuarios;
	}

	public void setGruposUsuarios(List<GrupoUsuario> gruposUsuarios) {
		this.gruposUsuarios = gruposUsuarios;
	}

}
