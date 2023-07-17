package com.algaworks.algafood.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.Cozinha;

@Repository
public interface CozinhaRepository extends JpaRepository<Cozinha, Long>{
	
	//OBS: o prefixo find pode ser substituido por read, query, get, stream etc. todos tem a mesma funcionalidade
	
	//podemos passar como nome do metodo springdatajpa apenas a propriedade que desejamos consultar, como o nome nesse exemplo 
	List<Cozinha> nome(String nome);

	//poderiamos usar tambem o findBy+atributo no nome do metodo springdatajpa exemplo:
	Optional<Cozinha> findByNome(String nome);
	
	//usando o containing no springdatajpa para filtrar similar ao like em query JPQL:
	List<Cozinha> findTodasByNomeContaining(String nome);
	
	//usando a propriedade boolean que retorna se o atributo passado por parametro existe. muito util para regra de negocio
	boolean existsByNome(String nome);
		
}
