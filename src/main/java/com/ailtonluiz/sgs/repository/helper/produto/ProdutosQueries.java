package com.ailtonluiz.sgs.repository.helper.produto;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ailtonluiz.sgs.dto.ProdutoDTO;
import com.ailtonluiz.sgs.dto.ValorItensEstoque;
import com.ailtonluiz.sgs.model.Produto;
import com.ailtonluiz.sgs.repository.filter.ProdutoFilter;

public interface ProdutosQueries {

	public Page<Produto> filtrar(ProdutoFilter filtro, Pageable pageable);

	public List<ProdutoDTO> porCodigoBarrasOuNomeOuReferencia(String codigoBarrasOuNomeOuReferencia);
	
	public List<ProdutoDTO> porCodigoBarrasOuNomeOuReferenciaCompra(String codigoBarrasOuNomeOuReferenciaCompra);

	public ValorItensEstoque valorItensEstoque();

}
