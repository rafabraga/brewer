package com.algaworks.brewer.repository.filter;

import java.io.Serializable;
import java.math.BigDecimal;

import com.algaworks.brewer.model.Estilo;
import com.algaworks.brewer.model.Origem;
import com.algaworks.brewer.model.Sabor;

public class CervejaFilter implements Serializable {

    private static final long serialVersionUID = -3393852829140363199L;

    private String sku;
    private String nome;
    private BigDecimal valorDe;
    private BigDecimal valorAte;
    private Origem origem;
    private Sabor sabor;
    private Estilo estilo;

    /**
     * @return the sku
     */
    public String getSku() {
        return this.sku;
    }

    /**
     * @param sku the sku to set
     */
    public void setSku(final String sku) {
        this.sku = sku;
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
     * @return the valorDe
     */
    public BigDecimal getValorDe() {
        return this.valorDe;
    }

    /**
     * @param valorDe the valorDe to set
     */
    public void setValorDe(final BigDecimal valorDe) {
        this.valorDe = valorDe;
    }

    /**
     * @return the valorAte
     */
    public BigDecimal getValorAte() {
        return this.valorAte;
    }

    /**
     * @param valorAte the valorAte to set
     */
    public void setValorAte(final BigDecimal valorAte) {
        this.valorAte = valorAte;
    }

    /**
     * @return the origem
     */
    public Origem getOrigem() {
        return this.origem;
    }

    /**
     * @param origem the origem to set
     */
    public void setOrigem(final Origem origem) {
        this.origem = origem;
    }

    /**
     * @return the sabor
     */
    public Sabor getSabor() {
        return this.sabor;
    }

    /**
     * @param sabor the sabor to set
     */
    public void setSabor(final Sabor sabor) {
        this.sabor = sabor;
    }

    /**
     * @return the estilo
     */
    public Estilo getEstilo() {
        return this.estilo;
    }

    /**
     * @param estilo the estilo to set
     */
    public void setEstilo(final Estilo estilo) {
        this.estilo = estilo;
    }

}
