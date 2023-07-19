package com.algaworks.algafood.infrastructure.repository.spec;

import java.math.BigDecimal;

import org.springframework.data.jpa.domain.Specification;

import com.algaworks.algafood.domain.model.Restaurante;

public class RestauranteSpecs {
	
	
	public static Specification<Restaurante> comFreteGratis(){
		//expressao lambda de um metodo simplificado que passa como parametro o root, criteria e builder
		//onde o builder retorna os restaurantes que possuam a taxa frete igual a zero ou seja gratis
		return (root, criteria, builder) -> builder.equal(root.get("taxaFrete"), BigDecimal.ZERO);
	}

	public static Specification<Restaurante> comNomeSemelhante(String nome){
		//expressao lambda de um metodo simplificado que passa como parametro o root, criteria e builder
		//onde o builder retorna os restaurantes que possuam o nome com a letra, sigla ou nome passada na requisicao
		return (root, criteria, builder) -> builder.like(root.get("nome"), "%"+ nome +"%");
	}
	
	
}
