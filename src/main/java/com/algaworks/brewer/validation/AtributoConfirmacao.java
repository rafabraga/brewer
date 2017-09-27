package com.algaworks.brewer.validation;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.OverridesAttribute;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;

import com.algaworks.brewer.validation.validator.AtributoConfirmacaoValidator;

@Retention(RUNTIME)
@Target({ ElementType.TYPE })
@Constraint(validatedBy = { AtributoConfirmacaoValidator.class })
public @interface AtributoConfirmacao {

    @OverridesAttribute(constraint = Pattern.class, name = "message")
    String message() default "Atributos não conferem.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String atributo();

    String atributoConfirmacao();

}
