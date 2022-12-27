package com.ailtonluiz.sgs.controller.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import com.ailtonluiz.sgs.model.GrupoProdutos;

public class GrupoProdutosConverter implements Converter<String, GrupoProdutos> {

	@Override
	public GrupoProdutos convert(String codigo) {
		if (!StringUtils.isEmpty(codigo)) {
			GrupoProdutos grupoProdutos = new GrupoProdutos();
			grupoProdutos.setCodigo(Long.valueOf(codigo));
			return grupoProdutos;
		}
		
		return null;
	}

}
