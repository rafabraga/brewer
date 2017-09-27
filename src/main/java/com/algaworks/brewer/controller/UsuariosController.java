package com.algaworks.brewer.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.algaworks.brewer.controller.page.PageWrapper;
import com.algaworks.brewer.model.Usuario;
import com.algaworks.brewer.repository.Grupos;
import com.algaworks.brewer.repository.Usuarios;
import com.algaworks.brewer.repository.filter.UsuarioFilter;
import com.algaworks.brewer.service.CadastroUsuarioService;
import com.algaworks.brewer.service.StatusUsuario;
import com.algaworks.brewer.service.exception.EmailJaCadastradoException;
import com.algaworks.brewer.service.exception.SenhaObrigatoriaUsuarioException;

@Controller
@RequestMapping("/usuarios")
public class UsuariosController {

    @Autowired
    private CadastroUsuarioService cadastroUsuarioService;

    @Autowired
    private Usuarios usuarios;

    @Autowired
    private Grupos grupos;

    @GetMapping("/novo")
    public ModelAndView novo(final Usuario usuario) {
        final ModelAndView mv = new ModelAndView("usuario/CadastroUsuario");
        mv.addObject("grupos", this.grupos.findAll());
        return mv;
    }

    @PostMapping("/novo")
    public ModelAndView cadastrar(@Valid final Usuario usuario, final BindingResult result, final RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return this.novo(usuario);
        }
        try {
            this.cadastroUsuarioService.salvar(usuario);
        } catch (final EmailJaCadastradoException e) {
            result.rejectValue("email", e.getMessage(), e.getMessage());
            return this.novo(usuario);
        } catch (final SenhaObrigatoriaUsuarioException e) {
            result.rejectValue("senha", e.getMessage(), e.getMessage());
            return this.novo(usuario);
        }
        attributes.addFlashAttribute("mensagem", "Salvo com sucesso.");
        return new ModelAndView("redirect:/usuarios/novo");
    }

    @GetMapping
    public ModelAndView pesquisar(final UsuarioFilter usuarioFilter, final BindingResult result,
            @PageableDefault(size = 2) final Pageable pageable, final HttpServletRequest httpServletRequest) {
        final ModelAndView mv = new ModelAndView("usuario/PesquisaUsuarios");
        mv.addObject("grupos", this.grupos.findAll());
        mv.addObject("pagina", new PageWrapper<>(this.usuarios.filtrar(usuarioFilter, pageable), httpServletRequest));
        return mv;
    }

    @PutMapping("/status")
    @ResponseStatus(HttpStatus.OK)
    public void atualizarStatus(@RequestParam("codigos[]") final Long[] codigos,
            @RequestParam("status") final StatusUsuario statusUsuario) {
        this.cadastroUsuarioService.alterarStatus(codigos, statusUsuario);
    }

}
