package com.algaworks.algafood.api.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.apache.commons.lang3.reflect.FieldUtils;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {
	
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CadastroRestauranteService cadastroRestaurante;
	
	
	@GetMapping
	public List<Restaurante> listar(){
		return restauranteRepository.findAll();
	}
	
	
	//ResponseEntity eh utilizado para retornar o corpo do objeto desejado
	//PathVariable pega a variavel passada pelo usuario e utiliza como referencia na busca do objeto 
	@GetMapping("/{restauranteId}")
	public ResponseEntity<Restaurante> buscar(@PathVariable Long restauranteId) {
	 Optional<Restaurante> restaurante = restauranteRepository.findById(restauranteId);
			 
	 //validando se o id do objeto passado nao eh null, se nao for, retorna, se for, o retorno sera not found  
	 if(restaurante.isPresent()) {
		 return ResponseEntity.ok(restaurante.get());
	 }
	 return ResponseEntity.notFound().build();
	}
	
	
	//Chamando o cadastroRestauranteService simplificamos o controller uma vez que toda regra de negocio pode ser aplicada no service
	@PostMapping
	public ResponseEntity<?> adicionar(@RequestBody Restaurante restaurante){
		//O tipo do ResponseEntity seria Restaurante mas como na linha 62 quisemos retornar uma mensagem para tratar o erro colocamos o "?" pois ele possibilita qualquer tipo de retorno do metodo
		try {
			restaurante = cadastroRestaurante.salvar(restaurante);
			//Regras embutidas no service e ja acopladas ao metodo salvar da linha anterior reduzindo o acoplamento do codigo
			return ResponseEntity.status(HttpStatus.CREATED).body(restaurante);
			//ResponseEntity.status Http CREATED para retornar o sucesso da requisicao e criacao de objeto + corpo do objeto criado no caso restaurante
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
			//No caso do caminho infeliz retornamos um badRequest pois a requisicao nao pode ser aceita por um mau uso da aplicacao 
		}
	}
	
	
	@PutMapping("/{restauranteId}")
	public ResponseEntity<?> atualizar(@PathVariable Long restauranteId, @RequestBody Optional<Restaurante> restauranteAtual2) throws EntidadeNaoEncontradaException{
		//Inserindo um optional <restaurante> do id passado pelo parametro nessa variavel de classe restauranteAtual
		Optional<Restaurante> restauranteAtual = restauranteRepository.findById(restauranteId);
		try {
			//Validando se o restaurante passado por parametro existe
			if (restauranteAtual.isPresent()) {
				BeanUtils.copyProperties(restauranteAtual2, restauranteAtual.get(), "id");
				//Usando a copyProperties para pegar as propriedades do body do restaurante passado na requisicao para atualizar o restaurante
				Restaurante restauranteSalvo = cadastroRestaurante.salvar(restauranteAtual.get());
				return ResponseEntity.ok(restauranteSalvo);
				//Retornando o corpo da alteracao 
			} 
			return ResponseEntity.notFound().build();
			//Retornando 404 not found para o cenario de o id do recurso/objeto passado no endpoint nao ter sido encontrado no banco
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PatchMapping("/{restauranteId}") //usado para modificar apenas alguns dos campos dentro de um objeto
	public ResponseEntity<?> atualizarParcial(@PathVariable Long restauranteId, @RequestBody Map<String, Object> campos) throws Exception{
		Optional<Restaurante> restauranteAtual = restauranteRepository.findById(restauranteId);
		//verificando se o id do restaurante passado eh null 
		if (restauranteAtual.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		//for each com lambda onde para cada campo do Map possui um valor correspondente e imprimindo 
		//metodo extraido para resumir o patchmapping
		merge(campos, restauranteAtual);
		return atualizar(restauranteId, restauranteAtual);
	}


	
	//o metodo merge vai ser responsavel por fazer as alteracoes parciais
	//recebe um hashmap (chave e valor) dos campos de origem e uma entidade que vai conter os valores destino
	private void merge(Map<String, Object> camposOrigem, Optional<Restaurante> restauranteAtual) {
		//object mapper eh o responsavel por converter objetos java -> json e visse versa
		ObjectMapper objectMapper = new ObjectMapper();
		//o que está sendo feito aqui eh a criacao e mapeamento de uma instancia com base nos camposOrigem seguindo uma equivalencia evitando exceptions 
		Restaurante restauranteOrigem = objectMapper.convertValue(camposOrigem, Restaurante.class);
		camposOrigem.forEach((nomePropriedade, valorPropriedade) -> {
			//classe utilitaria de reflections do spring
			//para cada campo do map de camposOrigem o campo nomePropriedade via ReflectionUtils e atribui ao field
			Field field = ReflectionUtils.findRequiredField(Restaurante.class, nomePropriedade);
			//atributos private nao podem ser acessados em outras classes e a propriedade setAccessible libera o acesso do campo para consultar e realizar alteracoes 
			field.setAccessible(true);
			try {
				//no meu curso de especialista spring rest foi utilizada uma propriedade diferente que esta deprecated entao dessa forma consegui atingir basicamente o mesmo resultado
				Object novoValor = FieldUtils.readField(restauranteOrigem, field.getName(), true);
//				System.out.println(nomePropriedade + " = " + valorPropriedade);
				//propriedade que atribui o campo field no objeto de destino o valor da propriedade	
				ReflectionUtils.setField(field, restauranteAtual, novoValor);
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			});
	}
	
	
}
