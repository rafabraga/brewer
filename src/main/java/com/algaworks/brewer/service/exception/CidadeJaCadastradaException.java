package com.algaworks.brewer.service.exception;

public class CidadeJaCadastradaException extends RuntimeException {

    private static final long serialVersionUID = -7116992416002480814L;

    public CidadeJaCadastradaException() {
        super("Cidade já cadastrada.");
    }

}
