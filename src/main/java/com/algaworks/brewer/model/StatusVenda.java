package com.algaworks.brewer.model;

public enum StatusVenda {

    ORCAMENTO("Orçamento"), EMITIDA("Emitida"), CANCELADA("Cancelada");

    private String descricao;

    StatusVenda(final String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return this.descricao;
    }

}