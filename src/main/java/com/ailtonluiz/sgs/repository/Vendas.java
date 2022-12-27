package com.ailtonluiz.sgs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ailtonluiz.sgs.model.Venda;
import com.ailtonluiz.sgs.repository.helper.venda.VendasQueries;

public interface Vendas extends JpaRepository<Venda, Long>, VendasQueries {

	



}
