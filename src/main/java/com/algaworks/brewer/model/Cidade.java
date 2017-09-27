package com.algaworks.brewer.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "cidade")
public class Cidade implements Serializable {

    private static final long serialVersionUID = -7508380967515385844L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "CIDADE_SEQ")
    @SequenceGenerator(name = "CIDADE_SEQ", sequenceName = "cidade_codigo_seq", allocationSize = 1)
    private Long codigo;

    @NotEmpty(message = "O nome é obrigatório.")
    private String nome;

    @NotNull(message = "O estado é obrigatório.")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "codigo_estado")
    @JsonIgnore
    private Estado estado;

    public boolean temEstado() {
        return (this.estado != null) && !StringUtils.isEmpty(this.nome);
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
