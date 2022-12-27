package com.ailtonluiz.sgs.repository.helper.marca;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ailtonluiz.sgs.model.Marca;
import com.ailtonluiz.sgs.repository.filter.MarcaFilter;

public interface MarcasQueries {
	
	public Page<Marca> filtrar(MarcaFilter filtro, Pageable pageable);

}
