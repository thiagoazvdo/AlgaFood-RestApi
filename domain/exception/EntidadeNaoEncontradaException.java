package com.algaworks.algafood.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//@ResponseStatus(HttpStatus.NOT_FOUND) //não é mais necessário pois agora tratamos através dos métodos @ExceptionHandler na classe ApiExceptionHandler
public abstract class EntidadeNaoEncontradaException extends NegocioException {

	private static final long serialVersionUID = 1L;
	
	public EntidadeNaoEncontradaException (String mensagem) {
		super(mensagem);
	}	
	
}
