package com.algaworks.brewer.dto;

import java.math.BigDecimal;

public class ValorItensEstoque {

    private BigDecimal valor;
    private Long totalItens;

    public ValorItensEstoque() {

    }

    public ValorItensEstoque(final BigDecimal valor, final Long totalItens) {
        this.valor = valor;
        this.totalItens = totalItens;
    }

    public BigDecimal getValor() {
        return this.valor != null ? this.valor : BigDecimal.ZERO;
    }

    public void setValor(final BigDecimal valor) {
        this.valor = valor;
    }

    public Long getTotalItens() {
        return this.totalItens != null ? this.totalItens : 0L;
    }

    public void setTotalItens(final Long totalItens) {
        this.totalItens = totalItens;
    }

}