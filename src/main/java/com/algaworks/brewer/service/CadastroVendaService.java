package com.algaworks.brewer.service;

import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.algaworks.brewer.model.StatusVenda;
import com.algaworks.brewer.model.Venda;
import com.algaworks.brewer.repository.Vendas;
import com.algaworks.brewer.service.event.venda.VendaEvent;

@Service
public class CadastroVendaService {

    @Autowired
    private Vendas vendas;

    @Autowired
    private ApplicationEventPublisher publisher;

    public Venda salvar(final Venda venda) {
        if (venda.isNova()) {
            venda.setDataCriacao(LocalDateTime.now());
        } else {
            final Venda vendaExistente = this.vendas.findOne(venda.getCodigo());
            venda.setDataCriacao(vendaExistente.getDataCriacao());
        }

        if (venda.getDataEntrega() != null) {
            venda.setDataHoraEntrega(
                    LocalDateTime.of(venda.getDataEntrega(), venda.getHoraEntrega() != null ? venda.getHoraEntrega() : LocalTime.NOON));
        }

        return this.vendas.saveAndFlush(venda);
    }

    public void emitir(final Venda venda) {
        venda.setStatus(StatusVenda.EMITIDA);
        this.salvar(venda);

        this.publisher.publishEvent(new VendaEvent(venda));
    }

    @PreAuthorize("#venda.usuario == principal.usuario or hasRole('CANCELAR_VENDA')")
    public void cancelar(final Venda venda) {
        final Venda vendaExistente = this.vendas.findOne(venda.getCodigo());

        vendaExistente.setStatus(StatusVenda.CANCELADA);
        this.vendas.save(vendaExistente);
    }

}
