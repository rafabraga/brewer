package com.algaworks.brewer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.algaworks.brewer.model.Cerveja;
import com.algaworks.brewer.repository.Cervejas;
import com.algaworks.brewer.service.event.cerveja.CervejaSalvaEvent;

@Service
public class CadastroCervejaService {

    @Autowired
    private Cervejas cervejas;

    @Autowired
    private ApplicationEventPublisher publisher;

    public void salvar(final Cerveja cerveja) {
        this.cervejas.save(cerveja);

        this.publisher.publishEvent(new CervejaSalvaEvent(cerveja));
    }

}
