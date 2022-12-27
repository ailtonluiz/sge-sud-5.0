package com.ailtonluiz.sgs.controller.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import com.ailtonluiz.sgs.model.Marca;

public class MarcaConverter implements Converter<String, Marca> {

	@Override
	public Marca convert(String codigo) {
		if (!StringUtils.isEmpty(codigo)) {
			Marca marca = new Marca();
			marca.setCodigo(Long.valueOf(codigo));
			return marca;
		}

		return null;
	}

}
