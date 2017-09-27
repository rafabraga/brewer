package com.algaworks.brewer.service.exception;

public class SenhaObrigatoriaUsuarioException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public SenhaObrigatoriaUsuarioException() {
        super("A senha é obrigatória para novo usuário.");
    }

}
