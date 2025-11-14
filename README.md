# Movie Sheriff - API de Avaliação de Filmes

## Visão Geral
Esta é uma **API RESTful** desenvolvida em **Java** com **Spring**, meu projeto permite que usuários se cadastrem, autentiquem e crie sua própria avaliação para um determinado filme. O projeto é baseado em uma base de dados externa de filmes, a The Movie Database (TMDb), que serve para buscar informações sobre filmes, evitando assim que eu tenha que possuir dados em grandes quantidades armazenados na minha própria base de dados.

O foco principal seria: aplicar tecnologias de autenticação de usuário e relacionar usuários e avaliações, sem persistir dados redundantes de forma local, de forma a consumir menos recurso.

---

## Tabela de Conteúdos
- [Funcionalidades](#funcionalidades)
- [Tecnologias utilizadas (Stack)](#tecnologias-utilizadas-stack)
- [Estrutura do Banco de dados](#estrutura-do-banco-de-dados)
- [Consumo da API Externa (TMDb)](#consumo-da-api-externa-tmdb)
- [Pincipais desafios e Escolhas do design](#pincipais-desafios-e-escolhas-do-design)

#### Clique [aqui](https://movie-sheriff.onrender.com/swagger-ui/index.html) para testar!

---

## Funcionalidades
  - **Autenticação de usuários**
    > Sistema completo de registro e login de usuários via Spring Security.
  - **Busca de filmes**
    > Endpoints que possibilitam pesquisar filmes via nome ou id onde a busca é feita em tempo real na API do TMDb.
  - **Criação de Avaliação**
    > Usuário uma vez que for devidamente autenticado pode atribuir nota e comentário à um filme de sua escolha tendo ciência de que a avaliação ficará exposta na plataforma.
  - **Consulta de Avaliações**
    > Usuário uma vez que for devidamente autenticado pode requisitar uma lista com todas avaliações, tanto sua quanto de outros usuários.

---

## Tecnologias utilizadas (Stack)
  - **Java 17**
  - **Spring 3.5**
  - **PostgreSQL** para armazenar os dados dos modelos/entidades User (usuário) e MovieRating (avaliação);
  - **MapStruct** para gerar mappers e ajudar a "traduzir" entidades em DTOs;
  - **The Movie Database (TMDb)** para recuperar dados dos filmes e manipular dentro da minha API;
  - **Spring Framework** no geral, mas mais especificamente:
    > _**Spring Data JPA**_ para persistência dos dados na base de dados; 
  
    > _**Spring WebMvc (WebClient)**_ para realizar requisição externa HTTP e tratar os dados do corpo da requisição;
    
    > _**Spring Security**_ para autenticação de usuários;
 - Além de outras ferramentas de menor expressão, como **Swagger**, **JUnit**, **Auth0**, **H2**, **Lombok** e **Docker**

---

## Estrutura do Banco de dados
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

---

### Relação das entidades
O projeto aborda um relacionamento bidirecional de 1:N onde
- User @OneToMany MovieRating, ou seja, um usuário pode ter muitas avaliações e
- MovieRating @ManyToOne User, onde muitas avaliações pertencem a um único usuário
Nesta arquitetura, MovieRating é a entidade "dona" da relação (responsável pela chave estrangeira user_id). Na entidade User, a anotação @OneToMany é configurada com mappedBy = "user" para indicar ao JPA que
a relação já está mapeada pela outra entidade, evitando a criação de uma tabela de junção desnecessária.

---

### Consumo da API Externa (TMDb)
Para fazer a recuperação dos dados dos filmes utilizei da interface do WebClient, retornando um builder do mesmo, porém passando como argumentos para o builder o URL base da requisição, adicionando ao header da requisição meu Bearer token previamente concedido pela TMDb como forma de garantir a autenticação, e então finalmente construindo meu cliente.  
Após então realizar a devida criação do meu cliente com os atributos necessários, pude usar ele para na requisição fazer buscas específicas:    
- **getMovieDetailsOnExternalAPIById()** que por natureza recebe um String id, então concatena com o URL que foi passado anteriormente no client o necessário para se buscar um filme por id, que seria "/movie/" de forma que o id fique por último e assim a requisição seja feita para um filme específico. Posteriormente há uma validação para caso não ache o filme específico uma exceção customizada seja jogada ao lado do cliente. E então, se achado o filme, ele é mapeado para uma DTO para posterior manipulação de dados.  
####
- **getMovieListOnExternalAPIByName()** que por natureza recebe um String name, então concatena com o URL que foi passado anteriormente no client o necessário para se buscar um filme pelo nome, quie seria "/search/movie?query=" de forma que o nome fique por último, o retorno seria um JSON com vários filmes que cotém a palavra no nome e então o mesmo é mapeado para uma lista que também é de DTOs para posterior manipulação.

---
 
### Pincipais desafios e Escolhas do design
Particularmente, fora o fato da persistência de usuários, a aplicação é inteira baseada em uma base de dados externa, na qual possui limite de requisições. Talvez a maior dificuldade seja a utilização de DTOs para a manipulação de dados.  
Optei pela não persistência de dados redundantes em minha base de dados, então um dos desafios foi conseguir puxar da base de dados local o id externo e com esse id externo ir na base de dados externa buscar pelo filme e seus dados, de forma a fazer o enriquecimento para posterior exposição utilizando-se tanto dos dados externos como nome e poster quanto dos dados locais como avaliação e quem fez a avaliação.

[Voltar ao topo](#visão-geral)
