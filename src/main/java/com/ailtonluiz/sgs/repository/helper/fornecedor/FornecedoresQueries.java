package com.ailtonluiz.sgs.repository.helper.fornecedor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ailtonluiz.sgs.model.Fornecedor;
import com.ailtonluiz.sgs.repository.filter.FornecedorFilter;

public interface FornecedoresQueries {

	public Page<Fornecedor> filtrar(FornecedorFilter filtro, Pageable pageable);
}
