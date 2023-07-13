package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {

	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	public Restaurante salvar(Restaurante restaurante) throws EntidadeNaoEncontradaException {
		//Atribuimos ao cozinhaId o id do restaurante passado por parametro para maior legibilidade do codigo
		Long cozinhaId = restaurante.getCozinha().getId();
		Cozinha cozinha = cozinhaRepository.porId(cozinhaId);
		
		//Se cozinha for nulo entao eh lancada a exception EntidadeNaoEncontrada com a seguinte mensagem
		if (cozinha == null) {
			throw new EntidadeNaoEncontradaException(String.format("Não existe cadastro de cozinha com código %d", cozinhaId));
		}
		
		//Se existe cozinha entao alteramos a cozinha
		restaurante.setCozinha(cozinha);
		
		return restauranteRepository.adicionar(restaurante);
		
	}

}
