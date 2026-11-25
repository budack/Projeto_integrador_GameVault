package com.gamevault;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
 * Divisao do trabalho:
 *
 * Danilo:
 *  - Configuracao do projeto e banco: Configuracoes iniciais
 *  - Entidades e relacionamentos: Anotacoes JPA e associacao 1:N
 *  - Controllers e CRUD: Endpoints e respostas HTTP
 *  - Login e JWT: Spring Security, tokens e filtros
 *  - Perfis ADMIN e COMUM: Controle de permissoes
 *
 * Isabella:
 *  - Services: Regras de negocio
 *  - Consultas com filtros: Consultas personalizadas
 *  - Repositories: Spring Data JPA
 *  - DTOs usando records: Estruturas para transportar dados
 *  - Testes no Postman: Validar os endpoints
 */
@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
