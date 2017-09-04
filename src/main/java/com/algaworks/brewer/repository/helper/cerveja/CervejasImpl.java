package com.algaworks.brewer.repository.helper.cerveja;

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

import com.algaworks.brewer.model.Cerveja;
import com.algaworks.brewer.repository.filter.CervejaFilter;

public class CervejasImpl implements CervejasQueries {

    @PersistenceContext
    private EntityManager manager;

    @Transactional(readOnly = true)
    @SuppressWarnings({ "deprecation", "unchecked" })
    @Override
    public Page<Cerveja> filtrar(final CervejaFilter filtro, final Pageable pageable) {
        final Criteria criteria = this.manager.unwrap(Session.class).createCriteria(Cerveja.class);

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

    private void adicionarFiltro(final CervejaFilter filtro, final Criteria criteria) {
        if (filtro != null) {
            if (!StringUtils.isEmpty(filtro.getSku())) {
                criteria.add(Restrictions.ilike("sku", filtro.getSku()));
            }

            if (!StringUtils.isEmpty(filtro.getNome())) {
                criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
            }

            if (this.isEstiloPresente(filtro)) {
                criteria.add(Restrictions.eq("estilo", filtro.getEstilo()));
            }

            if (filtro.getSabor() != null) {
                criteria.add(Restrictions.eq("sabor", filtro.getSabor()));
            }

            if (filtro.getOrigem() != null) {
                criteria.add(Restrictions.eq("origem", filtro.getOrigem()));
            }

            if (filtro.getValorDe() != null) {
                criteria.add(Restrictions.ge("valor", filtro.getValorDe()));
            }

            if (filtro.getValorAte() != null) {
                criteria.add(Restrictions.le("valor", filtro.getValorAte()));
            }
        }
    }

    @SuppressWarnings("deprecation")
    private Long total(final CervejaFilter filtro) {
        final Criteria criteria = this.manager.unwrap(Session.class).createCriteria(Cerveja.class);
        this.adicionarFiltro(filtro, criteria);
        criteria.setProjection(Projections.rowCount());
        return (Long) criteria.uniqueResult();
    }

    private boolean isEstiloPresente(final CervejaFilter filtro) {
        return (filtro.getEstilo() != null) && (filtro.getEstilo().getCodigo() != null);
    }

}
