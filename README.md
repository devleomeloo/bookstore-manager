[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=com.leonardo%3Abookstoremanager&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=com.leonardo%3Abookstoremanager) [![Build Status](https://app.travis-ci.com/devleomeloo/bookstore-manager.svg?branch=main)](https://app.travis-ci.com/devleomeloo/bookstore-manager)
<h2>Bookstore API Manager</h2>

O objetivo do projeto Bookstore API Manager é disponibilizar APIs REST para cadastro de livros, autores, editoras e usuario de uma livraria ficticia.

O projeto foi desenvolvido como base do curso completo sobre Spring Boot, publicado na Udemy em agosto de 2020:

```
https://www.udemy.com/course/spring-boot-api/
```

Para abrir a documentação (disponibilizada através do Swagger 2) de todas as operações implementadas com o padrão arquitetural REST, acesse o seguinte link abaixo:
```
https://bookstoremanager-heroku-prod.herokuapp.com/swagger-ui.html#/
```

Link do recurso para consultar usuários cadastrados no Heroku:
```
https://bookstoremanager-heroku-prod.herokuapp.com/api/v1/users
```

* OBS: Para acessar os outros endpoints, há uma pasta na raiz do projeto chamada "postman-collections", 
contendo a chamada de todos os recursos, com as environments local e produção (Heroku) para serem importadas no Postman.

As seguintes tecnologias são utilizadas como parte do desenvolvimento do projeto:

* Java 18.
* Maven 3.6.3.
* TDD - JUnit, Mockito 
* SpringSecurity - Autenticação via JWT
* Swagger 2 para a documentação de todos os endpoints desenvolvidos dentro do projeto.
* SDKMan! para o gerenciamento de múltiplas versões de Java, Maven e Spring Boot.
* Banco de dados H2 como SGBD do projeto (em ambos ambientes, Dev e Prod)
* Intellj IDEA Community Edition.
* GitHub para o armazenamento e controle de versão do projeto na nuvem.
* SonarCloud para verificaçao da qualidade de código.
* TravisCI como ferramenta de integração contínua.
* Heroku para o deploy do projeto na nuvem
* Postman para execução de testes de integração para a validação de ponta a ponta da API.