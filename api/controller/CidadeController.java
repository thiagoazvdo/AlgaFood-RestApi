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
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
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
		return cidadeRepository.listar();
	}
	
	
	//ResponseEntity eh utilizado para retornar o corpo do objeto desejado
	//PathVariable pega a variavel passada pelo usuario e utiliza como referencia na busca do objeto 
	@GetMapping("/{cidadeId}")
	public ResponseEntity<Cidade> buscar(@PathVariable Long cidadeId) {
	 Cidade cidade = cidadeRepository.buscar(cidadeId);
	 //validando se o id do objeto passado nao eh null, se nao for, retorna, se for, o retorno sera not found  
	 if(cidade != null) {
		 return ResponseEntity.ok(cidade);
	 }
	 return ResponseEntity.notFound().build();
	}
	
	
	//Chamando o cadastroCidadeService simplificamos o controller uma vez que toda regra de negocio pode ser aplicada no service
	@PostMapping
	public ResponseEntity<?> adicionar(@RequestBody Cidade cidade) throws EntidadeNaoEncontradaException{
		cidade = cadastroCidade.salvar(cidade);
		//Regras embutidas no service e ja acopladas ao metodo salvar da linha anterior reduzindo o acoplamento do codigo
		return ResponseEntity.status(HttpStatus.CREATED).body(cidade);
		//ResponseEntity.status Http CREATED para retornar o sucesso da requisicao e criacao de objeto + corpo do objeto criado no caso cidade
	}
	
	
	@PutMapping("/{cidadeId}")
	public ResponseEntity<?> atualizar(@PathVariable Long cidadeId, @RequestBody Cidade cidade) {
		//Inserindo a cidade do id passado pelo parametro nessa variavel de classe cidadeAtual
		Cidade cidadeAtual = cidadeRepository.buscar(cidadeId);
		
		//Validando se a cidade passada por parametro existe
		if (cidadeAtual != null) {
			BeanUtils.copyProperties(cidade, cidadeAtual, "id");
			//Usando a copyProperties para pegar as propriedades do body da cidade passado na requisicao para atualizar a cidade
			cidadeAtual = cadastroCidade.salvar(cidadeAtual);
			return ResponseEntity.ok(cidadeAtual);
			//Retornando o corpo da alteracao 
		}
		return ResponseEntity.notFound().build();
	
	}
	
	@DeleteMapping("/{cidadeId}")
	public ResponseEntity<?> remover(@PathVariable Long cidadeId){
		try {
			cadastroCidade.excluir(cidadeId);
			return ResponseEntity.noContent().build();
			//Indicativo informando a exclusao com sucesso
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		} catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}
	
}
