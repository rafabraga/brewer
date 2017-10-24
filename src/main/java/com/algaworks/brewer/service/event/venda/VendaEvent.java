package com.algaworks.brewer.service.event.venda;

import com.algaworks.brewer.model.Venda;

public class VendaEvent {

    private final Venda venda;

    public VendaEvent(final Venda venda) {
        this.venda = venda;
    }

    /**
     * @return the venda
     */
    public Venda getVenda() {
        return this.venda;
    }

}
