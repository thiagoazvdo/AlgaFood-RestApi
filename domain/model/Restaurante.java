package com.algaworks.algafood.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
	
	@JsonIgnore
	@Embedded //agregando a entidade endereco
	private Endereco endereco;
	
	@JsonIgnore
	@CreationTimestamp 	//anotacao da implementacao do hibernate que atribui a propriedade como uma data hora atual no momento do cadastro
	@Column(nullable = false, columnDefinition = "datetime") // definindo a coluna com apenas yyyyMMdd hrmins
	private LocalDateTime dataCadastro;
	
	@JsonIgnore
	@UpdateTimestamp // anotacao da implementacao do hibernate que atribui a propriedade como uma data hora atual no momento da atualizacao
	@Column(nullable = false, columnDefinition = "datetime") // definindo a coluna com apenas yyyyMMdd hrmins
	private LocalDateTime dataAtualizacao;
	
	
	@ManyToOne
//	@JoinColumn(name= "coinha_id", nullable=false)
	private Cozinha cozinha;

	@JsonIgnore
	@OneToMany(mappedBy = "restaurante")
	private List<Produto> produtos = new ArrayList<>();
	
	@JsonIgnore	// ignorando as formas de pagamento por restaurante no retorno da requisicao de restaurantes
	@ManyToMany // muitos restaurantes com muitas formas de pagamento
	@JoinTable(name = "restaurante_forma_pagamento", //customizando o nome da tabela intermediaria entre as entidades formapagamento e restaurante
	joinColumns = @JoinColumn(name = "restaurante_id"), //fazendo referencia a chave primaria de restaurante
	inverseJoinColumns = @JoinColumn (name = "forma_pagamento_id"))
	private List<FormaPagamento> formasPagamento = new ArrayList<>();

}
