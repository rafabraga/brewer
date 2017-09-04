package com.algaworks.brewer.thymeleaf.processor;

import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.engine.AttributeName;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractAttributeTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.spring4.util.FieldUtils;
import org.thymeleaf.templatemode.TemplateMode;

public class ClassForErrorAttributeTagProcessor extends AbstractAttributeTagProcessor {

    private static final String NOME_ATRIBUTO = "classforerror";
    private static final Integer PRECEDENCIA = 1000;

    public ClassForErrorAttributeTagProcessor(final String dialectPrefix) {
        super(TemplateMode.HTML, dialectPrefix, null, false, NOME_ATRIBUTO, true, PRECEDENCIA, true);
    }

    @Override
    protected void doProcess(final ITemplateContext context, final IProcessableElementTag tag, final AttributeName attributeName,
            final String attributeValue, final IElementTagStructureHandler structureHandler) {
        final Boolean temErro = FieldUtils.hasErrors(context, attributeValue);
        if (temErro) {
            final String classesExistentes = tag.getAttributeValue("class");
            structureHandler.setAttribute("class", classesExistentes + " has-error");
        }

    }

}
