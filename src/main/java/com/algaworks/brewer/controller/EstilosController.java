package com.algaworks.brewer.controller;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.algaworks.brewer.controller.page.PageWrapper;
import com.algaworks.brewer.model.Estilo;
import com.algaworks.brewer.repository.Estilos;
import com.algaworks.brewer.repository.filter.EstiloFilter;
import com.algaworks.brewer.service.CadastroEstiloService;
import com.algaworks.brewer.service.exception.NomeEstiloJaCadastradoException;

@Controller
@RequestMapping("/estilos")
public class EstilosController {

    @Autowired
    private CadastroEstiloService cadastroEstiloService;

    @Autowired
    private Estilos estilos;

    @GetMapping("/novo")
    public ModelAndView novo(final Estilo estilo) {
        return new ModelAndView("estilo/CadastroEstilo");
    }

    @PostMapping("/novo")
    public ModelAndView cadastrar(@Valid final Estilo estilo, final BindingResult result, final Model model,
            final RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return this.novo(estilo);
        }
        try {
            this.cadastroEstiloService.salvar(estilo);
        } catch (final NomeEstiloJaCadastradoException e) {
            result.rejectValue("nome", e.getMessage(), e.getMessage());
            return this.novo(estilo);
        }
        attributes.addFlashAttribute("mensagem", "Salvo com sucesso.");
        return new ModelAndView("redirect:/estilos/novo");
    }

    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
    public @ResponseBody ResponseEntity<?> salvar(@RequestBody @Valid final Estilo estilo, final BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body(result.getFieldError("nome").getDefaultMessage());
        }
        final Estilo estiloSalvo = this.cadastroEstiloService.salvar(estilo);
        return ResponseEntity.ok(estiloSalvo);
    }

    @GetMapping
    public ModelAndView pesquisar(final EstiloFilter estiloFilter, final BindingResult result,
            @PageableDefault(size = 2) final Pageable pageable, final HttpServletRequest httpServletRequest) {
        final ModelAndView mv = new ModelAndView("estilo/PesquisaEstilos");
        mv.addObject("pagina", new PageWrapper<>(this.estilos.filtrar(estiloFilter, pageable), httpServletRequest));
        return mv;
    }

}
