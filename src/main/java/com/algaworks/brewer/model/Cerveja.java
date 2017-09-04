package com.algaworks.brewer.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.util.StringUtils;

import com.algaworks.brewer.validation.SKU;

@Entity
@Table(name = "cerveja")
public class Cerveja implements Serializable {

    private static final long serialVersionUID = 5861755523212177674L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "CERVEJA_SEQ")
    @SequenceGenerator(name = "CERVEJA_SEQ", sequenceName = "cerveja_codigo_seq", allocationSize = 1)
    private Long codigo;

    @SKU
    @NotBlank(message = "SKU é obrigatório.")
    private String sku;

    @NotBlank(message = "Nome é obrigatório.")
    private String nome;

    @Size(min = 1, max = 50, message = "O tamanho da descrição deve estar entre 1 e 50.")
    private String descricao;

    @NotNull(message = "Valor é obrigatório.")
    @DecimalMin("0.01")
    @DecimalMax(value = "9999999.99", message = "O valor da cerveja deve ser menor que R$ 9.999.999,99.")
    private BigDecimal valor;

    @NotNull(message = "Teor alcoólico é obrigatório.")
    @DecimalMax(value = "100.00", message = "O valor do teor alcoólico deve ser no máximo 100.")
    @Column(name = "teor_alcoolico")
    private BigDecimal teorAlcoolico;

    @NotNull(message = "A comissão é obrigatória.")
    @DecimalMax(value = "100.00", message = "A comissão deve ser no máximo 100.")
    private BigDecimal comissao;

    @NotNull(message = "A quantidade em estoque é obrigatória.")
    @Max(value = 9999, message = "A quantidade em estoque deve ser no máximo 9999.")
    @Column(name = "quantidade_estoque")
    private Integer quantidadeEstoque;

    @NotNull(message = "A origem é obrigatória.")
    @Enumerated(EnumType.STRING)
    private Origem origem;

    @NotNull(message = "O sabor é obrigatório.")
    @Enumerated(EnumType.STRING)
    private Sabor sabor;

    @NotNull(message = "Estilo é obrigatório.")
    @ManyToOne
    @JoinColumn(name = "codigo_estilo")
    private Estilo estilo;

    private String foto;

    @Column(name = "content_type")
    private String contentType;

    /* Funções de callback do JPA. */
    @PrePersist
    @PreUpdate
    private void prePersistUpdate() {
        this.sku = this.sku.toUpperCase();
    }

    public String getFotoOuMock() {
        return !StringUtils.isEmpty(this.foto) ? this.foto : "cerveja-mock.png";
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
     * @return the descricao
     */
    public String getDescricao() {
        return this.descricao;
    }

    /**
     * @param descricao the descricao to set
     */
    public void setDescricao(final String descricao) {
        this.descricao = descricao;
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
     * @return the teorAlcoolico
     */
    public BigDecimal getTeorAlcoolico() {
        return this.teorAlcoolico;
    }

    /**
     * @param teorAlcoolico the teorAlcoolico to set
     */
    public void setTeorAlcoolico(final BigDecimal teorAlcoolico) {
        this.teorAlcoolico = teorAlcoolico;
    }

    /**
     * @return the comissao
     */
    public BigDecimal getComissao() {
        return this.comissao;
    }

    /**
     * @param comissao the comissao to set
     */
    public void setComissao(final BigDecimal comissao) {
        this.comissao = comissao;
    }

    /**
     * @return the quantidadeEstoque
     */
    public Integer getQuantidadeEstoque() {
        return this.quantidadeEstoque;
    }

    /**
     * @param quantidadeEstoque the quantidadeEstoque to set
     */
    public void setQuantidadeEstoque(final Integer quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
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

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((this.codigo == null) ? 0 : this.codigo.hashCode());
        return result;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Cerveja)) {
            return false;
        }
        final Cerveja other = (Cerveja) obj;
        if (this.codigo == null) {
            if (other.codigo != null) {
                return false;
            }
        } else if (!this.codigo.equals(other.codigo)) {
            return false;
        }
        return true;
    }

}
