package com.algaworks.brewer.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.brewer.model.Estilo;
import com.algaworks.brewer.repository.Estilos;
import com.algaworks.brewer.service.exception.NomeEstiloJaCadastradoException;

@Service
public class CadastroEstiloService {

    @Autowired
    private Estilos estilos;

    public Estilo salvar(final Estilo estilo) {
        final Optional<Estilo> optionalEstilo = this.estilos.findByNomeIgnoreCase(estilo.getNome());
        if (optionalEstilo.isPresent()) {
            throw new NomeEstiloJaCadastradoException("Nome do estilo já cadastrado.");
        }
        return this.estilos.saveAndFlush(estilo);
    }

}
