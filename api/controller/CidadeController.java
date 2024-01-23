package com.algaworks.algafood.api.controller;

import java.util.List;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.service.CadastroCidadeService;

@RestController
@RequestMapping("/cidades")
public class CidadeController {
	
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private CadastroCidadeService cadastroCidade;
	
	
	@GetMapping
	public List<Cidade> listar(){
		return cidadeRepository.findAll();
	}
	
	
	//ResponseEntity eh utilizado para retornar o corpo do objeto desejado
	//PathVariable pega a variavel passada pelo usuario e utiliza como referencia na busca do objeto 
//	@GetMapping("/{cidadeId}")
//	public ResponseEntity<Cidade> buscar(@PathVariable Long cidadeId) {
//	 Optional<Cidade> cidade = cidadeRepository.findById(cidadeId);
//	 //validando se o id do objeto passado nao eh null, se nao for, retorna, se for, o retorno sera not found  
//	 if(cidade.isPresent()) {
//		 return ResponseEntity.ok(cidade.get());
//	 }
//	 return ResponseEntity.notFound().build();
//	}
	
	//get refatorado
	@GetMapping("/{cidadeId}")
	public Cidade buscar(@PathVariable Long cidadeId) {

		return cadastroCidade.buscarOuFalhar(cidadeId);
	}
	
	
	//Chamando o cadastroCidadeService simplificamos o controller uma vez que toda regra de negocio pode ser aplicada no service
//	@PostMapping
//	public ResponseEntity<?> adicionar(@RequestBody Cidade cidade) throws EntidadeNaoEncontradaException{
//		cidade = cadastroCidade.salvar(cidade);
//		//Regras embutidas no service e ja acopladas ao metodo salvar da linha anterior reduzindo o acoplamento do codigo
//		return ResponseEntity.status(HttpStatus.CREATED).body(cidade);
//		//ResponseEntity.status Http CREATED para retornar o sucesso da requisicao e criacao de objeto + corpo do objeto criado no caso cidade
//	}
	
	//post refatorado
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cidade adicionar(@RequestBody Cidade cidade) {
		try {
			return cadastroCidade.salvar(cidade);
		} catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}
	
	
//	@PutMapping("/{cidadeId}")
//	public ResponseEntity<?> atualizar(@PathVariable Long cidadeId, @RequestBody Cidade cidade) {
//		//Inserindo a cidade do id passado pelo parametro nessa variavel de classe cidadeAtual
//		Optional<Cidade> cidadeAtual = cidadeRepository.findById(cidadeId);
//		
//		//Validando se a cidade passada por parametro existe
//		if (cidadeAtual.isPresent()) {
//			BeanUtils.copyProperties(cidade, cidadeAtual, "id");
//			//Usando a copyProperties para pegar as propriedades do body da cidade passado na requisicao para atualizar a cidade
//			Cidade cidadeSalva = cadastroCidade.salvar(cidadeAtual.get());
//			return ResponseEntity.ok(cidadeSalva);
//			//Retornando o corpo da alteracao 
//		}
//		return ResponseEntity.notFound().build();
//	
//	}
	
	//put refatorado
	@PutMapping("/{cidadeId}")
	public Cidade atualizar(@PathVariable Long cidadeId, @RequestBody Cidade cidade) {

		try {
			Cidade cidadeAtual = cadastroCidade.buscarOuFalhar(cidadeId);

			BeanUtils.copyProperties(cidade, cidadeAtual, "id");

			return cadastroCidade.salvar(cidadeAtual);
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}
	
	@DeleteMapping("/{cidadeId}")
	public void remover(@PathVariable Long cidadeId){
		cadastroCidade.excluir(cidadeId);
	}

	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ResponseEntity<?> tratarEntidadeNaoEncontradaException(
			EntidadeNaoEncontradaException e)
	{
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	}

}
