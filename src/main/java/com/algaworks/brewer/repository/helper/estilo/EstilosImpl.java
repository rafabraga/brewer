package com.algaworks.brewer.repository.helper.estilo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.algaworks.brewer.model.Estilo;
import com.algaworks.brewer.repository.filter.EstiloFilter;

public class EstilosImpl implements EstilosQueries {

    @PersistenceContext
    private EntityManager manager;

    @Transactional(readOnly = true)
    @SuppressWarnings({ "deprecation", "unchecked" })
    @Override
    public Page<Estilo> filtrar(final EstiloFilter filtro, final Pageable pageable) {
        final Criteria criteria = this.manager.unwrap(Session.class).createCriteria(Estilo.class);

        final int paginaAtual = pageable.getPageNumber();
        final int totalRegistrosPorPagina = pageable.getPageSize();
        final int primeiroRegistro = paginaAtual * totalRegistrosPorPagina;

        criteria.setFirstResult(primeiroRegistro);
        criteria.setMaxResults(totalRegistrosPorPagina);

        final Sort sort = pageable.getSort();
        if (sort != null) {
            final Sort.Order order = sort.iterator().next();
            final String field = order.getProperty();
            criteria.addOrder(order.isAscending() ? Order.asc(field) : Order.desc(field));
        }

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
