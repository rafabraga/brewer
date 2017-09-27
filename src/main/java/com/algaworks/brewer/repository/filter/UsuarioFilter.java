package com.algaworks.brewer.repository.filter;

import java.io.Serializable;
import java.util.List;

import com.algaworks.brewer.model.Grupo;

public class UsuarioFilter implements Serializable {

    private static final long serialVersionUID = -4190284298703589611L;

    private String nome;
    private String email;
    private List<Grupo> grupos;

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

}
