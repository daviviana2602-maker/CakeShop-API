# CakeShop API

Aplicação completa de e-commerce para venda de bolos e produtos de confeitaria, desenvolvida com **Java 21 e Spring Boot**, com frontend separado utilizando **Vue.js 3, TypeScript, HTML e CSS**, consumindo integralmente a API REST desenvolvida no backend.

O projeto implementa autenticação segura, controle de acesso, gerenciamento de pedidos, carrinho de compras, cache, rate limiting, testes automatizados e pipeline de integração contínua.

---

# Tecnologias utilizadas

## Backend

* Java 21
* Spring Boot
* Spring Security
* Spring Data JPA / Hibernate
* PostgreSQL
* Redis
* Flyway
* Docker
* JUnit 5
* Mockito
* Swagger/OpenAPI

## Frontend

* Vue.js 3
* TypeScript
* HTML
* CSS
* Axios
* Vue Router
* Sistema de notificações Toast

---

# Funcionalidades

## Autenticação e usuários

* Cadastro e gerenciamento de contas
* Autenticação utilizando JWT
* Access Token e Refresh Token
* Renovação automática de sessão
* Controle de acesso baseado em roles (RBAC)
* Validação de autorização por propriedade do recurso (ownership)
* Atualização de perfil
* Verificação de email utilizando integração com API externa (Resend)
* Tokens de verificação com expiração

---

# Produtos

* Cadastro de produtos
* Edição e exclusão de produtos
* Listagem paginada
* Cache das listagens utilizando Redis
* Controle de acesso para operações administrativas

---

# Carrinho e pedidos

* Criação de pedidos
* Gerenciamento de itens do carrinho
* Adição e remoção de produtos
* Atualização de quantidade de itens
* Conclusão de pedidos
* Cancelamento de pedidos
* Controle de status dos pedidos

---

# Segurança e performance

## Rate Limiting

Implementação de limite de requisições utilizando Redis com controle por chave e expiração através de TTL.

O mecanismo reduz abuso de endpoints e protege a aplicação contra excesso de requisições.

## Cache

Utilização do Redis para cache de produtos e consultas paginadas, reduzindo chamadas desnecessárias ao banco de dados.

---

# Testes automatizados

O projeto possui **53 testes automatizados** utilizando:

* JUnit 5
* Mockito

Os testes cobrem principalmente:

* Regras de negócio
* Camada de serviços
* Validações
* Tratamento de exceções
* Fluxos de autenticação e pedidos

---

# Arquitetura

O backend segue uma arquitetura organizada em camadas:

* Controllers
* Services
* Repositories
* Entities
* DTOs
* Exceptions personalizadas

Também possui:

* Tratamento global de exceções
* Validações utilizando Bean Validation
* Migrations versionadas com Flyway
* Separação entre frontend e backend

---

# DevOps e documentação

## Docker

Ambiente de desenvolvimento utilizando containers para serviços da aplicação.

## CI/CD

Pipeline automatizado utilizando GitHub Actions para:

* Execução dos testes automatizados
* Build da aplicação
* Validação do projeto

## Documentação da API

Documentação dos endpoints utilizando Swagger/OpenAPI.

---

# Frontend

Frontend desenvolvido em Vue.js 3 com TypeScript, responsável pela interface do usuário e consumo da API REST.

Principais implementações:

* Autenticação integrada com JWT
* Controle de rotas
* Consumo da API utilizando Axios
* Tratamento de erros vindos do backend
* Notificações através de Toasts
* Gerenciamento do fluxo de produtos, carrinho e pedidos

---

# Objetivo do projeto

O objetivo do CakeShop API foi desenvolver uma aplicação próxima de um ambiente profissional, aplicando conceitos utilizados em sistemas reais, como segurança, controle de acesso, cache, testes automatizados, integração contínua e boas práticas de desenvolvimento backend.
