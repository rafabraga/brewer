package com.algaworks.brewer.controller.converter;

import org.springframework.core.convert.converter.Converter;
import org.thymeleaf.util.StringUtils;

import com.algaworks.brewer.model.Cidade;

public class CidadeConverter implements Converter<String, Cidade> {

    @Override
    public Cidade convert(final String codigo) {
        if (!StringUtils.isEmptyOrWhitespace(codigo)) {
            final Cidade cidade = new Cidade();
            cidade.setCodigo(Long.valueOf(codigo));
            return cidade;
        }
        return null;
    }

}
