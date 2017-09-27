package com.algaworks.brewer.repository.filter;

import java.io.Serializable;

public class ClienteFilter implements Serializable {

    private static final long serialVersionUID = -126928190423967247L;

    private String nome;
    private String cpfOuCnpj;

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
     * @return the cpfOuCnpj
     */
    public String getCpfOuCnpj() {
        return this.cpfOuCnpj;
    }

    /**
     * @param cpfOuCnpj the cpfOuCnpj to set
     */
    public void setCpfOuCnpj(final String cpfOuCnpj) {
        this.cpfOuCnpj = cpfOuCnpj;
    }

}
