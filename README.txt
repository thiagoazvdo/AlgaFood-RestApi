# Minha Aplicação Spring REST

Bem-vindo(a) à Minha Aplicação Spring REST! Esta é uma aplicação de exemplo que demonstra um pouco do meu conhecimento em APIs RESTful usando o framework Spring com Java, Maven e Flyway para migração de banco de dados.

## Como executar a aplicação

Antes de executar a aplicação, certifique-se de ter instalado em sua máquina o Java (versão 10 ou superior), o Maven e o MySQL Workbench versao 8.0.32.

1. Faça o clone do repositório para a sua máquina local:

git clone https://github.com/thiagoazvdo/MinhaRestApi.git

2. Acesse o diretório do projeto

3. Compile o projeto usando o Maven:

mvn clean install

4. Execute a aplicação na IDE de sua preferencia. 


A aplicação será executada localmente em http://localhost:8080.

## Endpoints da API

A API possui os seguintes endpoints:

    PARA COZINHAS

1. `GET /cozinhas`: Collection Resource que retorna as cozinhas ja inseridas via arquivo import.sql existente neste projeto.

2. `POST /cozinhas`: Cria um nova cozinha. Envie um JSON com os dados do usuário no corpo da requisição. Exemplo : {"nome":"Mexicana"}

3. `GET /cozinhas/{id}`: Retorna os detalhes da cozinha com o ID especificado (temos apenas 4 cozinhas, de id 1 a 4).

4. `PUT /cozinhas/{id}`: Atualiza os dados da cozinha com o ID especificado. Envie um JSON com os novos dados do usuário no corpo da requisição. Exemplo: {"nome":"Japonesa"}

5. `DELETE /cozinhas/{id}`: Exclui os dados da cozinha com o ID especificado. Obs: Status 409 de Conflito, pois todas as cozinhas estao sendo utilizadas em relacionamentos com outras entidades.

    PARA RESTAURANTES ~ mesma lógica de cozinha

1. `GET /restaurantes`;

2. `POST /restaurantes`;

3. `GET /restaurantes/{id}`;

4. `PUT /restaurantes/{id}`;

5. `DELETE /restaurantes/{id}`.

    PARA CIDADES ~ mesma lógica de cozinha

1. `GET /cidades`;

2. `POST /cidades`;

3. `GET /cidades/{id}`;

4. `PUT /cidades/{id}`;

5. `DELETE /cidades/{id}`.


    PARA ESTADOS ~ mesma lógica de cozinha

1. `GET /estados`;

2. `POST /estados`;

3. `GET /estados/{id}`;

4. `PUT /estados/{id}`;

5. `DELETE /estados/{id}`.

#Utilizando o JPA através de ORM (modelo objeto relacional) foi criado o esquema de tabelas
bem como seus respectivos relacionamentos com anotações no spring como @ManyToOne, @ManyToMany,
@JoinColumn etc.

#A aplicação possui também tratamento de exceção para erros através de classes nos pacotes:
  '-api
     '- exceptionhandler
  '-domain
    '- exception

#Conta ainda com script para atualização, controle e gerenciamento do banco de dados com
a ferramenta DbMigration.

#Validação de inserção de dados do usuário através de métodos como POST e PUT com a biblioteca
@BeanValidation.

#Arquivo application.properties com propriedades como geração de log de sistema em diversos níveis,
simulação de diferentes ambientes como DEV, HOMOLOG, PROD; exibição de comandos SQL utilizados pelo JPA
entre outros.

#...

