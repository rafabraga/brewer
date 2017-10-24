package com.algaworks.brewer.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.algaworks.brewer.controller.page.PageWrapper;
import com.algaworks.brewer.dto.CervejaDTO;
import com.algaworks.brewer.model.Cerveja;
import com.algaworks.brewer.model.Origem;
import com.algaworks.brewer.model.Sabor;
import com.algaworks.brewer.repository.Cervejas;
import com.algaworks.brewer.repository.Estilos;
import com.algaworks.brewer.repository.filter.CervejaFilter;
import com.algaworks.brewer.service.CadastroCervejaService;
import com.algaworks.brewer.service.exception.ImpossivelExcluirEntidadeException;

@Controller
@RequestMapping("/cervejas")
public class CervejasController {

    @Autowired
    private Estilos estilos;

    @Autowired
    private Cervejas cervejas;

    @Autowired
    private CadastroCervejaService cadastroCervejaService;

    @GetMapping("/novo")
    public ModelAndView novo(final Cerveja cerveja) {
        final ModelAndView mv = new ModelAndView("cerveja/CadastroCerveja");
        mv.addObject("sabores", Sabor.values());
        mv.addObject("estilos", this.estilos.findAll());
        mv.addObject("origens", Origem.values());
        return mv;
    }

    @PostMapping(value = { "/novo", "{\\d+}" })
    public ModelAndView salvar(@Valid final Cerveja cerveja, final BindingResult result, final Model model,
            final RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return this.novo(cerveja);
        }
        this.cadastroCervejaService.salvar(cerveja);
        attributes.addFlashAttribute("mensagem", "Salvo com sucesso.");
        return new ModelAndView("redirect:/cervejas/novo");
    }

    @GetMapping
    public ModelAndView pesquisar(final CervejaFilter cervejaFilter, final BindingResult result,
            @PageableDefault(size = 2) final Pageable pageable, final HttpServletRequest httpServletRequest) {
        final ModelAndView mv = new ModelAndView("cerveja/PesquisaCervejas");
        mv.addObject("sabores", Sabor.values());
        mv.addObject("estilos", this.estilos.findAll());
        mv.addObject("origens", Origem.values());
        mv.addObject("pagina", new PageWrapper<>(this.cervejas.filtrar(cervejaFilter, pageable), httpServletRequest));
        return mv;
    }

    @ResponseBody
    @RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<CervejaDTO> pesquisarPorSkuOuNome(final String skuOuNome) {
        return this.cervejas.listarPorSkuOuNome(skuOuNome);
    }

    @DeleteMapping("/{codigo}")
    public @ResponseBody ResponseEntity<?> excluir(@PathVariable("codigo") final Cerveja cerveja) {
        try {
            this.cadastroCervejaService.excluir(cerveja);
        } catch (final ImpossivelExcluirEntidadeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{codigo}")
    public ModelAndView editar(@PathVariable("codigo") final Cerveja cerveja) {
        final ModelAndView mv = this.novo(cerveja);
        mv.addObject("cerveja", cerveja);
        return mv;
    }

}
