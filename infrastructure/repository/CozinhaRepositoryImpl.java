package com.algaworks.algafood.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;

@Repository
public class CozinhaRepositoryImpl implements CozinhaRepository {
	
	@PersistenceContext
	private EntityManager manager;


	@Override
	public List<Cozinha> todas() {
		return manager.createQuery("from Cozinha", Cozinha.class).getResultList(); 
																					
	}
	
	@Override
	public List<Cozinha> porNome(String nome) {
//		createquery responsavel pelas consultas JPQL em repositorios
		return manager.createQuery("from Cozinha where nome like : nome", Cozinha.class)
				.setParameter("nome", "%" + nome + "%").getResultList();
//		: nome (parametro nomeado)
//		.setParameter recebe o nome do parametro do JPQL e o parametro que foi passado no metodo 
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



