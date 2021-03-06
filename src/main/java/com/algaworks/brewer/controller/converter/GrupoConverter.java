package com.algaworks.brewer.controller.converter;

import org.springframework.core.convert.converter.Converter;
import org.thymeleaf.util.StringUtils;

import com.algaworks.brewer.model.Grupo;

public class GrupoConverter implements Converter<String, Grupo> {

    @Override
    public Grupo convert(final String codigo) {
        if (!StringUtils.isEmptyOrWhitespace(codigo)) {
            final Grupo grupo = new Grupo();
            grupo.setCodigo(Long.valueOf(codigo));
            return grupo;
        }
        return null;
    }

}
