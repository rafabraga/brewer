package com.algaworks.brewer.dto;

import java.io.Serializable;

public class FotoDTO implements Serializable {

    private static final long serialVersionUID = -2198168740195531211L;

    private String nome;
    private String contentType;

    public FotoDTO(final String nome, final String contentType) {
        this.nome = nome;
        this.contentType = contentType;
    }

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

    /**
     * @return the contentType
     */
    public String getContentType() {
        return this.contentType;
    }

    /**
     * @param contentType the contentType to set
     */
    public void setContentType(final String contentType) {
        this.contentType = contentType;
    }

}
