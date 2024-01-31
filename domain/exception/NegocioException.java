package com.algaworks.algafood.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


//Exception mais generalista e mais acima de nível mais acima/conceitual
//@ResponseStatus(value= HttpStatus.BAD_REQUEST) //não é mais necessário pois agora tratamos através dos métodos @ExceptionHandler na classe ApiExceptionHandler
public class NegocioException extends RuntimeException {

    private static final long seriVersionUID = 1L;

    public NegocioException(String mensagem) {
        super(mensagem);
    }

    public NegocioException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }

}
