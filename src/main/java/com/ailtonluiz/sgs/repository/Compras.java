package com.ailtonluiz.sgs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ailtonluiz.sgs.model.Compra;
import com.ailtonluiz.sgs.repository.helper.compra.ComprasQueries;

public interface Compras extends JpaRepository<Compra, Long>, ComprasQueries {

	
}
