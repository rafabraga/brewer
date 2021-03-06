package com.algaworks.brewer.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.algaworks.brewer.model.Usuario;
import com.algaworks.brewer.repository.Usuarios;
import com.algaworks.brewer.service.exception.EmailJaCadastradoException;
import com.algaworks.brewer.service.exception.SenhaObrigatoriaUsuarioException;

@Service
public class CadastroUsuarioService {

    @Autowired
    private Usuarios usuarios;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void salvar(final Usuario usuario) {
        final Optional<Usuario> optionalUsuario = this.usuarios.findByEmailIgnoreCase(usuario.getEmail());
        if (optionalUsuario.isPresent() && !optionalUsuario.get().equals(usuario)) {
            throw new EmailJaCadastradoException();
        }
        if (usuario.isNovo() && StringUtils.isEmpty(usuario.getSenha())) {
            throw new SenhaObrigatoriaUsuarioException();
        }
        if (usuario.isNovo() || !StringUtils.isEmpty(usuario.getSenha())) {
            usuario.setSenha(this.passwordEncoder.encode(usuario.getSenha()));
            // Setar na confirmação por causa do bean validation, que verifica se senha e
            // confirmação são iguais.
        } else if (StringUtils.isEmpty(usuario.getSenha())) {
            usuario.setSenha(optionalUsuario.get().getSenha());
        }
        usuario.setConfirmacaoSenha(usuario.getSenha());

        if (!usuario.isNovo() && (usuario.getAtivo() == null)) {
            usuario.setAtivo(optionalUsuario.get().getAtivo());
        }

        this.usuarios.save(usuario);
    }

    public void alterarStatus(final Long[] codigos, final StatusUsuario statusUsuario) {
        statusUsuario.executar(codigos, this.usuarios);
    }

}
