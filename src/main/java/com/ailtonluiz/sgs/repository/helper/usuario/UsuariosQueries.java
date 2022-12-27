package com.ailtonluiz.sgs.repository.helper.usuario;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ailtonluiz.sgs.model.Usuario;
import com.ailtonluiz.sgs.repository.filter.UsuarioFilter;

public interface UsuariosQueries {

	public Optional<Usuario> porEmailEAtivo(String email);

	public List<String> permissoes(Usuario usuario);

	public Page<Usuario> filtrar(UsuarioFilter filtro, Pageable pageable);

	public Usuario buscarComGruposUsuarios(Long codigo);
}
