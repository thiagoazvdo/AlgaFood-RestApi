package com.algaworks.algafood.api.exceptionhandler;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class Problema {

    private LocalDateTime dataHora;

    private String mensagem;

}
