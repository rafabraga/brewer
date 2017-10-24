package com.algaworks.brewer.service;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.brewer.model.Cerveja;
import com.algaworks.brewer.repository.Cervejas;
import com.algaworks.brewer.service.exception.ImpossivelExcluirEntidadeException;
import com.algaworks.brewer.storage.FotoStorage;

@Service
public class CadastroCervejaService {

    @Autowired
    private Cervejas cervejas;

    @Autowired
    private FotoStorage fotoStorage;

    public void salvar(final Cerveja cerveja) {
        this.cervejas.save(cerveja);
    }

    public void excluir(final Cerveja cerveja) {
        try {
            final String foto = cerveja.getFoto();

            this.cervejas.delete(cerveja);
            this.cervejas.flush();

            this.fotoStorage.excluir(foto);
        } catch (final PersistenceException e) {
            throw new ImpossivelExcluirEntidadeException("");
        }
    }

}
