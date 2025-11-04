# API de Avaliação de Filmes

## Visão Geral {#section1}
Esta é uma **API RESTful** desenvolvida em **Java** com **Spring**, meu projeto permite que usuários se cadastrem, autentiquem e crie sua própria avaliação para um determinado filme. O projeto é baseado em uma  
base de dados externa de filmes, a The Movie Database (TMDb), que serve para buscar informações sobre filmes, evitando assim que eu tenha que possuir dados em grandes quantidades armazenados na minha própria  
base de dados.

O foco principal seria: aplicar tecnologias de autenticação de usuário e relacionar usuários e avaliações, sem persistir dados redundantes de forma local, de forma a consumir menos recurso.

## Tabela de Conteúdos
- [Funcionalidades](#funcionalidades)
- [Tecnologias utilizadas (Stack)](#stack)
- [Estrutura do Banco de dados](#database)
- [Consumo da API Externa (TMDb)](#tmdb)
- [Pincipais desafios & Escolhas do design](#desafios&design)
- [Pincipais endpoints](#endpoints)
- [Clique aqui para testar](#link)

## Funcionalidades {#funcionalidades}
  - **Autenticação de usuários**
    > Sistema completo de registro e login de usuários via Spring Security.
  - **Busca de filmes**
    > Endpoints que possibilitam pesquisar filmes via nome ou id onde a busca é feita em tempo real na API do TMDb.
  - **Criação de Avaliação**
    > Usuário uma vez que for devidamente autenticado pode atribuir nota e comentário à um filme de sua escolha tendo ciência de que a avaliação ficará exposta na plataforma.
  - **Consulta de Avaliações**
    > Usuário uma vez que for devidamente autenticado pode requisitar uma lista com todas avaliações, tanto sua quanto de outros usuários.

## Tecnologias utilizadas (Stack) {#stack}
  - **Java 21**
  - **Spring Framework** no geral, mas mais especificamente:
    > **Spring Data JPA** para persistência dos dados na base de dados;
    > **Spring WebMvc** (WebClient) para realizar requisição externa HTTP e tratar os dados do corpo da requisição;
    > **Spring Security** para autenticação de usuários;
  - **PostgreSQL** para armazenar os dados dos modelos/entidades User (usuário) e MovieRating (avaliação);
  - **MapStruct** para gerar mappers de DTOs, facilitando a vida na hora da conversão de entidades e DTOs;
  - **The Movie Database (TMDb)** para recuperar dados dos filmes e manipular dentro da minha API;

## Estrutura do Banco de dados {#database}
A intenção foi evitar a redundância de dados, então por isso possuo apenas duas entidades que são persistidas localmente, e nenhuma delas armazena muitas informações sobre os filmes, confira:
  - *MovieRating* é a entidade de avaliação, ela armazena respectivamente
    > id  -   um número identificador próprio gerado automaticamente, para identificação local apenas;
    > externalId  -  o número identificador que é atribuido ao filme na API externa, usado para fazer buscas;
    > rating  -  a nota em si;
    > user  -  o usuário que fez a avaliação;
  - *User*
    > id  -  um número identificador próprio gerado automaticamente, para identificação local apenas;
    > email  -  email do usuário como forma de acesso à sua conta;
    > password  -  senha para autenticação do usuário juntamente ao email;
    > ratings  -  uma lista das avaliações feitas por esse usuário;

### Relação das entidades
O projeto aborda um relacionamento bidirecional de 1:N onde
- User @OneToMany MovieRating, ou seja, um usuário pode ter muitas avaliações e
- MovieRating @ManyToOne User, onde muitas avaliações pertencem a um único usuário
Nesta arquitetura, MovieRating é a entidade "dona" da relação (responsável pela chave estrangeira user_id). Na entidade User, a anotação @OneToMany é configurada com mappedBy = "user" para indicar ao JPA que
a relação já está mapeada pela outra entidade, evitando a criação de uma tabela de junção desnecessária.


[Voltar ao topo](#section1)
