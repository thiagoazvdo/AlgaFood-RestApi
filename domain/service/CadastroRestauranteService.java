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
		//Retorna a cozinha do Optional e caso nao exista lance essa exception
		Cozinha cozinha = cozinhaRepository.findById(cozinhaId)
				.orElseThrow(()-> new EntidadeNaoEncontradaException(
						String.format("Não existe cadastro de cozinha com o código %d", cozinhaId)));		
		
		//Se existe cozinha entao alteramos a cozinha
		restaurante.setCozinha(cozinha);
		
		return restauranteRepository.save(restaurante);
		
	}

}
