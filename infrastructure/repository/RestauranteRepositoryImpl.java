package com.algaworks.algafood.infrastructure.repository;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepositoryQueries;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQueries {

	/*
	 * esse tipo de interface de implementacao eh criada com o intuido de organizar
	 * o projeto para o caso de queries mais extensas e dinamicas, com ela o spring
	 * pela anotacao @Repository e pelo sufixo Impl entende que esse metodo pode ser
	 * chamado no controller uma vez que coloquemos o metodo e sua assinatura no
	 * repository principal da entidade Restuarante. para que o java nos auxilie em
	 * tempo de compilacao podemos ainda fazer que a interface RestauranteRepository
	 * herde alem de JPARepository a RestauranteRepositoryImpl.
	 */

	@PersistenceContext
	private EntityManager manager;

	@Override
	public List<Restaurante> find(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {

		var jpql = "from Restaurante where nome like :nome " + "and taxaFrete between :taxaInicial and :taxaFinal";

		return manager.createQuery(jpql, Restaurante.class).setParameter("nome", "%" + nome + "%")
				.setParameter("taxaInicial", taxaFreteInicial).setParameter("taxaFinal", taxaFreteFinal)
				.getResultList();
	}

}
