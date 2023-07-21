package com.algaworks.algafood.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data 	
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tab_cozinha")
public class Cozinha {
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "nom_cozinha")
	private String nome;
	
	//caso queira que a cozinha tenha acesso a muitos restaurantes devemos fazer da seguinte forma
//	@JsonIgnore // -> evita que um get de cozinhas fique em loop infinito carregando restaurante e cozinhas inumeras vezes
//	@OneToMany(mappedBy= "cozinha") //mappedBy -> propriedade que foi usada para o mapeamento na entidade Restaurante
//	private List<Restaurante> restaurantes = new ArrayList<>();
	
}
