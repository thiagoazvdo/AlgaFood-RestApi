package com.algaworks.algafood.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.Cozinha;

@Repository
public interface CozinhaRepository extends JpaRepository<Cozinha, Long>{
	
	//podemos passar como nome do metodo springdatajpa apenas a propriedade que desejamos consultar, como o nome nesse exemplo 
	List<Cozinha> nome(String nome);

	//poderiamos usar tambem o findBy+atributo no nome do metodo springdatajpa exemplo:
	Optional<Cozinha> findByNome(String nome);

}
