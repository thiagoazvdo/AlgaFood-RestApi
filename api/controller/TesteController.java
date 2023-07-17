package com.algaworks.algafood.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;

@RestController
@RequestMapping("/teste")
public class TesteController {
	
	//classe criada apenas para testar separadamente uma consulta por nome de cozinha e usando o like no JPQL para buscar por letra, sigla ou parte do nome
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@GetMapping("/cozinhas/buscar-por-nome")
	public List<Cozinha> buscarPorNome(@RequestHeader("nome") String nome){
		return cozinhaRepository.nome(nome);
	}
	
	@GetMapping("/cozinhas/unica/buscar-por-nome")
	public Optional<Cozinha> unicaPorNome(@RequestHeader("nome") String nome){
		return cozinhaRepository.findByNome(nome);
	}
	
	
}
