package com.ailtonluiz.sgs.repository.helper.compra;

import java.math.BigDecimal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ailtonluiz.sgs.model.Compra;
import com.ailtonluiz.sgs.repository.filter.CompraFilter;

public interface ComprasQueries {

	public Page<Compra> filtrar(CompraFilter filtro, Pageable pageable);

	public Compra buscarComItens(Long codigo);

	public BigDecimal valorTotalEntregue();

	public BigDecimal valorTotalPendente();

	public BigDecimal valorTotalOrcamento();

	public Long quantidadePedidosCompraPendentes();
}
