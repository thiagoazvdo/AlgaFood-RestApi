package com.algaworks.algafood.infrastructure.repository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

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
		
		//usando o stringbuilder para concatenacao de strings
		var jpql = new StringBuilder();
		//where 0 = 0 para que seja sempre verdade e caso o usuario nao passe algum dos parametros a consulta ainda funcione
		jpql.append("from Restaurante where 0 = 0 ");

		//criando um HashMap para que dentro de cada if adicionar o parametro e o valor (caso exista) para no final usar no forEach
		var parametros = new HashMap<String, Object>();
		
		if (StringUtils.hasLength(nome)) {
			jpql.append("and nome like :nome ");
			parametros.put("nome", "%" + nome + "%");
		}
		
		if (taxaFreteInicial != null) {
			jpql.append("and taxaFrete >= :taxaInicial ");
			parametros.put("taxaInicial", taxaFreteInicial);
		}
		
		if (taxaFreteFinal != null) {
			jpql.append("and taxaFrete <= :taxaFinal ");
			parametros.put("taxaFinal", taxaFreteFinal);
		}
		
		//query do tipo typedquery - consulta tipada que retorna um restaurante
		TypedQuery<Restaurante> query = manager.createQuery(jpql.toString(), Restaurante.class);
		
		//atribuindo um a um do mapa
		parametros.forEach((chave, valor) -> query.setParameter(chave, valor));
		
		return query.getResultList();
		}
	
//	@Override
//	public List<Restaurante> find(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {
//		CriteriaBuilder builder = manager.getCriteriaBuilder();
//		
//		CriteriaQuery<Restaurante> criteria = builder.createQuery(Restaurante.class);
//		Root<Restaurante> root = criteria.from(Restaurante.class);
//		
//		ArrayList predicates = new ArrayList<Predicate>();
//		
//		if(StringUtils.hasText(nome)) {
//			predicates.add(builder.like(root.get("nome"), "%" + nome + "%"));
//		}
//		
//		if(taxaFreteInicial!= null) { 
//			predicates.add(builder.greaterThanOrEqualTo(root.get("taxaFrete"), taxaFreteInicial));
//		}
//		
//		if(taxaFreteFinal != null) {
//			predicates.add((Predicate) builder.lessThanOrEqualTo(root.get("taxaFrete"), taxaFreteFinal));
//		}
//		
//		criteria.where((javax.persistence.criteria.Predicate[]) predicates.toArray(new Predicate[0]));
//		
//		TypedQuery<Restaurante> query = manager.createQuery(criteria);
//		return query.getResultList();
//	}

}
