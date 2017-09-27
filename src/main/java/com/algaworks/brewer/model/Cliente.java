package com.algaworks.brewer.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;
import org.hibernate.validator.group.GroupSequenceProvider;

import com.algaworks.brewer.model.validation.ClienteGroupSequenceProvider;
import com.algaworks.brewer.model.validation.group.CnpjGroup;
import com.algaworks.brewer.model.validation.group.CpfGroup;

@Entity
@Table(name = "cliente")
@GroupSequenceProvider(ClienteGroupSequenceProvider.class)
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "CLIENTE_SEQ")
    @SequenceGenerator(name = "CLIENTE_SEQ", sequenceName = "cliente_codigo_seq", allocationSize = 1)
    private Long codigo;

    @NotBlank(message = "O nome é obrigatório.")
    private String nome;

    @NotNull(message = "Tipo pessoa é obrigatório.")
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_pessoa")
    private TipoPessoa tipoPessoa;

    @NotBlank(message = "CPF/CNPJ é obrigatório.")
    @CPF(groups = CpfGroup.class)
    @CNPJ(groups = CnpjGroup.class)
    @Column(name = "cpf_cnpj")
    private String cpfOuCnpj;

    private String telefone;

    @Email(message = "O e-mail é inválido.")
    private String email;

    @Embedded
    private Endereco endereco;

    @PrePersist
    @PreUpdate
    private void prePersistPreUpdate() {
        this.cpfOuCnpj = TipoPessoa.removerFormatacao(this.cpfOuCnpj);
    }

    @PostLoad
    private void postLoad() {
        this.cpfOuCnpj = this.tipoPessoa.formatar(this.cpfOuCnpj);
    }

    public Long getCodigo() {
        return this.codigo;
    }

    public void setCodigo(final Long codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(final String nome) {
        this.nome = nome;
    }

    public TipoPessoa getTipoPessoa() {
        return this.tipoPessoa;
    }

    public void setTipoPessoa(final TipoPessoa tipoPessoa) {
        this.tipoPessoa = tipoPessoa;
    }

    public String getCpfOuCnpj() {
        return this.cpfOuCnpj;
    }

    public void setCpfOuCnpj(final String cpfOuCnpj) {
        this.cpfOuCnpj = cpfOuCnpj;
    }

    public String getTelefone() {
        return this.telefone;
    }

    public void setTelefone(final String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public Endereco getEndereco() {
        return this.endereco;
    }

    public void setEndereco(final Endereco endereco) {
        this.endereco = endereco;
    }

    public String getCpfOuCnpjSemFormatacao() {
        return TipoPessoa.removerFormatacao(this.cpfOuCnpj);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((this.codigo == null) ? 0 : this.codigo.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        final Cliente other = (Cliente) obj;
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