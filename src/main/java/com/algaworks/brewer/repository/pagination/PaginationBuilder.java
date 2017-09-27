package com.algaworks.brewer.repository.pagination;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PaginationBuilder {

    private final Criteria criteria;
    private final Pageable pageable;

    public PaginationBuilder(final Criteria criteria, final Pageable pageable) {
        this.criteria = criteria;
        this.pageable = pageable;
        this.setLimit();
    }

    private void setLimit() {
        final int pageNumber = this.pageable.getPageNumber();
        final int maxResult = this.pageable.getPageSize();
        final int firstResult = pageNumber * maxResult;
        this.criteria.setFirstResult(firstResult);
        this.criteria.setMaxResults(maxResult);
    }

    /**
     * Build with ordination.
     *
     * @return {@code Criteria}
     */
    public PaginationBuilder withOrdination() {
        final Sort sort = this.pageable.getSort();
        if (sort != null) {
            final Sort.Order order = sort.iterator().next();
            final String property = order.getProperty();
            this.criteria.addOrder(order.isAscending() ? Order.asc(property) : Order.desc(property));
        }
        return this;
    }

    public Criteria builder() {
        return this.criteria;
    }

}
