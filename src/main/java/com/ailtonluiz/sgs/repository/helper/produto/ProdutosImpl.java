package com.ailtonluiz.sgs.repository.helper.produto;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.ailtonluiz.sgs.dto.ProdutoDTO;
import com.ailtonluiz.sgs.dto.ValorItensEstoque;
import com.ailtonluiz.sgs.model.Produto;
import com.ailtonluiz.sgs.repository.filter.ProdutoFilter;
import com.ailtonluiz.sgs.repository.paginacao.PaginacaoUtil;

public class ProdutosImpl implements ProdutosQueries {

	@PersistenceContext
	private EntityManager manager;

	@Autowired
	private PaginacaoUtil paginacaoUtil;

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public Page<Produto> filtrar(ProdutoFilter filtro, Pageable pageable) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Produto.class);

		paginacaoUtil.preparar(criteria, pageable);
		adicionarFiltro(filtro, criteria);

		return new PageImpl<>(criteria.list(), pageable, total(filtro));
	}

	@Override
	public ValorItensEstoque valorItensEstoque() {
		String query = "select new com.ailtonluiz.sgs.dto.ValorItensEstoque(sum(custoCompra * quantidadeEstoque), sum(quantidadeEstoque)) from Produto";
		return manager.createQuery(query, ValorItensEstoque.class).getSingleResult();
	}

	private Long total(ProdutoFilter filtro) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Produto.class);
		adicionarFiltro(filtro, criteria);
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();
	}

	private void adicionarFiltro(ProdutoFilter filtro, Criteria criteria) {

		if (filtro != null) {
			if (!StringUtils.isEmpty(filtro.getCodigoBarras())) {
				criteria.add(Restrictions.eq("codigoBarras", filtro.getCodigoBarras()));
			}

			if (!StringUtils.isEmpty(filtro.getNomeProduto())) {
				criteria.add(Restrictions.ilike("nomeProduto", filtro.getNomeProduto(), MatchMode.ANYWHERE));
			}

			if (!StringUtils.isEmpty(filtro.getReferencia())) {
				criteria.add(Restrictions.eq("referencia", filtro.getReferencia()));
			}
			
			if(!StringUtils.isEmpty(filtro.getReferenciaFornecedor())) {
				criteria.add(Restrictions.eq("referenciaFornecedor", filtro.getReferenciaFornecedor()));
			}
			
			if (isGrupoPresente(filtro)) {
				criteria.add(Restrictions.eq("grupoProdutos", filtro.getGrupoProdutos()));
			}

			if (isMarcaPresente(filtro)) {
				criteria.add(Restrictions.eq("marca", filtro.getMarca()));
			}

			if (filtro.getValorDe() != null) {
				criteria.add(Restrictions.ge("custoVenda", filtro.getValorDe()));
			}

			if (filtro.getValorAte() != null) {
				criteria.add(Restrictions.le("custoVenda", filtro.getValorAte()));
			}
			if (filtro.getEstoqueDe() != null) {
				criteria.add(Restrictions.ge("quantidadeEstoque", filtro.getEstoqueDe()));
			}
			if (filtro.getEstoqueAte() != null) {
				criteria.add(Restrictions.le("quantidadeEstoque", filtro.getEstoqueAte()));
			}
			criteria.add(Restrictions.eq("ativo", filtro.isAtivo()));
			criteria.addOrder(Order.asc("nomeProduto"));

		}

	}

	private boolean isGrupoPresente(ProdutoFilter filtro) {
		return filtro.getSubgrupoProdutos() != null && filtro.getSubgrupoProdutos().getCodigo() != null;
	}

	private boolean isMarcaPresente(ProdutoFilter filtro) {
		return filtro.getMarca() != null && filtro.getMarca().getCodigo() != null;
	}

	@Override
	public List<ProdutoDTO> porCodigoBarrasOuNomeOuReferencia(String codigoBarrasOuNomeOuReferencia) {
		String jpql = "select new com.ailtonluiz.sgs.dto.ProdutoDTO(p.codigo, p.codigoBarras, p.nomeProduto, p.custoVenda, p.custoCompra, p.foto, p.referencia, m.nome, p.quantidadeEstoque, p.quantidadeEstoquePendente, p.quantidadeCaixa, p.ativo) "
				+ "from Produto p inner join p.marca m where p.ativo =  TRUE "
				+ "and lower(p.nomeProduto) like lower(:codigoBarrasOuNomeOuReferencia) "
				// + "and p.quantidadeEstoque > 0 "
				+ "or lower(p.codigoBarras) like lower(:codigoBarrasOuNomeOuReferencia) "
				+ "or lower(p.referencia) like lower(:codigoBarrasOuNomeOuReferencia)";
		List<ProdutoDTO> produtosFiltrados = manager.createQuery(jpql, ProdutoDTO.class)
				.setParameter("codigoBarrasOuNomeOuReferencia", "%" + codigoBarrasOuNomeOuReferencia + "%")
				.getResultList();
		return produtosFiltrados;

	}

	@Override
	public List<ProdutoDTO> porCodigoBarrasOuNomeOuReferenciaCompra(String codigoBarrasOuNomeOuReferenciaCompra) {
		String jpql = "select new com.ailtonluiz.sgs.dto.ProdutoDTO(p.codigo, p.codigoBarras, p.nomeProduto, p.custoVenda, p.custoCompra, p.foto, p.referencia, m.nome, p.quantidadeEstoque, p.quantidadeEstoquePendente, p.quantidadeCaixa, p.ativo) "
				+ "from Produto p inner join p.marca m where p.ativo =  TRUE "
				+ "and lower(p.nomeProduto) like lower(:codigoBarrasOuNomeOuReferenciaCompra) "
				+ "or lower(p.codigoBarras) like lower(:codigoBarrasOuNomeOuReferenciaCompra) "
				+ "or lower(p.referencia) like lower(:codigoBarrasOuNomeOuReferenciaCompra)";
		List<ProdutoDTO> produtosFiltradosCompra = manager.createQuery(jpql, ProdutoDTO.class)
				.setParameter("codigoBarrasOuNomeOuReferenciaCompra", "%" + codigoBarrasOuNomeOuReferenciaCompra + "%")
				.getResultList();
		return produtosFiltradosCompra;
	}

	@Transactional(readOnly = true)
	@Override
	public Produto buscarComSubgrupoProdutosGrupoProdutos(Long codigo) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Produto.class);
		criteria.createAlias("subgrupoProdutos", "sgp", JoinType.LEFT_OUTER_JOIN);
		criteria.createAlias("sgp.grupoProdutos", "gp", JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("codigo", codigo));
		
		return (Produto) criteria.uniqueResult();
	}
}
