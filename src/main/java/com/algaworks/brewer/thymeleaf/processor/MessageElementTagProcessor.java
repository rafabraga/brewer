package com.algaworks.brewer.thymeleaf.processor;

import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.model.IModel;
import org.thymeleaf.model.IModelFactory;
import org.thymeleaf.model.IProcessableElementTag;
import org.thymeleaf.processor.element.AbstractElementTagProcessor;
import org.thymeleaf.processor.element.IElementTagStructureHandler;
import org.thymeleaf.templatemode.TemplateMode;

public class MessageElementTagProcessor extends AbstractElementTagProcessor {

    private static final String NOME_TAG = "message";
    private static final Integer PRECEDENCIA = 1000;

    public MessageElementTagProcessor(final String dialectPrefix) {
        super(TemplateMode.HTML, dialectPrefix, NOME_TAG, true, null, false, PRECEDENCIA);
    }

    @Override
    protected void doProcess(final ITemplateContext context, final IProcessableElementTag tag,
            final IElementTagStructureHandler structureHandler) {
        final IModelFactory modelFactory = context.getModelFactory();
        final IModel model = modelFactory.createModel();

        model.add(modelFactory.createStandaloneElementTag("th:block", "th:include", "fragments/MensagemSucesso :: alerta"));
        model.add(modelFactory.createStandaloneElementTag("th:block", "th:include", "fragments/MensagensErroValidacao :: alerta"));

        // O código ainda precisa ser processado, se fosse HTML não precisaria.
        structureHandler.replaceWith(model, true);

    }

}
