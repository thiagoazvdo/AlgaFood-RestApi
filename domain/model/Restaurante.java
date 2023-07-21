package com.algaworks.algafood.domain.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "restaurante")
public class Restaurante {
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
	@Column(name = "nome")
	private String nome;
	
	@Column(name = "taxa_frete")
	private BigDecimal taxaFrete;
	
	@ManyToOne
//	@JoinColumn(name= "coinha_id", nullable=false)
	private Cozinha cozinha;
	
	@JsonIgnore	// ignorando as formas de pagamento por restaurante no retorno da requisicao de restaurantes
	@ManyToMany // muitos restaurantes com muitas formas de pagamento
	@JoinTable(name = "restaurante_forma_pagamento", //customizando o nome da tabela intermediaria entre as entidades formapagamento e restaurante
	joinColumns = @JoinColumn(name = "restaurante_id"), //fazendo referencia a chave primaria de restaurante
	inverseJoinColumns = @JoinColumn (name = "forma_pagamento_id"))
	private List<FormaPagamento> formasPagamento = new ArrayList<>();

}
