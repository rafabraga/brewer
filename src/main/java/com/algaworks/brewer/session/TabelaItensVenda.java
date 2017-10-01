package com.algaworks.brewer.session;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import com.algaworks.brewer.model.Cerveja;
import com.algaworks.brewer.model.ItemVenda;

class TabelaItensVenda {

    private final String uuid;

    private final List<ItemVenda> itensVenda = new ArrayList<>();

    /**
     * @param uuid
     */
    public TabelaItensVenda(final String uuid) {
        this.uuid = uuid;
    }

    public BigDecimal getValorTotal() {
        return this.itensVenda.stream().map(ItemVenda::getValorTotal).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
    }

    public void adicionarItem(final Cerveja cerveja, final Integer quantidade) {
        final Optional<ItemVenda> itemVendaOptional = this.buscarItemPorCerveja(cerveja);

        if (itemVendaOptional.isPresent()) {
            itemVendaOptional.get().setQuantidade(itemVendaOptional.get().getQuantidade() + quantidade);
        } else {
            final ItemVenda itemVenda = new ItemVenda();
            itemVenda.setCerveja(cerveja);
            itemVenda.setQuantidade(quantidade);
            itemVenda.setValorUnitario(cerveja.getValor());

            this.itensVenda.add(itemVenda);
        }
    }

    public void alterarQuantidadeCerveja(final Cerveja cerveja, final Integer quantidade) {
        final ItemVenda itemVenda = this.buscarItemPorCerveja(cerveja).get();
        itemVenda.setQuantidade(quantidade);
    }

    public void excluirCerveja(final Cerveja cerveja) {
        final int indice = IntStream.range(0, this.itensVenda.size()).filter(i -> this.itensVenda.get(i).getCerveja().equals(cerveja))
                .findAny().getAsInt();
        this.itensVenda.remove(indice);
    }

    /**
     * @param cerveja
     * @return
     */
    protected Optional<ItemVenda> buscarItemPorCerveja(final Cerveja cerveja) {
        return this.itensVenda.stream().filter(i -> i.getCerveja().equals(cerveja)).findAny();
    }

    public List<ItemVenda> getItensVenda() {
        return this.itensVenda;
    }

    public int total() {
        return this.itensVenda.size();
    }

    public String getUuid() {
        return this.uuid;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((this.uuid == null) ? 0 : this.uuid.hashCode());
        return result;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof TabelaItensVenda)) {
            return false;
        }
        final TabelaItensVenda other = (TabelaItensVenda) obj;
        if (this.uuid == null) {
            if (other.uuid != null) {
                return false;
            }
        } else if (!this.uuid.equals(other.uuid)) {
            return false;
        }
        return true;
    }

}
