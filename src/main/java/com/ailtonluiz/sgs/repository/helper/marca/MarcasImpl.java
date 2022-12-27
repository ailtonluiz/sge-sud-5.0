package com.ailtonluiz.sgs.repository.helper.marca;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.ailtonluiz.sgs.model.Marca;
import com.ailtonluiz.sgs.repository.filter.MarcaFilter;
import com.ailtonluiz.sgs.repository.paginacao.PaginacaoUtil;

public class MarcasImpl implements MarcasQueries {

	@PersistenceContext
	private EntityManager manager;

	@Autowired
	private PaginacaoUtil paginacaoUtil;

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public Page<Marca> filtrar(MarcaFilter filtro, Pageable pageable) {

		Criteria criteria = manager.unwrap(Session.class).createCriteria(Marca.class);

		paginacaoUtil.preparar(criteria, pageable);

		adicionarFiltro(filtro, criteria);

		return new PageImpl<>(criteria.list(), pageable, total(filtro));
	}

	private Long total(MarcaFilter filtro) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Marca.class);
		adicionarFiltro(filtro, criteria);
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();

	}

	private void adicionarFiltro(MarcaFilter filtro, Criteria criteria) {
		if (filtro != null) {
			if (!StringUtils.isEmpty(filtro.getNome())) {
				criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
			

			}
			criteria.add(Restrictions.eq("ativo", filtro.isAtivo()));
			criteria.addOrder(Order.asc("nome"));
		}
	}

}
