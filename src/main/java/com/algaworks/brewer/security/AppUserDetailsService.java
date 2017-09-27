package com.algaworks.brewer.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.algaworks.brewer.model.Usuario;
import com.algaworks.brewer.repository.Usuarios;

@Service
public class AppUserDetailsService implements UserDetailsService {

    @Autowired
    private Usuarios usuarios;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final Optional<Usuario> optionalUsuario = this.usuarios.findByEmailIgnoreCaseAndAtivoTrue(username);
        final Usuario usuario = optionalUsuario.orElseThrow(() -> new UsernameNotFoundException("Usuário e/ou senha incorretos."));

        return new UsuarioSistema(usuario, this.listarPermissoes(usuario));
    }

    private Collection<? extends GrantedAuthority> listarPermissoes(final Usuario usuario) {
        final List<GrantedAuthority> permissoes = new ArrayList<>();

        this.usuarios.permissoes(usuario).forEach(p -> permissoes.add(new SimpleGrantedAuthority(p.toUpperCase())));

        return permissoes;
    }

}
