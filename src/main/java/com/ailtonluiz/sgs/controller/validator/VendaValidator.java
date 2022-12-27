package com.ailtonluiz.sgs.controller.validator;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.ailtonluiz.sgs.model.Venda;

@Component
public class VendaValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Venda.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "cliente.codigo", "", "Seleccione un cliente en búsqueda rápida");
		Venda venda = (Venda) target;
		validarSeInformouItens(errors, venda);
		validarValorTotalNegativo(errors, venda);
	}

	private void validarValorTotalNegativo(Errors errors, Venda venda) {
		if (venda.getValorTotal().compareTo(BigDecimal.ZERO) < 0) {
			errors.reject("", "El valor total no puede ser negativo");
		}
	}
	
	private void validarSeInformouItens(Errors errors, Venda venda) {
		if (venda.getItens().isEmpty()) {
			errors.reject("", "Añadir al menos un producto a la venta");
		}
	}
}
