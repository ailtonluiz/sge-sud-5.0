package com.ailtonluiz.sgs.controller.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import com.ailtonluiz.sgs.model.GrupoUsuario;

public class GrupoUsuarioConverter implements Converter<String, GrupoUsuario> {

	@Override
	public GrupoUsuario convert(String codigo) {
		if (!StringUtils.isEmpty(codigo)) {
			GrupoUsuario grupoUsuario = new GrupoUsuario();
			grupoUsuario.setCodigo(Long.valueOf(codigo));
			return grupoUsuario;
		}
		return null;
	}

}
