package com.algaworks.brewer.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Embeddable
public class Endereco implements Serializable {

    private static final long serialVersionUID = 1L;

    private String logradouro;

    private String numero;

    private String complemento;

    private String cep;

    @ManyToOne
    @JoinColumn(name = "codigo_cidade")
    private Cidade cidade;

    @Transient
    private Estado estado;

    public String getNomeCidadeSiglaEstado() {
        if (this.getCidade() != null) {
            return this.getCidade().getNome() + "/" + this.getCidade().getEstado().getSigla();
        }
        return null;
    }

    /**
     * @return the logradouro
     */
    public String getLogradouro() {
        return this.logradouro;
    }

    /**
     * @param logradouro the logradouro to set
     */
    public void setLogradouro(final String logradouro) {
        this.logradouro = logradouro;
    }

    /**
     * @return the numero
     */
    public String getNumero() {
        return this.numero;
    }

    /**
     * @param numero the numero to set
     */
    public void setNumero(final String numero) {
        this.numero = numero;
    }

    /**
     * @return the complemento
     */
    public String getComplemento() {
        return this.complemento;
    }

    /**
     * @param complemento the complemento to set
     */
    public void setComplemento(final String complemento) {
        this.complemento = complemento;
    }

    /**
     * @return the cep
     */
    public String getCep() {
        return this.cep;
    }

    /**
     * @param cep the cep to set
     */
    public void setCep(final String cep) {
        this.cep = cep;
    }

    /**
     * @return the cidade
     */
    public Cidade getCidade() {
        return this.cidade;
    }

    /**
     * @param cidade the cidade to set
     */
    public void setCidade(final Cidade cidade) {
        this.cidade = cidade;
    }

    /**
     * @return the estado
     */
    public Estado getEstado() {
        return this.estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(final Estado estado) {
        this.estado = estado;
    }

}