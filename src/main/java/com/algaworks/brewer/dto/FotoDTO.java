package com.algaworks.brewer.dto;

import java.io.Serializable;

public class FotoDTO implements Serializable {

    private static final long serialVersionUID = -2198168740195531211L;

    private String nome;
    private String contentType;
    private String url;

    public FotoDTO(final String nome, final String contentType, final String url) {
        this.nome = nome;
        this.contentType = contentType;
        this.url = url;
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

    /**
     * @return the url
     */
    public String getUrl() {
        return this.url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(final String url) {
        this.url = url;
    }

}
