package com.algaworks.brewer.service.event.venda;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.algaworks.brewer.model.Cerveja;
import com.algaworks.brewer.model.ItemVenda;
import com.algaworks.brewer.repository.Cervejas;

@Component
public class VendaListener {

    @Autowired
    private Cervejas cervejas;

    @EventListener
    public void vendaEmitida(final VendaEvent vendaEvent) {
        for (final ItemVenda item : vendaEvent.getVenda().getItens()) {
            final Cerveja cerveja = this.cervejas.findOne(item.getCerveja().getCodigo());
            cerveja.setQuantidadeEstoque(cerveja.getQuantidadeEstoque() - item.getQuantidade());
            this.cervejas.save(cerveja);
        }
    }
}
