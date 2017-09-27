package com.algaworks.brewer.repository.filter;

import java.io.Serializable;

public class CidadeFilter implements Serializable {

    private static final long serialVersionUID = 5884919692165271701L;

    private Long codigoEstado;
    private String nomeCidade;

    /**
     * @return the codigoEstado
     */
    public Long getCodigoEstado() {
        return this.codigoEstado;
    }

    /**
     * @param codigoEstado the codigoEstado to set
     */
    public void setCodigoEstado(final Long codigoEstado) {
        this.codigoEstado = codigoEstado;
    }

    /**
     * @return the nomeCidade
     */
    public String getNomeCidade() {
        return this.nomeCidade;
    }

    /**
     * @param nomeCidade the nomeCidade to set
     */
    public void setNomeCidade(final String nomeCidade) {
        this.nomeCidade = nomeCidade;
    }

}
