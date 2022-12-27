package com.ailtonluiz.sgs.repository.helper.subgrupo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ailtonluiz.sgs.model.SubgrupoProdutos;
import com.ailtonluiz.sgs.repository.filter.SubgrupoProdutosFilter;

public interface SubgruposProdutosQueries {

	public Page<SubgrupoProdutos> filtrar(SubgrupoProdutosFilter filtro, Pageable pageable);
	
	public SubgrupoProdutos buscarComGrupoProdutos(Long codigo);

}
