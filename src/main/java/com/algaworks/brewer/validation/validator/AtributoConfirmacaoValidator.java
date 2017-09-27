package com.algaworks.brewer.validation.validator;

import java.lang.reflect.InvocationTargetException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintValidatorContext.ConstraintViolationBuilder;

import org.apache.commons.beanutils.BeanUtils;

import com.algaworks.brewer.validation.AtributoConfirmacao;

public class AtributoConfirmacaoValidator implements ConstraintValidator<AtributoConfirmacao, Object> {

    private String atributo;
    private String atributoConfirmacao;

    @Override
    public void initialize(final AtributoConfirmacao constraintAnnotation) {
        this.atributo = constraintAnnotation.atributo();
        this.atributoConfirmacao = constraintAnnotation.atributoConfirmacao();
    }

    @Override
    public boolean isValid(final Object object, final ConstraintValidatorContext context) {
        boolean valido = false;
        try {
            final Object valorAtributo = BeanUtils.getProperty(object, this.atributo);
            final Object valorAtributoConfirmacao = BeanUtils.getProperty(object, this.atributoConfirmacao);

            valido = this.ambosSaoIguais(valorAtributo, valorAtributoConfirmacao);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException("Erro ao recuperar valores dos atributos.", e);
        }

        if (!valido) {
            context.disableDefaultConstraintViolation();
            final String mensagem = context.getDefaultConstraintMessageTemplate();
            final ConstraintViolationBuilder violationBuilder = context.buildConstraintViolationWithTemplate(mensagem);
            violationBuilder.addPropertyNode(this.atributoConfirmacao).addConstraintViolation();
        }

        return valido;
    }

    private boolean ambosSaoIguais(final Object valorAtributo, final Object valorAtributoConfirmacao) {
        return (valorAtributo != null) && valorAtributo.equals(valorAtributoConfirmacao);
    }

}
