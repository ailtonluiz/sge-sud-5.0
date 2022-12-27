package com.ailtonluiz.sgs.repository.helper.parametro;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.ailtonluiz.sgs.model.Parametro;
import com.ailtonluiz.sgs.repository.filter.ParametroFilter;
import com.ailtonluiz.sgs.repository.paginacao.PaginacaoUtil;

public class ParametrosImpl implements ParametrosQueries {

	@PersistenceContext
	private EntityManager manager;

	@Autowired
	private PaginacaoUtil paginacaoUtil;

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public Page<Parametro> filtrar(ParametroFilter filtro, Pageable pageable) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Parametro.class);

		paginacaoUtil.preparar(criteria, pageable);
		adicionarFiltro(filtro, criteria);

		return new PageImpl<>(criteria.list(), pageable, total(filtro));
	}

	private Long total(ParametroFilter filtro) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Parametro.class);
		adicionarFiltro(filtro, criteria);
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();

	}

	private void adicionarFiltro(ParametroFilter filtro, Criteria criteria) {
		if (filtro != null) {
			if (!StringUtils.isEmpty(filtro.getNomeEmpresa())) {
				criteria.add(Restrictions.ilike("nomeEmpresa", filtro.getNomeEmpresa(), MatchMode.ANYWHERE));
			}

			if (!StringUtils.isEmpty(filtro.getEmail())) {
				criteria.add(Restrictions.ilike("email", filtro.getEmail(), MatchMode.ANYWHERE));
			}
			
			if (!StringUtils.isEmpty(filtro.getTelefone())) {
				criteria.add(Restrictions.ilike("telefone", filtro.getTelefone(), MatchMode.ANYWHERE));
			}
			
			if (!StringUtils.isEmpty(filtro.getResponsavel())) {
				criteria.add(Restrictions.ilike("responsavel", filtro.getResponsavel(), MatchMode.ANYWHERE));
			}
			criteria.add(Restrictions.eq("ativo", filtro.isAtivo()));
		}
	}

}