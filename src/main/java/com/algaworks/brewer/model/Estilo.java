package com.algaworks.brewer.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "estilo")
public class Estilo implements Serializable {

    private static final long serialVersionUID = 8041181155976949465L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "ESTILO_SEQ")
    @SequenceGenerator(name = "ESTILO_SEQ", sequenceName = "estilo_codigo_seq", allocationSize = 1)
    private Long codigo;

    @Size(max = 15, message = "Nome deve ter no máximo 15 caracteres.")
    @NotBlank(message = "Nome é obrigatório.")
    private String nome;

    @OneToMany(mappedBy = "estilo")
    private List<Cerveja> cervejas;

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
     * @return the cervejas
     */
    public List<Cerveja> getCervejas() {
        return this.cervejas;
    }

    /**
     * @param cervejas the cervejas to set
     */
    public void setCervejas(final List<Cerveja> cervejas) {
        this.cervejas = cervejas;
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
        if (!(obj instanceof Estilo)) {
            return false;
        }
        final Estilo other = (Estilo) obj;
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
