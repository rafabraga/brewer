package com.algaworks.brewer.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "item_venda")
public class ItemVenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "ITEM_VENDA_SEQ")
    @SequenceGenerator(name = "ITEM_VENDA_SEQ", sequenceName = "item_venda_codigo_seq", allocationSize = 1)
    private Long codigo;

    private Integer quantidade;

    @Column(name = "valor_unitario")
    private BigDecimal valorUnitario;

    @ManyToOne
    @JoinColumn(name = "codigo_cerveja")
    private Cerveja cerveja;

    @ManyToOne
    @JoinColumn(name = "codigo_venda")
    private Venda venda;

    public BigDecimal getValorTotal() {
        return this.valorUnitario.multiply(BigDecimal.valueOf(this.quantidade));
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
     * @return the quantidade
     */
    public Integer getQuantidade() {
        return this.quantidade;
    }

    /**
     * @param quantidade the quantidade to set
     */
    public void setQuantidade(final Integer quantidade) {
        this.quantidade = quantidade;
    }

    /**
     * @return the valorUnitario
     */
    public BigDecimal getValorUnitario() {
        return this.valorUnitario;
    }

    /**
     * @param valorUnitario the valorUnitario to set
     */
    public void setValorUnitario(final BigDecimal valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    /**
     * @return the cerveja
     */
    public Cerveja getCerveja() {
        return this.cerveja;
    }

    /**
     * @param cerveja the cerveja to set
     */
    public void setCerveja(final Cerveja cerveja) {
        this.cerveja = cerveja;
    }

    /**
     * @return the venda
     */
    public Venda getVenda() {
        return this.venda;
    }

    /**
     * @param venda the venda to set
     */
    public void setVenda(final Venda venda) {
        this.venda = venda;
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
        if (!(obj instanceof ItemVenda)) {
            return false;
        }
        final ItemVenda other = (ItemVenda) obj;
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
