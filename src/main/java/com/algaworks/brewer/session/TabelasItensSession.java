package com.algaworks.brewer.session;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import com.algaworks.brewer.model.Cerveja;
import com.algaworks.brewer.model.ItemVenda;

@Component
@SessionScope
public class TabelasItensSession {

    private final Set<TabelaItensVenda> tabelas = new HashSet<>();

    public void adicionarItem(final String uuid, final Cerveja cerveja, final int quantidade) {
        final TabelaItensVenda tabela = this.buscarTabelaPorUuid(uuid);
        tabela.adicionarItem(cerveja, quantidade);
        this.tabelas.add(tabela);
    }

    public void alterarQuantidadeCerveja(final String uuid, final Cerveja cerveja, final Integer quantidade) {
        final TabelaItensVenda tabela = this.buscarTabelaPorUuid(uuid);
        tabela.alterarQuantidadeCerveja(cerveja, quantidade);
    }

    public void excluirCerveja(final String uuid, final Cerveja cerveja) {
        final TabelaItensVenda tabela = this.buscarTabelaPorUuid(uuid);
        tabela.excluirCerveja(cerveja);
    }

    public List<ItemVenda> getItensVenda(final String uuid) {
        final TabelaItensVenda tabela = this.buscarTabelaPorUuid(uuid);
        return tabela.getItensVenda();
    }

    protected TabelaItensVenda buscarTabelaPorUuid(final String uuid) {
        return this.tabelas.stream().filter(t -> t.getUuid().equals(uuid)).findAny().orElse(new TabelaItensVenda(uuid));
    }

    public BigDecimal getValorTotal(final String uuid) {
        return this.buscarTabelaPorUuid(uuid).getValorTotal();
    }

}
