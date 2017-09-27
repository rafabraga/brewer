package com.algaworks.brewer.repository.helper.cliente;

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

import com.algaworks.brewer.model.Cliente;
import com.algaworks.brewer.repository.filter.ClienteFilter;
import com.algaworks.brewer.repository.pagination.PaginationBuilder;

public class ClientesImpl implements ClientesQueries {

    @PersistenceContext
    private EntityManager manager;

    @SuppressWarnings({ "deprecation", "unchecked" })
    @Transactional(readOnly = true)
    @Override
    public Page<Cliente> filtrar(final ClienteFilter filtro, final Pageable pageable) {
        Criteria criteria = this.manager.unwrap(Session.class).createCriteria(Cliente.class);

        criteria = new PaginationBuilder(criteria, pageable).withOrdination().builder();
        criteria.createAlias("endereco.cidade", "cidade", JoinType.LEFT_OUTER_JOIN);
        criteria.createAlias("cidade.estado", "estado", JoinType.LEFT_OUTER_JOIN);

        this.adicionarFiltro(filtro, criteria);
        return new PageImpl<>(criteria.list(), pageable, this.total(filtro));
    }

    private void adicionarFiltro(final ClienteFilter filtro, final Criteria criteria) {
        if (filtro != null) {
            if (!StringUtils.isEmpty(filtro.getNome())) {
                criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
            }
            if (!StringUtils.isEmpty(filtro.getCpfOuCnpj())) {
                criteria.add(Restrictions.ilike("cpfOuCnpj", filtro.getCpfOuCnpj(), MatchMode.ANYWHERE));
            }
        }
    }

    @SuppressWarnings("deprecation")
    private Long total(final ClienteFilter filtro) {
        final Criteria criteria = this.manager.unwrap(Session.class).createCriteria(Cliente.class);
        this.adicionarFiltro(filtro, criteria);
        criteria.setProjection(Projections.rowCount());
        return (Long) criteria.uniqueResult();
    }

}
