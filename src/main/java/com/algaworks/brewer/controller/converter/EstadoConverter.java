package com.algaworks.brewer.controller.converter;

import org.springframework.core.convert.converter.Converter;
import org.thymeleaf.util.StringUtils;

import com.algaworks.brewer.model.Estado;

public class EstadoConverter implements Converter<String, Estado> {

    @Override
    public Estado convert(final String codigo) {
        if (!StringUtils.isEmptyOrWhitespace(codigo)) {
            final Estado estado = new Estado();
            estado.setCodigo(Long.valueOf(codigo));
            return estado;
        }
        return null;
    }

}
