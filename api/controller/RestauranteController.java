package com.algaworks.algafood.api.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;

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
	public Restaurante buscar(@PathVariable Long restauranteId) {
		return cadastroRestaurante.buscarOuFalhar(restauranteId);
	}
	
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Restaurante adicionar(@RequestBody Restaurante restaurante){
		return cadastroRestaurante.salvar(restaurante);
	}
	
	
	@PutMapping("/{restauranteId}")
	public Restaurante atualizar(@PathVariable Long restauranteId, @RequestBody Restaurante restaurante) {

		Restaurante restauranteAtual = cadastroRestaurante.buscarOuFalhar(restauranteId);
		BeanUtils.copyProperties(restaurante, restauranteAtual, "id", "formasPagamento", "endereco", "dataCadastro", "produtos"); 
		
		return cadastroRestaurante.salvar(restauranteAtual);
		
	}
	
	@DeleteMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long restauranteId) {
		cadastroRestaurante.excluir(restauranteId);
	}
	
//	@PatchMapping("/{restauranteId}") //usado para modificar apenas alguns dos campos dentro de um objeto
//	public Restaurante atualizarParcial(@PathVariable Long restauranteId, @RequestBody Map<String, Object> campos) throws Exception{
//		Restaurante restauranteAtual = cadastroRestaurante.buscarOuFalhar(restauranteId);
//	
//		merge(campos, restauranteAtual);
//		return atualizar(restauranteId, restauranteAtual);
//	}


	
	//o metodo merge vai ser responsavel por fazer as alteracoes parciais
	//recebe um hashmap (chave e valor) dos campos de origem e uma entidade que vai conter os valores destino
//	private void merge(Map<String, Object> camposOrigem, Optional<Restaurante> restauranteAtual) {
//		//object mapper eh o responsavel por converter objetos java -> json e visse versa
//		ObjectMapper objectMapper = new ObjectMapper();
//		//o que está sendo feito aqui eh a criacao e mapeamento de uma instancia com base nos camposOrigem seguindo uma equivalencia evitando exceptions 
//		Restaurante restauranteOrigem = objectMapper.convertValue(camposOrigem, Restaurante.class);
//		camposOrigem.forEach((nomePropriedade, valorPropriedade) -> {
//			//classe utilitaria de reflections do spring
//			//para cada campo do map de camposOrigem o campo nomePropriedade via ReflectionUtils e atribui ao field
//			Field field = ReflectionUtils.findRequiredField(Restaurante.class, nomePropriedade);
//			//atributos private nao podem ser acessados em outras classes e a propriedade setAccessible libera o acesso do campo para consultar e realizar alteracoes 
//			field.setAccessible(true);
//			try {
//				//no meu curso de especialista spring rest foi utilizada uma propriedade diferente que esta deprecated entao dessa forma consegui atingir basicamente o mesmo resultado
//				Object novoValor = FieldUtils.readField(restauranteOrigem, field.getName(), true);
//				System.out.println(nomePropriedade + " = " + valorPropriedade);
//				//propriedade que atribui o campo field no objeto de destino o valor da propriedade	
//				ReflectionUtils.setField(field, restauranteAtual, novoValor);
//			} catch (IllegalAccessException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			});
//	}
	
	
}
