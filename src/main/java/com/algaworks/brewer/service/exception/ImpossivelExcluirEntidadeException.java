package com.algaworks.brewer.service.exception;

public class ImpossivelExcluirEntidadeException extends RuntimeException {

    /** Constante de serialização. */
    private static final long serialVersionUID = -3923052442890328070L;

    public ImpossivelExcluirEntidadeException(final String mensagem) {
        super(mensagem);
    }
}
