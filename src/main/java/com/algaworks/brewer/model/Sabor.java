package com.algaworks.brewer.model;

public enum Sabor {

    ADOCICADA("Adocicada"), AMARGA("Amarga"), FORTE("Forte"), FRUTADA("Frutada"), SUAVE("Suave");

    private String descricao;

    private Sabor(final String descricao) {
        this.descricao = descricao;
    }

    /**
     * @return the descricao
     */
    public String getDescricao() {
        return this.descricao;
    }

}
