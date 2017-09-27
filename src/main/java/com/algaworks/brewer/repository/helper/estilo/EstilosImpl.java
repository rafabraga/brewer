package com.algaworks.brewer.repository.helper.estilo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.algaworks.brewer.model.Estilo;
import com.algaworks.brewer.repository.filter.EstiloFilter;
import com.algaworks.brewer.repository.pagination.PaginationBuilder;

public class EstilosImpl implements EstilosQueries {

    @PersistenceContext
    private EntityManager manager;

    @Transactional(readOnly = true)
    @SuppressWarnings({ "deprecation", "unchecked" })
    @Override
    public Page<Estilo> filtrar(final EstiloFilter filtro, final Pageable pageable) {
        Criteria criteria = this.manager.unwrap(Session.class).createCriteria(Estilo.class);

        criteria = new PaginationBuilder(criteria, pageable).withOrdination().builder();

        this.adicionarFiltro(filtro, criteria);
        return new PageImpl<>(criteria.list(), pageable, this.total(filtro));
    }

    private void adicionarFiltro(final EstiloFilter filtro, final Criteria criteria) {
        if (filtro != null) {
            if (!StringUtils.isEmpty(filtro.getNome())) {
                criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
            }
        }
    }

    @SuppressWarnings("deprecation")
    private Long total(final EstiloFilter filtro) {
        final Criteria criteria = this.manager.unwrap(Session.class).createCriteria(Estilo.class);
        this.adicionarFiltro(filtro, criteria);
        criteria.setProjection(Projections.rowCount());
        return (Long) criteria.uniqueResult();
    }

}
