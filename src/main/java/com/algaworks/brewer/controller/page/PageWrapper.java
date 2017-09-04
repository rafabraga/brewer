package com.algaworks.brewer.controller.page;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

public class PageWrapper<T> {

    private final Page<T> page;
    private final UriComponentsBuilder uriBuilder;

    public PageWrapper(final Page<T> page, final HttpServletRequest httpServletRequest) {
        this.page = page;
        this.uriBuilder = ServletUriComponentsBuilder.fromRequest(httpServletRequest);
    }

    public List<T> getConteudo() {
        return this.page.getContent();
    }

    public boolean isVazia() {
        return this.page.getContent().isEmpty();
    }

    public int getAtual() {
        return this.page.getNumber();
    }

    public boolean isPrimeira() {
        return this.page.isFirst();
    }

    public boolean isUltima() {
        return this.page.isLast();
    }

    public int getTotal() {
        return this.page.getTotalPages();
    }

    public String urlParaPagina(final int pagina) {
        return this.uriBuilder.replaceQueryParam("page", pagina).build(true).encode().toUriString();
    }

    public String urlOrdenada(final String propriedade) {
        final UriComponentsBuilder uriBuilderOrder = UriComponentsBuilder.fromUriString(this.uriBuilder.build(true).encode().toUriString());

        final String valorSort = String.format("%s,%s,", propriedade, this.inverterDirecao(propriedade));

        return uriBuilderOrder.replaceQueryParam("sort", valorSort).build(true).encode().toUriString();
    }

    private String inverterDirecao(final String propriedade) {
        String direcao = "asc";

        final Order order = this.page.getSort() != null ? this.page.getSort().getOrderFor(propriedade) : null;
        if (order != null) {
            direcao = Sort.Direction.ASC.equals(order.getDirection()) ? "desc" : "asc";
        }

        return direcao;
    }

    public boolean descendente(final String propriedade) {
        return this.inverterDirecao(propriedade).equals("asc");
    }

    public boolean ordenada(final String propriedade) {
        final Order order = this.page.getSort() != null ? this.page.getSort().getOrderFor(propriedade) : null;

        if (order == null) {
            return false;
        }

        return this.page.getSort().getOrderFor(propriedade) != null ? true : false;
    }

}
