package com.algaworks.brewer.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import org.springframework.util.StringUtils;

import com.algaworks.brewer.model.Origem;

public class CervejaDTO implements Serializable {

    private static final long serialVersionUID = -6906131360148537370L;

    private Long codigo;
    private String sku;
    private String nome;
    private BigDecimal valor;
    private String origem;
    private String foto;

    /**
     * @param codigo
     * @param sku
     * @param nome
     * @param valor
     * @param origem
     * @param foto
     */
    public CervejaDTO(final Long codigo, final String sku, final String nome, final BigDecimal valor, final Origem origem,
            final String foto) {
        this.codigo = codigo;
        this.sku = sku;
        this.nome = nome;
        this.valor = valor;
        this.origem = origem.getDescricao();
        this.foto = StringUtils.isEmpty(foto) ? "cerveja-mock.png" : foto;
    }

    /**
     * @return the codigo
     */
    public Long getCodigo() {
        return this.codigo;
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(final Long codigo) {
        this.codigo = codigo;
    }

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
     * @return the valor
     */
    public BigDecimal getValor() {
        return this.valor;
    }

    /**
     * @param valor the valor to set
     */
    public void setValor(final BigDecimal valor) {
        this.valor = valor;
    }

    /**
     * @return the origem
     */
    public String getOrigem() {
        return this.origem;
    }

    /**
     * @param origem the origem to set
     */
    public void setOrigem(final String origem) {
        this.origem = origem;
    }

    /**
     * @return the foto
     */
    public String getFoto() {
        return this.foto;
    }

    /**
     * @param foto the foto to set
     */
    public void setFoto(final String foto) {
        this.foto = foto;
    }

}
