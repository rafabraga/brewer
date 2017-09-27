package com.algaworks.brewer.service.exception;

public class EmailJaCadastradoException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public EmailJaCadastradoException() {
        super("O e-mail já foi cadastrado.");
    }

}
