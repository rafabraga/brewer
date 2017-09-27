package com.algaworks.brewer.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import com.algaworks.brewer.validation.AtributoConfirmacao;

@AtributoConfirmacao(atributo = "senha", atributoConfirmacao = "confirmacaoSenha", message = "Senha e confirmação não conferem.")
@Entity
@Table(name = "usuario")
@DynamicUpdate
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "USUARIO_SEQ")
    @SequenceGenerator(name = "USUARIO_SEQ", sequenceName = "usuario_codigo_seq", allocationSize = 1)
    private Long codigo;

    @NotBlank(message = "Nome é obrigatório.")
    private String nome;

    @NotBlank(message = "E-mail é obrigatório.")
    @Email(message = "E-mail inválido")
    private String email;

    private String senha;

    @Transient
    private String confirmacaoSenha;

    private Boolean ativo;

    @Size(min = 1, message = "Selecione pelo menos um grupo.")
    @ManyToMany
    @JoinTable(name = "usuario_grupo", joinColumns = @JoinColumn(name = "codigo_usuario"), inverseJoinColumns = @JoinColumn(name = "codigo_grupo"))
    private List<Grupo> grupos;

    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    @PreUpdate
    private void preUpdate() {
        this.confirmacaoSenha = this.senha;
    }

    public boolean isNovo() {
        return this.codigo == null;
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
     * @return the email
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(final String email) {
        this.email = email;
    }

    /**
     * @return the senha
     */
    public String getSenha() {
        return this.senha;
    }

    /**
     * @param senha the senha to set
     */
    public void setSenha(final String senha) {
        this.senha = senha;
    }

    /**
     * @return the ativo
     */
    public Boolean getAtivo() {
        return this.ativo;
    }

    /**
     * @param ativo the ativo to set
     */
    public void setAtivo(final Boolean ativo) {
        this.ativo = ativo;
    }

    /**
     * @return the grupos
     */
    public List<Grupo> getGrupos() {
        return this.grupos;
    }

    /**
     * @param grupos the grupos to set
     */
    public void setGrupos(final List<Grupo> grupos) {
        this.grupos = grupos;
    }

    /**
     * @return the dataNascimento
     */
    public LocalDate getDataNascimento() {
        return this.dataNascimento;
    }

    /**
     * @param dataNascimento the dataNascimento to set
     */
    public void setDataNascimento(final LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    /**
     * @return the confirmacaoSenha
     */
    public String getConfirmacaoSenha() {
        return this.confirmacaoSenha;
    }

    /**
     * @param confirmacaoSenha the confirmacaoSenha to set
     */
    public void setConfirmacaoSenha(final String confirmacaoSenha) {
        this.confirmacaoSenha = confirmacaoSenha;
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
        final Usuario other = (Usuario) obj;
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