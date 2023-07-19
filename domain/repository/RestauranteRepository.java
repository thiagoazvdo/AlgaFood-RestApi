package com.algaworks.algafood.domain.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.algaworks.algafood.domain.model.Restaurante;

public interface RestauranteRepository extends JpaRepository<Restaurante, Long>, RestauranteRepositoryQueries, JpaSpecificationExecutor<Restaurante> {
	
	
	//usando JPQL customizadas com @Query ~ fazendo um bind do que receber como parametro passar na query
	@Query("from Restaurante where nome like %:nome% and cozinha.id = :id")
	List<Restaurante> consultarPorNome(@Param("nome") String nome, @Param("id") Long cozinha);
	
	//usando o findbybetween para demonstrar a possibilidade de filtrar entre os valores passados por parametro
	List<Restaurante> findByTaxaFreteBetween(BigDecimal taxaInicial, BigDecimal taxaFinal);
	
	//usando mais de uma propriedade na expressao com o and
	List<Restaurante> findByNomeContainingAndCozinhaId(String nome, Long cozinha);
	
	//podemos ainda filtrar apenas o primeiro por exemplo
	Optional<Restaurante> findFirstRestauranteByNomeContaining(String nome);
	
	//podemos tambem filtrar apenas os 5 primeiro por exemplo
	List<Restaurante> findTop2ByNomeContaining(String nome);
	
	//contando quantos restaurantes temos por uma cozinha especifica
	int countByCozinhaId(Long cozinha);

}
