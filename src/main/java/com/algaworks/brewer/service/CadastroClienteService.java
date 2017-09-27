package com.algaworks.brewer.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.brewer.model.Cliente;
import com.algaworks.brewer.repository.Clientes;
import com.algaworks.brewer.service.exception.CpfCnpjClienteJaCadastrado;

@Service
public class CadastroClienteService {

    @Autowired
    private Clientes clientes;

    public void salvar(final Cliente cliente) {
        final Optional<Cliente> clienteExistente = this.clientes.findByCpfOuCnpj(cliente.getCpfOuCnpjSemFormatacao());
        if (clienteExistente.isPresent()) {
            throw new CpfCnpjClienteJaCadastrado();
        }
        this.clientes.save(cliente);
    }

}
