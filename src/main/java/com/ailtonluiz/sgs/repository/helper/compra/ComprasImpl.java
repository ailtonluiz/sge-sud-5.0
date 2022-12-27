package com.ailtonluiz.sgs.repository.helper.compra;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Year;
import java.util.Optional;

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

import com.ailtonluiz.sgs.model.Compra;
import com.ailtonluiz.sgs.model.StatusCompra;
import com.ailtonluiz.sgs.repository.filter.CompraFilter;
import com.ailtonluiz.sgs.repository.paginacao.PaginacaoUtil;

public class ComprasImpl implements ComprasQueries {

	@PersistenceContext
	private EntityManager manager;

	@Autowired
	private PaginacaoUtil paginacaoUtil;

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public Page<Compra> filtrar(CompraFilter filtro, Pageable pageable) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Compra.class);
		paginacaoUtil.preparar(criteria, pageable);
		adicionarFiltro(filtro, criteria);

		return new PageImpl<>(criteria.list(), pageable, total(filtro));
	}

	private Long total(CompraFilter filtro) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Compra.class);
		adicionarFiltro(filtro, criteria);
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();
	}

	@Override
	@Transactional(readOnly = true)
	public Compra buscarComItens(Long codigo) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Compra.class);
		criteria.createAlias("itens", "i", JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("codigo", codigo));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return (Compra) criteria.uniqueResult();
	}

	@Override
	public BigDecimal valorTotalEntregue() {
		Optional<BigDecimal> optional = Optional.ofNullable(manager
				.createQuery("select sum(valorTotal) from Compra where year(dataEntrada) = :ano and status = :status",
						BigDecimal.class)
				.setParameter("ano", Year.now().getValue()).setParameter("status", StatusCompra.ENTREGUE)
				.getSingleResult());
		return optional.orElse(BigDecimal.ZERO);
	}

	@Override
	public BigDecimal valorTotalPendente() {
		Optional<BigDecimal> optional = Optional.ofNullable(manager
				.createQuery("select sum(valorTotal) from Compra where year(dataEntrada) = :ano and status = :status",
						BigDecimal.class)
				.setParameter("ano", Year.now().getValue()).setParameter("status", StatusCompra.PENDENTE)
				.getSingleResult());
		return optional.orElse(BigDecimal.ZERO);
	}

	@Override
	public BigDecimal valorTotalOrcamento() {
		Optional<BigDecimal> optional = Optional.ofNullable(manager
				.createQuery("select sum(valorTotal) from Compra where year(dataEntrada) = :ano and status = :status",
						BigDecimal.class)
				.setParameter("ano", Year.now().getValue()).setParameter("status", StatusCompra.ORCAMENTO)
				.getSingleResult());
		return optional.orElse(BigDecimal.ZERO);
	}
	
	
	
	@Override
	public Long quantidadePedidosCompraPendentes() {
		Optional<Long> optional = Optional.ofNullable(
				manager.createQuery("select count(*) from Compra where  status = :status", Long.class)
				.setParameter("status", StatusCompra.ORCAMENTO)
				.getSingleResult());
		return optional.orElse(Long.sum(0, 0));
	}

	private void adicionarFiltro(CompraFilter filtro, Criteria criteria) {
		criteria.createAlias("fornecedor", "c");

		if (filtro != null) {
			if (!StringUtils.isEmpty(filtro.getCodigo())) {
				criteria.add(Restrictions.eq("codigo", filtro.getCodigo()));
			}

			if (filtro.getStatus() != null) {
				criteria.add(Restrictions.eq("status", filtro.getStatus()));
			}

			if (filtro.getDesde() != null) {
				LocalDateTime desde = LocalDateTime.of(filtro.getDesde(), LocalTime.of(0, 0));
				criteria.add(Restrictions.ge("dataCriacao", desde));
			}

			if (filtro.getAte() != null) {
				LocalDateTime ate = LocalDateTime.of(filtro.getAte(), LocalTime.of(23, 59));
				criteria.add(Restrictions.le("dataCriacao", ate));
			}

			if (filtro.getValorMinimo() != null) {
				criteria.add(Restrictions.ge("valorTotal", filtro.getValorMinimo()));
			}

			if (filtro.getValorMaximo() != null) {
				criteria.add(Restrictions.le("valorTotal", filtro.getValorMaximo()));
			}

			if (!StringUtils.isEmpty(filtro.getNomeFornecedor())) {
				criteria.add(Restrictions.ilike("c.nome", filtro.getNomeFornecedor(), MatchMode.ANYWHERE));
			}

			if (filtro.getNrAlbara() != null) {
				criteria.add(Restrictions.ilike("nrAlbara", filtro.getNrAlbara(), MatchMode.ANYWHERE));
			}
			criteria.addOrder(Order.desc("codigo"));

		}
	}

	

}
