package com.algaworks.algafood.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//@ResponseStatus(HttpStatus.CONFLICT) //não é mais necessário pois agora tratamos através dos métodos @ExceptionHandler na classe ApiExceptionHandler
public class EntidadeEmUsoException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public EntidadeEmUsoException(String mensagem) {
		super(mensagem);
	}

}
