package com.ailtonluiz.sgs.controller.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ailtonluiz.sgs.service.exception.NomeGrupoProdutosJaCadastradoException;
import com.ailtonluiz.sgs.service.exception.NomeMarcaJaCadastradoException;

@ControllerAdvice
public class ControllerAdviceExceptionHandler {

	@ExceptionHandler(NomeMarcaJaCadastradoException.class)
	public ResponseEntity<String> handleNomeMarcaJaCadastradoException(NomeMarcaJaCadastradoException e) {
		return ResponseEntity.badRequest().body(e.getMessage());

	}

	@ExceptionHandler(NomeGrupoProdutosJaCadastradoException.class)
	public ResponseEntity<String> handlerNomeGrupoProdutosJaCadastradoException(
			NomeGrupoProdutosJaCadastradoException e) {
		return ResponseEntity.badRequest().body(e.getMessage());
	}
}
