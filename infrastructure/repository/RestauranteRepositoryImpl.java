package com.algaworks.algafood.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

@Component
public class RestauranteRepositoryImpl implements RestauranteRepository {
	
	@PersistenceContext
	private EntityManager manager;

	@Override
	public List<Restaurante> todos() {
		return manager.createQuery("from Restaurante", Restaurante.class).getResultList(); // uma forma mais direta de fazer a
																					// mesma coisa das linhas anteriores
	}
	@Override
	public Restaurante porId(Long id) {
		return manager.find(Restaurante.class, id);
	}

	/*
	 * metodo usado para cadastrar e atualizar por isso foi definido o nome salvar
	 * pois contempla as duas classes
	 */
	@Transactional
	@Override
	public Restaurante adicionar(Restaurante restaurante) {
		return manager.merge(restaurante);
	}

	/*
	 * //precisamos fazer pois o metodo manager eh desanexado/desassociado pelo
	 * contexto de persistencia do jpa e por isso chamamos o buscar antes do remove
	 */
	@Transactional
	@Override
	public void remover(Restaurante restaurante) {
		restaurante = porId(restaurante.getId());
		manager.remove(restaurante);
	}

}
