package com.algaworks.brewer.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.brewer.model.Cidade;
import com.algaworks.brewer.repository.Cidades;
import com.algaworks.brewer.service.exception.CidadeJaCadastradaException;

@Service
public class CadastroCidadeService {

    @Autowired
    private Cidades cidades;

    public void salvar(final Cidade cidade) {
        final Optional<Cidade> optionalCidade = this.cidades.findByNomeIgnoreCaseAndEstadoCodigo(cidade.getNome(),
                cidade.getEstado().getCodigo());
        if (optionalCidade.isPresent()) {
            throw new CidadeJaCadastradaException();
        }
        this.cidades.save(cidade);
    }

}
