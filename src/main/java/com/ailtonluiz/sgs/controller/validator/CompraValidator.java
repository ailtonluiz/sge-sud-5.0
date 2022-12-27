package com.ailtonluiz.sgs.controller.validator;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.ailtonluiz.sgs.model.Compra;

@Component
public class CompraValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Compra.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "fornecedor.codigo", "", "Seleccione un proveedor en búsqueda rápida");
		Compra compra = (Compra) target;
		validarSeInformouItens(errors, compra);
		validarValorTotalNegativo(errors, compra);
		validarSeInformouNrAlbara(errors, compra);
		validarSeInformouDataAlbara(errors, compra);
	}

	private void validarValorTotalNegativo(Errors errors, Compra compra) {
		if (compra.getValorTotal().compareTo(BigDecimal.ZERO) < 0) {
			errors.reject("", "El valor total no puede ser negativo");
		}
	}
	
	//Fecha Albara
	
	private void validarSeInformouItens(Errors errors, Compra compra) {
		if (compra.getItens().isEmpty()) {
			errors.reject("", "Añadir al menos un producto a la compra");
		}
	}
	private void validarSeInformouNrAlbara(Errors errors, Compra compra) {
		if (compra.getNrAlbara().isEmpty()) {
			errors.reject("", "Introduzca el numero del Albará");
		}
	}
	private void validarSeInformouDataAlbara(Errors errors, Compra compra) {
		if (compra.getDataAlbara() == null) {
			errors.rejectValue("dataAlbara","", "Introduzca la fecha del Albará");
		}
	}
}
