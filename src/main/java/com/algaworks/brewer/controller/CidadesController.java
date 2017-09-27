package com.algaworks.brewer.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.algaworks.brewer.controller.page.PageWrapper;
import com.algaworks.brewer.model.Cidade;
import com.algaworks.brewer.repository.Cidades;
import com.algaworks.brewer.repository.Estados;
import com.algaworks.brewer.repository.filter.CidadeFilter;
import com.algaworks.brewer.service.CadastroCidadeService;
import com.algaworks.brewer.service.exception.CidadeJaCadastradaException;

@Controller
@RequestMapping("/cidades")
public class CidadesController {

    @Autowired
    private Cidades cidades;

    @Autowired
    private Estados estados;

    @Autowired
    private CadastroCidadeService cadastroCidadeService;

    @GetMapping("/novo")
    public ModelAndView novo(final Cidade cidade) {
        final ModelAndView mv = new ModelAndView("cidade/CadastroCidade");
        mv.addObject("estados", this.estados.findAll());
        return mv;
    }

    @PostMapping("/novo")
    @CacheEvict(value = "cidades", key = "#cidade.estado.codigo", condition = "#cidade.temEstado()")
    public ModelAndView cadastrar(@Valid final Cidade cidade, final BindingResult result, final RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return this.novo(cidade);
        }
        try {
            this.cadastroCidadeService.salvar(cidade);
        } catch (final CidadeJaCadastradaException e) {
            result.rejectValue("nome", e.getMessage(), e.getMessage());
            return this.novo(cidade);
        }
        attributes.addFlashAttribute("mensagem", "Salvo com sucesso.");
        return new ModelAndView("redirect:/cidades/novo");
    }

    @GetMapping
    public ModelAndView pesquisar(final CidadeFilter cidadeFilter, final BindingResult result,
            @PageableDefault(size = 2) final Pageable pageable, final HttpServletRequest httpServletRequest) {
        final ModelAndView mv = new ModelAndView("cidade/PesquisaCidades");
        mv.addObject("estados", this.estados.findAll());
        mv.addObject("pagina", new PageWrapper<>(this.cidades.filtrar(cidadeFilter, pageable), httpServletRequest));
        return mv;
    }

    @Cacheable(value = "cidades", key = "#codigoEstado")
    @RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<Cidade> pesquisarPorCodigoEstado(
            @RequestParam(name = "estado", defaultValue = "-1") final Long codigoEstado) {
        try {
            Thread.sleep(1000);
        } catch (final Exception e) {

        }
        return this.cidades.findByEstadoCodigo(codigoEstado);
    }

}
