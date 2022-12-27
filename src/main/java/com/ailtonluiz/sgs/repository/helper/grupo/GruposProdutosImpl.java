package com.ailtonluiz.sgs.repository.helper.grupo;

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

import com.ailtonluiz.sgs.model.GrupoProdutos;
import com.ailtonluiz.sgs.repository.filter.GrupoProdutosFilter;
import com.ailtonluiz.sgs.repository.paginacao.PaginacaoUtil;

public class GruposProdutosImpl implements GruposProdutosQueries {

	@PersistenceContext
	private EntityManager manager;
	

	@Autowired
	private PaginacaoUtil paginacaoUtil;

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public Page<GrupoProdutos> filtrar(GrupoProdutosFilter filtro, Pageable pageable) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(GrupoProdutos.class);

		paginacaoUtil.preparar(criteria, pageable);
		adicionarFiltro(filtro, criteria);
		
		return new PageImpl<>(criteria.list(), pageable, total(filtro));
	}
	
	private Long total(GrupoProdutosFilter filtro) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(GrupoProdutos.class);
		adicionarFiltro(filtro, criteria);
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();

	}
	
	private void adicionarFiltro(GrupoProdutosFilter filtro, Criteria criteria) {
		if (filtro != null) {
			if (!StringUtils.isEmpty(filtro.getNome())) {
				criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
				
			}
			criteria.add(Restrictions.eq("ativo", filtro.isAtivo()));
		}
	}

}
