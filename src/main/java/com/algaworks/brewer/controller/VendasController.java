
package com.algaworks.brewer.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.algaworks.brewer.controller.validator.VendaValidator;
import com.algaworks.brewer.model.Cerveja;
import com.algaworks.brewer.model.Venda;
import com.algaworks.brewer.repository.Cervejas;
import com.algaworks.brewer.security.UsuarioSistema;
import com.algaworks.brewer.service.CadastroVendaService;
import com.algaworks.brewer.session.TabelasItensSession;

@Controller
@RequestMapping("/vendas")
public class VendasController {

    @Autowired
    private Cervejas cervejas;

    @Autowired
    private TabelasItensSession tabelaItens;

    @Autowired
    private CadastroVendaService cadastroVendaService;

    @Autowired
    private VendaValidator vendaValidator;

    @InitBinder
    public void iniciarValidadores(final WebDataBinder binder) {
        binder.addValidators(this.vendaValidator);
    }

    @GetMapping("/novo")
    public ModelAndView novo(final Venda venda) {
        final ModelAndView mv = new ModelAndView("venda/CadastroVenda");

        if (StringUtils.isEmpty(venda.getUuid())) {
            venda.setUuid(UUID.randomUUID().toString());
        }

        mv.addObject("itens", venda.getItens());
        mv.addObject("valorFrete", venda.getValorFrete());
        mv.addObject("valorDesconto", venda.getValorDesconto());
        mv.addObject("valorTotalItens", this.tabelaItens.getValorTotal(venda.getUuid()));
        return mv;
    }

    @PostMapping("/novo")
    public ModelAndView salvar(final Venda venda, final BindingResult result, final RedirectAttributes attributes,
            @AuthenticationPrincipal final UsuarioSistema usuarioSistema) {
        venda.adicionarItens(this.tabelaItens.getItensVenda(venda.getUuid()));
        venda.calcularValorTotal();

        this.vendaValidator.validate(venda, result);
        if (result.hasErrors()) {
            return this.novo(venda);
        }

        venda.setUsuario(usuarioSistema.getUsuario());
        this.cadastroVendaService.salvar(venda);
        attributes.addFlashAttribute("mensagem", "Venda salva com sucesso");
        return new ModelAndView("redirect:/vendas/novo");
    }

    @PostMapping("/item")
    public ModelAndView adicionarItem(final Long codigoCerveja, final String uuid) {
        final Cerveja cerveja = this.cervejas.findOne(codigoCerveja);
        this.tabelaItens.adicionarItem(uuid, cerveja, 1);
        return this.mvTabelaItensVenda(uuid);
    }

    @PutMapping("/item/{codigoCerveja}")
    public ModelAndView alterarQuantidadeItem(@PathVariable final Long codigoCerveja, final Integer quantidade, final String uuid) {
        final Cerveja cerveja = this.cervejas.findOne(codigoCerveja);
        this.tabelaItens.alterarQuantidadeCerveja(uuid, cerveja, quantidade);
        return this.mvTabelaItensVenda(uuid);
    }

    /*
     * Este método utiliza melhor a integração com o Spring Data. Podemos perceber
     * que o próprio Spring consegue usar o findOne() do repository para converter o
     * código na entidade. Para isso, adicionei o método domainClassConverter() em
     * WebConfig.
     */
    @DeleteMapping("/item/{uuid}/{codigoCerveja}")
    public ModelAndView excluirItem(@PathVariable("codigoCerveja") final Cerveja cerveja, @PathVariable final String uuid) {
        this.tabelaItens.excluirCerveja(uuid, cerveja);
        return this.mvTabelaItensVenda(uuid);
    }

    /**
     * @return
     */
    protected ModelAndView mvTabelaItensVenda(final String uuid) {
        final ModelAndView mv = new ModelAndView("venda/TabelaItensVenda");
        mv.addObject("valorTotal", this.tabelaItens.getValorTotal(uuid));
        mv.addObject("itens", this.tabelaItens.getItensVenda(uuid));
        return mv;
    }

}
