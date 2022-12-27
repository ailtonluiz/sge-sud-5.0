package com.ailtonluiz.sgs.repository.helper.subgrupo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.ailtonluiz.sgs.model.SubgrupoProdutos;
import com.ailtonluiz.sgs.repository.filter.SubgrupoProdutosFilter;
import com.ailtonluiz.sgs.repository.paginacao.PaginacaoUtil;

public class SubgruposProdutosImpl implements SubgruposProdutosQueries {
	
	@PersistenceContext
	private EntityManager manager;
	
	
	@Autowired
	private PaginacaoUtil paginacaoUtil;
	
	
	
	

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public Page<SubgrupoProdutos> filtrar(SubgrupoProdutosFilter filtro, Pageable pageable) {
		
		Criteria criteria = manager.unwrap(Session.class).createCriteria(SubgrupoProdutos.class);
		
		paginacaoUtil.preparar(criteria, pageable);
		adicionarFiltro(filtro, criteria);
		criteria.createAlias("grupoProdutos", "gp");
		
		

		return new PageImpl<>(criteria.list(), pageable, total(filtro));
	}
	
	private Long total(SubgrupoProdutosFilter filtro) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(SubgrupoProdutos.class);
		adicionarFiltro(filtro, criteria);
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();
	}


	
	private void adicionarFiltro(SubgrupoProdutosFilter filtro, Criteria criteria) {
		if (filtro != null) {
			if (filtro.getGrupoProdutos() != null) {
				criteria.add(Restrictions.eq("grupoProdutos", filtro.getGrupoProdutos()));
			}

			if (!StringUtils.isEmpty(filtro.getNome())) {
				criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
			}
		}
	}

	
	@Transactional(readOnly = true)
	@Override
	public SubgrupoProdutos buscarComGrupoProdutos(Long codigo) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(SubgrupoProdutos.class);
		criteria.createAlias("grupoProdutos", "gp", JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("codigo", codigo));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		return (SubgrupoProdutos) criteria.uniqueResult();
	}

}
