package com.algaworks.brewer.service;

import com.algaworks.brewer.repository.Usuarios;

public enum StatusUsuario {

    ATIVAR {
        @Override
        public void executar(final Long[] codigos, final Usuarios usuarios) {
            usuarios.findByCodigoIn(codigos).forEach(u -> {
                u.setAtivo(true);
                usuarios.save(u);
            });
        }
    },
    DESATIVAR {
        @Override
        public void executar(final Long[] codigos, final Usuarios usuarios) {
            usuarios.findByCodigoIn(codigos).forEach(u -> {
                u.setAtivo(false);
                usuarios.save(u);
            });
        }
    };

    public abstract void executar(Long[] codigos, Usuarios usuarios);

}
