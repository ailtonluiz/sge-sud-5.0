package com.ailtonluiz.sgs.repository.helper.grupo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ailtonluiz.sgs.model.GrupoProdutos;
import com.ailtonluiz.sgs.repository.filter.GrupoProdutosFilter;

public interface GruposProdutosQueries {

	public Page<GrupoProdutos> filtrar(GrupoProdutosFilter filtro, Pageable pageable);
}
