package com.algaworks.brewer.service;

import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.brewer.model.Venda;
import com.algaworks.brewer.repository.Vendas;

@Service
public class CadastroVendaService {

    @Autowired
    private Vendas vendas;

    public void salvar(final Venda venda) {
        if (venda.isNova()) {
            venda.setDataCriacao(LocalDateTime.now());
        }

        if (venda.getDataEntrega() != null) {
            venda.setDataHoraEntrega(
                    LocalDateTime.of(venda.getDataEntrega(), venda.getHoraEntrega() != null ? venda.getHoraEntrega() : LocalTime.NOON));
        }

        this.vendas.save(venda);
    }

}
