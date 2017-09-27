package com.algaworks.brewer.repository.helper.cidade;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.algaworks.brewer.model.Cidade;
import com.algaworks.brewer.repository.filter.CidadeFilter;
import com.algaworks.brewer.repository.pagination.PaginationBuilder;

public class CidadesImpl implements CidadesQueries {

    @PersistenceContext
    private EntityManager manager;

    @Transactional(readOnly = true)
    @SuppressWarnings({ "deprecation", "unchecked" })
    @Override
    public Page<Cidade> filtrar(final CidadeFilter filtro, final Pageable pageable) {
        Criteria criteria = this.manager.unwrap(Session.class).createCriteria(Cidade.class);

        criteria = new PaginationBuilder(criteria, pageable).withOrdination().builder();
        criteria.createAlias("estado", "estado", JoinType.LEFT_OUTER_JOIN);

        this.adicionarFiltro(filtro, criteria);
        return new PageImpl<>(criteria.list(), pageable, this.total(filtro));
    }

    private void adicionarFiltro(final CidadeFilter filtro, final Criteria criteria) {
        if (filtro != null) {
            if (!StringUtils.isEmpty(filtro.getNomeCidade())) {
                criteria.add(Restrictions.ilike("nome", filtro.getNomeCidade(), MatchMode.ANYWHERE));
            }
            if (filtro.getCodigoEstado() != null) {
                criteria.add(Restrictions.eq("estado.codigo", filtro.getCodigoEstado()));
            }
        }
    }

    @SuppressWarnings("deprecation")
    private Long total(final CidadeFilter filtro) {
        final Criteria criteria = this.manager.unwrap(Session.class).createCriteria(Cidade.class);
        this.adicionarFiltro(filtro, criteria);
        criteria.setProjection(Projections.rowCount());
        return (Long) criteria.uniqueResult();
    }

}
