# Hotelaria

Projeto feito em **Spring Boot** para representar um sistema simples de hotelaria

## Requisitos

- [JDK 21](https://adoptium.net/) instalado (`java -version`)
- [Maven](https://maven.apache.org/) instalado (`mvn -version`)

## Como rodar

Clonar o repositório e entrar na pasta:

bash

git clone https://github.com/gabriel-liz/hotelaria.git

cd hotelaria

## Para rodar o projeto direto no maven

docker-compose up -d

mvn spring-boot:run

## Para rodar os testes:

mvn test

## Caso deseje acessar o banco:

PgAdmin: http://localhost:8083/browser/

Dados para acesso:

User: dba@hotel.com

Password: hotel

## Para acessar a documentação e testar:

Documentação Swagger: http://localhost:8080/swagger-ui/index.html#

Documentação em JSON: http://localhost:8080/v3/api-docs

Coleção com os end-Points no Postman no arquivo: **Hotelaria.postman_collection**
