package com.ailtonluiz.sgs.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ailtonluiz.sgs.model.GrupoProdutos;
import com.ailtonluiz.sgs.model.SubgrupoProdutos;
import com.ailtonluiz.sgs.repository.helper.subgrupo.SubgruposProdutosQueries;

@Repository
public interface SubgruposProdutos extends JpaRepository<SubgrupoProdutos, Long>, SubgruposProdutosQueries {

	public List<SubgrupoProdutos> findByGrupoProdutosCodigo(Long codigoGrupoProduto);

	public Optional<SubgrupoProdutos> findByNomeAndGrupoProdutos(String nome, GrupoProdutos grupoProdutos);

}
