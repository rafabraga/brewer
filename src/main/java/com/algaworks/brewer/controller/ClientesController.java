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
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.algaworks.brewer.controller.page.PageWrapper;
import com.algaworks.brewer.model.Cliente;
import com.algaworks.brewer.model.TipoPessoa;
import com.algaworks.brewer.repository.Clientes;
import com.algaworks.brewer.repository.Estados;
import com.algaworks.brewer.repository.filter.ClienteFilter;
import com.algaworks.brewer.service.CadastroClienteService;
import com.algaworks.brewer.service.exception.CpfCnpjClienteJaCadastrado;

@Controller
@RequestMapping("/clientes")
public class ClientesController {

    @Autowired
    private Estados estados;

    @Autowired
    private Clientes clientes;

    @Autowired
    private CadastroClienteService cadastroClienteService;

    @GetMapping("/novo")
    public ModelAndView novo(final Cliente cliente) {
        final ModelAndView mv = new ModelAndView("cliente/CadastroCliente");
        mv.addObject("tiposPessoa", TipoPessoa.values());
        mv.addObject("estados", this.estados.findAll());
        return mv;
    }

    @PostMapping("/novo")
    public ModelAndView salvar(@Valid final Cliente cliente, final BindingResult result, final RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return this.novo(cliente);
        }
        try {
            this.cadastroClienteService.salvar(cliente);
        } catch (final CpfCnpjClienteJaCadastrado e) {
            result.rejectValue("cpfOuCnpj", e.getMessage(), e.getMessage());
            return this.novo(cliente);
        }
        attributes.addFlashAttribute("mensagem", "Salvo com sucesso.");
        return new ModelAndView("redirect:/clientes/novo");
    }

    @GetMapping
    public ModelAndView pesquisar(final ClienteFilter clienteFilter, final BindingResult result,
            @PageableDefault(size = 2) final Pageable pageable, final HttpServletRequest httpServletRequest) {
        final ModelAndView mv = new ModelAndView("cliente/PesquisaClientes");
        mv.addObject("pagina", new PageWrapper<>(this.clientes.filtrar(clienteFilter, pageable), httpServletRequest));
        return mv;
    }

    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody List<Cliente> pesquisar(final String nome) {
        this.validarPesquisaRapida(nome);
        return this.clientes.findByNomeStartingWithIgnoreCase(nome);
    }

    public void validarPesquisaRapida(final String nome) {
        if (StringUtils.isEmpty(nome) || (nome.length() < 3)) {
            throw new IllegalArgumentException();
        }
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Void> tratarIllegalArgumentException(final IllegalArgumentException e) {
        return ResponseEntity.badRequest().build();
    }

}
