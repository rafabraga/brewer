package com.algaworks.brewer.service.event.cerveja;

import org.springframework.util.StringUtils;

import com.algaworks.brewer.model.Cerveja;

public class CervejaSalvaEvent {

    private final Cerveja cerveja;

    public CervejaSalvaEvent(final Cerveja cerveja) {
        this.cerveja = cerveja;
    }

    public boolean temFoto() {
        return !StringUtils.isEmpty(this.cerveja.getFoto());
    }

    /**
     * @return the cerveja
     */
    public Cerveja getCerveja() {
        return this.cerveja;
    }

}
