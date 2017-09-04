package com.algaworks.brewer.model;

public enum Origem {

    NACIONAL("Nacional"), INTERNACIONAL("Internacional");

    private String descricao;

    private Origem(final String descricao) {
        this.descricao = descricao;
    }

    /**
     * @return the descricao
     */
    public String getDescricao() {
        return this.descricao;
    }

}
