package com.algaworks.brewer.session;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import com.algaworks.brewer.model.Cerveja;

public class TabelaItensVendaTest {

    private TabelaItensVenda tabelaItensVenda;

    @Before
    public void setUp() {
        this.tabelaItensVenda = new TabelaItensVenda("1");
    }

    @Test
    public void deveCalcularValorTotalSemItens() throws Exception {
        assertEquals(BigDecimal.ZERO, this.tabelaItensVenda.getValorTotal());
    }

    @Test
    public void deveCalcularValorTotalComUmItem() throws Exception {
        final Cerveja cerveja = new Cerveja();
        final BigDecimal valor = new BigDecimal("8.90");
        cerveja.setValor(valor);

        this.tabelaItensVenda.adicionarItem(cerveja, 1);

        assertEquals(valor, this.tabelaItensVenda.getValorTotal());
    }

    @Test
    public void deveCalcularValorTotalComVariosItens() throws Exception {
        final Cerveja cerveja1 = new Cerveja();
        final BigDecimal valor1 = new BigDecimal("8.90");
        cerveja1.setCodigo(1L);
        cerveja1.setValor(valor1);

        final Cerveja cerveja2 = new Cerveja();
        final BigDecimal valor2 = new BigDecimal("10");
        cerveja2.setCodigo(2L);
        cerveja2.setValor(valor2);

        this.tabelaItensVenda.adicionarItem(cerveja1, 1);
        this.tabelaItensVenda.adicionarItem(cerveja2, 2);

        assertEquals(new BigDecimal("28.90"), this.tabelaItensVenda.getValorTotal());
    }

    @Test
    public void deveManterTamanhoListaParaMesmasCervejas() throws Exception {
        final Cerveja cerveja1 = new Cerveja();
        final BigDecimal valor1 = new BigDecimal("8.90");
        cerveja1.setCodigo(1L);
        cerveja1.setValor(valor1);

        this.tabelaItensVenda.adicionarItem(cerveja1, 1);
        this.tabelaItensVenda.adicionarItem(cerveja1, 1);

        assertEquals(new BigDecimal("17.80"), this.tabelaItensVenda.getValorTotal());
        assertEquals(1, this.tabelaItensVenda.total());
    }

}
