package com.algaworks.algafood.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;

@Component
public class CozinhaRepositoryImpl implements CozinhaRepository {
	
	@PersistenceContext
	private EntityManager manager;


	@Override
	public List<Cozinha> todas() {
		return manager.createQuery("from Cozinha", Cozinha.class).getResultList(); 
																					
	}
	@Override
	public Cozinha porId(Long id) {
		return manager.find(Cozinha.class, id);
	}

	/*
	 * metodo usado para cadastrar e atualizar por isso foi definido o nome salvar
	 * pois contempla as duas classes
	 */
	@Override
	@Transactional
	public Cozinha adicionar(Cozinha cozinha) {
		return manager.merge(cozinha);
	}

	/*
	 * //precisamos fazer pois o metodo manager eh desanexado/desassociado pelo
	 * contexto de persistencia do jpa e por isso chamamos o buscar antes do remove
	 */
	@Override
	@Transactional
	public void remover(Long id) {
		Cozinha cozinha = porId(id);
		if (cozinha == null) {
			throw new EmptyResultDataAccessException(1);
		}
		manager.remove(cozinha);
		}
	}



