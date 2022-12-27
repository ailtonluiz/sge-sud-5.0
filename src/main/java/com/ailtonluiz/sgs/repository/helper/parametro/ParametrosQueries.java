package com.ailtonluiz.sgs.repository.helper.parametro;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ailtonluiz.sgs.model.Parametro;
import com.ailtonluiz.sgs.repository.filter.ParametroFilter;

public interface ParametrosQueries {

	public Page<Parametro> filtrar(ParametroFilter filtro, Pageable pageable);
}
