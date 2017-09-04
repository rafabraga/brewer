package com.algaworks.brewer.repository.filter;

import java.io.Serializable;

public class EstiloFilter implements Serializable {

    private static final long serialVersionUID = -931385461653057474L;

    private String nome;

    /**
     * @return the nome
     */
    public String getNome() {
        return this.nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(final String nome) {
        this.nome = nome;
    }

}
