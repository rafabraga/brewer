package com.algaworks.brewer.controller.converter;

import org.springframework.core.convert.converter.Converter;
import org.thymeleaf.util.StringUtils;

import com.algaworks.brewer.model.Estilo;

public class EstiloConverter implements Converter<String, Estilo> {

    @Override
    public Estilo convert(final String codigo) {
        if (!StringUtils.isEmptyOrWhitespace(codigo)) {
            final Estilo estilo = new Estilo();
            estilo.setCodigo(Long.valueOf(codigo));
            return estilo;
        }
        return null;
    }

}
