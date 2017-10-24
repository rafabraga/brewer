package com.algaworks.brewer.repository.helper.venda;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.MonthDay;
import java.time.Year;
import java.util.List;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.algaworks.brewer.dto.VendaMes;
import com.algaworks.brewer.model.StatusVenda;
import com.algaworks.brewer.model.TipoPessoa;
import com.algaworks.brewer.model.Venda;
import com.algaworks.brewer.repository.filter.VendaFilter;
import com.algaworks.brewer.repository.pagination.PaginationBuilder;

public class VendasImpl implements VendasQueries {

    @PersistenceContext
    private EntityManager manager;

    @Override
    @Transactional(readOnly = true)
    @SuppressWarnings({ "deprecation", "unchecked" })
    public Page<Venda> filtrar(final VendaFilter filtro, final Pageable pageable) {
        Criteria criteria = this.manager.unwrap(Session.class).createCriteria(Venda.class);

        criteria = new PaginationBuilder(criteria, pageable).withOrdination().builder();
        criteria.addOrder(Order.asc("codigo"));

        this.adicionarFiltro(filtro, criteria);
        return new PageImpl<>(criteria.list(), pageable, this.total(filtro));
    }

    @Override
    @Transactional(readOnly = true)
    @SuppressWarnings("deprecation")
    public Venda buscarComItens(final Long codigo) {
        final Criteria criteria = this.manager.unwrap(Session.class).createCriteria(Venda.class);
        criteria.createAlias("itens", "i", JoinType.LEFT_OUTER_JOIN);
        criteria.add(Restrictions.idEq(codigo));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return (Venda) criteria.uniqueResult();
    }

    @Override
    public BigDecimal valorTotalNoAno() {
        final Optional<BigDecimal> optional = Optional.ofNullable(this.manager
                .createQuery("select sum(valorTotal) from Venda where year(dataCriacao) = :ano and status = :status", BigDecimal.class)
                .setParameter("ano", Year.now().getValue()).setParameter("status", StatusVenda.EMITIDA).getSingleResult());
        return optional.orElse(BigDecimal.ZERO);
    }

    @Override
    public BigDecimal valorTotalNoMes() {
        final Optional<BigDecimal> optional = Optional.ofNullable(this.manager.createQuery(
                "select sum(valorTotal) from Venda where month(dataCriacao) = :mes and year(dataCriacao) = :ano and status = :status",
                BigDecimal.class).setParameter("mes", MonthDay.now().getMonthValue()).setParameter("ano", Year.now().getValue())
                .setParameter("status", StatusVenda.EMITIDA).getSingleResult());
        return optional.orElse(BigDecimal.ZERO);
    }

    @Override
    public BigDecimal valorTicketMedioNoAno() {
        final Optional<BigDecimal> optional = Optional.ofNullable(this.manager
                .createQuery("select sum(valorTotal)/count(*) from Venda where year(dataCriacao) = :ano and status = :status",
                        BigDecimal.class)
                .setParameter("ano", Year.now().getValue()).setParameter("status", StatusVenda.EMITIDA).getSingleResult());
        return optional.orElse(BigDecimal.ZERO);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<VendaMes> totalPorMes() {
        final List<VendaMes> vendasMes = this.manager.createNamedQuery("Vendas.totalPorMes").getResultList();

        LocalDate hoje = LocalDate.now();
        for (int i = 1; i <= 6; i++) {
            final String mesIdeal = String.format("%d/%02d", hoje.getYear(), hoje.getMonthValue());

            final boolean possuiMes = vendasMes.stream().filter(v -> v.getMes().equals(mesIdeal)).findAny().isPresent();
            if (!possuiMes) {
                vendasMes.add(i - 1, new VendaMes(mesIdeal, 0));
            }

            hoje = hoje.minusMonths(1);
        }

        return vendasMes;
    }

    private void adicionarFiltro(final VendaFilter filtro, final Criteria criteria) {
        criteria.createAlias("cliente", "c");
        if (filtro != null) {
            if (filtro.getCodigo() != null) {
                criteria.add(Restrictions.idEq(filtro.getCodigo()));
            }
            if (filtro.getStatus() != null) {
                criteria.add(Restrictions.eq("status", filtro.getStatus()));
            }
            if (!StringUtils.isEmpty(filtro.getNomeCliente())) {
                criteria.add(Restrictions.ilike("c.nome", filtro.getNomeCliente(), MatchMode.ANYWHERE));
            }
            if (filtro.getDataCriacaoDe() != null) {
                criteria.add(Restrictions.ge("dataCriacao", LocalDateTime.of(filtro.getDataCriacaoDe(), LocalTime.MIDNIGHT)));
            }
            if (filtro.getDataCriacaoAte() != null) {
                criteria.add(Restrictions.le("dataCriacao", LocalDateTime.of(filtro.getDataCriacaoAte(), LocalTime.MAX)));
            }
            if (filtro.getValorTotalDe() != null) {
                criteria.add(Restrictions.ge("valorTotal", filtro.getValorTotalDe()));
            }
            if (filtro.getValorTotalAte() != null) {
                criteria.add(Restrictions.le("valorTotal", filtro.getValorTotalAte()));
            }
            if (!StringUtils.isEmpty(filtro.getCpfOuCnpjCliente())) {
                criteria.add(Restrictions.eq("c.cpfOuCnpj", TipoPessoa.removerFormatacao(filtro.getCpfOuCnpjCliente())));
            }
        }
    }

    @SuppressWarnings("deprecation")
    private Long total(final VendaFilter filtro) {
        final Criteria criteria = this.manager.unwrap(Session.class).createCriteria(Venda.class);
        this.adicionarFiltro(filtro, criteria);
        criteria.setProjection(Projections.rowCount());
        return (Long) criteria.uniqueResult();
    }

}
