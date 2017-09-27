package com.algaworks.brewer.model.validation;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;

import com.algaworks.brewer.model.Cliente;

public class ClienteGroupSequenceProvider implements DefaultGroupSequenceProvider<Cliente> {

    @Override
    public List<Class<?>> getValidationGroups(final Cliente cliente) {
        final List<Class<?>> grupos = new ArrayList<>();
        grupos.add(Cliente.class);

        if (this.isTipoPessoaInformado(cliente)) {
            grupos.add(cliente.getTipoPessoa().getGrupo());
        }

        return grupos;
    }

    private boolean isTipoPessoaInformado(final Cliente cliente) {
        return (cliente != null) && (cliente.getTipoPessoa() != null);
    }

}
