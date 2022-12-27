package com.ailtonluiz.sgs.controller.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import com.ailtonluiz.sgs.model.SubgrupoProdutos;

public class SubgrupoProdutosConverter implements Converter<String, SubgrupoProdutos> {

	@Override
	public SubgrupoProdutos convert(String codigo) {

		if (!StringUtils.isEmpty(codigo)) {
			SubgrupoProdutos subgrupoProdutos = new SubgrupoProdutos();
			subgrupoProdutos.setCodigo(Long.valueOf(codigo));
			return subgrupoProdutos;
		}

		return null;
	}

}
