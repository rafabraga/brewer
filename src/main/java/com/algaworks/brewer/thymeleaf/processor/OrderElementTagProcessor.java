package com.algaworks.brewer.thymeleaf.processor;

import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.model.IAttribute;
import org.thymeleaf.model.IModel;
import org.thymeleaf.model.IModelFactory;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractElementTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.templatemode.TemplateMode;

public class OrderElementTagProcessor extends AbstractElementTagProcessor {

    private static final String NOME_TAG = "order";
    private static final Integer PRECEDENCIA = 1000;

    public OrderElementTagProcessor(final String dialectPrefix) {
        super(TemplateMode.HTML, dialectPrefix, NOME_TAG, true, null, false, PRECEDENCIA);
    }

    @Override
    protected void doProcess(final ITemplateContext context, final IProcessableElementTag tag,
            final IElementTagStructureHandler structureHandler) {
        final IModelFactory modelFactory = context.getModelFactory();
        final IModel model = modelFactory.createModel();

        final IAttribute page = tag.getAttribute("page");
        final IAttribute field = tag.getAttribute("field");
        final IAttribute text = tag.getAttribute("text");

        model.add(modelFactory.createStandaloneElementTag("th:block", "th:include",
                String.format("fragments/Ordenacao :: order (%s, %s, %s)", page.getValue(), field.getValue(), text.getValue())));

        // O código ainda precisa ser processado, se fosse HTML não precisaria.
        structureHandler.replaceWith(model, true);

    }

}
