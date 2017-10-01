package com.algaworks.brewer.controller.validator;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.algaworks.brewer.model.Venda;

@Component
public class VendaValidator implements Validator {

    @Override
    public boolean supports(final Class<?> clazz) {
        return Venda.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(final Object target, final Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "cliente.codigo", "", "Selecione um cliente na pesquisa rápida.");

        final Venda venda = (Venda) target;
        this.validarSeInformouApenasHorarioEntrega(errors, venda);
        this.validarSeInformouItens(errors, venda);
        this.validarValorTotalNegativo(errors, venda);
    }

    private void validarValorTotalNegativo(final Errors errors, final Venda venda) {
        if (venda.getValorTotal().compareTo(BigDecimal.ZERO) < 0) {
            errors.reject("", "Valor total não pode ser negativo.");
        }
    }

    private void validarSeInformouItens(final Errors errors, final Venda venda) {
        if (venda.getItens().isEmpty()) {
            errors.reject("", "Adicione pelo menos uma cerveja na venda.");
        }
    }

    private void validarSeInformouApenasHorarioEntrega(final Errors errors, final Venda venda) {
        if ((venda.getHoraEntrega() != null) && (venda.getDataEntrega() == null)) {
            errors.rejectValue("dataEntrega", "", "Informe uma data da entrega para um horário.");
        }
    }

}
