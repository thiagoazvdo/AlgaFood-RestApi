package com.algaworks.algafood.api.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import com.algaworks.algafood.domain.service.CadastroEstadoService;

@RestController
@RequestMapping("/estados")
public class EstadoController {
	

	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CadastroEstadoService cadastroEstado;
	
	@GetMapping
	public List<Estado> listar(){
		return estadoRepository.listar();
	}
	
	//ResponseEntity eh utilizado para retornar o corpo do objeto desejado
	//PathVariable pega a variavel passada pelo usuario e utiliza como referencia na busca do objeto 
	@GetMapping("/{estadoId}")
	public ResponseEntity<Estado> buscar(@PathVariable Long estadoId) {
	 Estado estado = estadoRepository.buscar(estadoId);
	 //validando se o id do objeto passado nao eh null, se nao for, retorna, se for, o retorno sera not found  
	 if(estado != null) {
		 return ResponseEntity.ok(estado);
	 }
	 return ResponseEntity.notFound().build();
	}
	
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Estado adicionar(@RequestBody Estado estado) {
		return cadastroEstado.salvar(estado);
	}
	
	
	@PutMapping("/{estadoId}")
	public ResponseEntity<?> atualizar(@PathVariable Long estadoId, @RequestBody Estado estado) {
		//Inserindo o estado do id passado pelo parametro nessa variavel de classe estadoAtual
		Estado estadoAtual = estadoRepository.buscar(estadoId);
		
		//Validando se o estado passado por parametro existe
		if (estadoAtual != null) {
			BeanUtils.copyProperties(estado, estadoAtual, "id");
			//Usando a copyProperties para pegar as propriedades do body do estado passado na requisicao para atualizar o estado do id passado
			estadoAtual = cadastroEstado.salvar(estadoAtual);
			return ResponseEntity.ok(estadoAtual);
			//Retornando o corpo da alteracao 
		} 
		return ResponseEntity.notFound().build();
		//Retornando 404 not found para o cenario de o id do recurso/objeto passado no endpoint nao ter sido encontrado no banco
	}
	
	
	@DeleteMapping("/{estadoId}")
	public ResponseEntity<?> remover(@PathVariable Long estadoId) {
		try {
			cadastroEstado.excluir(estadoId);
			return ResponseEntity.noContent().build();
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		} catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}
	
}
