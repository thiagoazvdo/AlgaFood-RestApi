package com.algaworks.algafood.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.sun.istack.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
	
	@NotNull	//validação agora no código e não no banco ou seja no JPA
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
	
//	@JsonIgnore
	@JsonIgnoreProperties("hibernateLazyInitializer") //ignora a serializacao para evitar o erro de proxy ao realizar uma busca de restaurante retornando a cozinha associada ao mesmo
	@ManyToOne(fetch = FetchType.LAZY)	//relacionamentos terminados em ToOne seguem o padrao eager loading (carregamento ansioso)e por padrao pode ser feito em 1 unico select ou e vario, como solucao podemos mudar o fetch padrao eager para lazy que nesse caso otimiza a consulta do jpa
	@JoinColumn(name= "cozinha_id", nullable=false)
	private Cozinha cozinha;

	@JsonIgnore
	@OneToMany(mappedBy = "restaurante")
	private List<Produto> produtos = new ArrayList<>();
	
	
	@JsonIgnore	// ignorando as formas de pagamento por restaurante no retorno da requisicao de restaurantes
	@ManyToMany // muitos restaurantes com muitas formas de pagamento (o padrao para relacionamentos terminados em ToMany eh lazy loading dessa forma as consultas sao otimizadas e so serao realiza-las pelo hibernate quando for extremamente necessario)
	@JoinTable(name = "restaurante_forma_pagamento", //customizando o nome da tabela intermediaria entre as entidades formapagamento e restaurante
	joinColumns = @JoinColumn(name = "restaurante_id"), //fazendo referencia a chave primaria de restaurante
	inverseJoinColumns = @JoinColumn (name = "forma_pagamento_id"))
	private List<FormaPagamento> formasPagamento = new ArrayList<>();

}
