package com.algaworks.brewer.repository.helper.cerveja;

import java.util.List;

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

import com.algaworks.brewer.dto.CervejaDTO;
import com.algaworks.brewer.dto.ValorItensEstoque;
import com.algaworks.brewer.model.Cerveja;
import com.algaworks.brewer.repository.filter.CervejaFilter;
import com.algaworks.brewer.repository.pagination.PaginationBuilder;
import com.algaworks.brewer.storage.FotoStorage;

public class CervejasImpl implements CervejasQueries {

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    private FotoStorage fotoStorage;

    @Transactional(readOnly = true)
    @SuppressWarnings({ "deprecation", "unchecked" })
    @Override
    public Page<Cerveja> filtrar(final CervejaFilter filtro, final Pageable pageable) {
        Criteria criteria = this.manager.unwrap(Session.class).createCriteria(Cerveja.class);

        criteria = new PaginationBuilder(criteria, pageable).withOrdination().builder();

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

    @Override
    public List<CervejaDTO> listarPorSkuOuNome(final String skuOuNome) {
        final String jpql = "select new com.algaworks.brewer.dto.CervejaDTO(codigo, sku, nome, valor, origem, foto) "
                + "from Cerveja where lower(sku) like lower(:skuOuNome) or lower(nome) like lower(:skuOuNome)";
        final List<CervejaDTO> cervejasFiltradas = this.manager.createQuery(jpql, CervejaDTO.class)
                .setParameter("skuOuNome", "%" + skuOuNome + "%").getResultList();
        cervejasFiltradas.forEach(c -> c.setUrlThumbnailFoto(this.fotoStorage.getUrl(FotoStorage.THUMBNAIL_PREFIX + c.getFoto())));
        return cervejasFiltradas;
    }

    @Override
    public ValorItensEstoque valorItensEstoque() {
        final String query = "select new com.algaworks.brewer.dto.ValorItensEstoque(sum(valor * quantidadeEstoque), sum(quantidadeEstoque)) from Cerveja";
        return this.manager.createQuery(query, ValorItensEstoque.class).getSingleResult();
    }

}
