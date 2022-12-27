package com.ailtonluiz.sgs.repository.helper.venda;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ailtonluiz.sgs.dto.VendaMes;
import com.ailtonluiz.sgs.model.Venda;
import com.ailtonluiz.sgs.repository.filter.VendaFilter;

public interface VendasQueries {

	public Page<Venda> filtrar(VendaFilter filtro, Pageable pageable);

	public Venda buscarComItens(Long codigo);
	

	public BigDecimal valorTotalNoAno();
	public BigDecimal valorTotalNoMes();
	public BigDecimal valorTicketMedioNoAno();
	public BigDecimal valorDia();
	public Long quantidadePedidosPendentes();
	public List<VendaMes> totalPorMes();
	
}
