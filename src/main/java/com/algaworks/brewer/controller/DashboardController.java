package com.algaworks.brewer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.algaworks.brewer.repository.Cervejas;
import com.algaworks.brewer.repository.Clientes;
import com.algaworks.brewer.repository.Vendas;

@Controller
public class DashboardController {

    @Autowired
    private Vendas vendas;

    @Autowired
    private Cervejas cervejas;

    @Autowired
    private Clientes clientes;

    @GetMapping("/")
    public ModelAndView dashboard() {
        final ModelAndView mv = new ModelAndView("Dashboard");
        mv.addObject("vendasNoAno", this.vendas.valorTotalNoAno());
        mv.addObject("vendasNoMes", this.vendas.valorTotalNoMes());
        mv.addObject("ticketMedio", this.vendas.valorTicketMedioNoAno());

        mv.addObject("valorItensEstoque", this.cervejas.valorItensEstoque());
        mv.addObject("totalClientes", this.clientes.count());

        return mv;
    }

}
