package com.algaworks.brewer.service.exception;

public class CpfCnpjClienteJaCadastrado extends RuntimeException {

    private static final long serialVersionUID = -8457428695167533632L;

    public CpfCnpjClienteJaCadastrado() {
        super("CPF/CNPJ do cliente já cadastrado.");
    }

}
