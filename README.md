# 🍰 CakeShop API

REST API para gerenciamento de usuários, produtos e pedidos, desenvolvida com Java e Spring Boot, aplicando conceitos de autenticação, autorização, arquitetura em camadas e boas práticas de desenvolvimento backend.

---

## 📖 Sobre o projeto

A CakeShop API simula o backend de um e-commerce de bolos, tortas e produtos artesanais.

O objetivo do projeto é praticar conceitos utilizados em aplicações reais, incluindo:

* Autenticação com JWT
* Controle de acesso baseado em papéis (RBAC)
* Controle de ownership de recursos
* Versionamento de banco de dados
* Tratamento global de exceções
* Containerização da aplicação
* Arquitetura organizada e escalável

---

## 🚀 Tecnologias

### Backend

* Java 21
* Spring Boot
* Spring Security
* Spring Data JPA (Hibernate)

### Banco de Dados

* PostgreSQL
* Flyway

### Infraestrutura

* Docker
* Docker Compose

### Documentação

* Swagger / OpenAPI

---

## 🔐 Segurança

### Autenticação

* Registro de usuários
* Login com JWT
* BCrypt para armazenamento seguro de senhas

### Autorização

* Controle de acesso baseado em Roles:

    * USER
    * ADMIN

### Ownership

* Usuários só podem acessar e modificar recursos que lhes pertencem
* Proteção contra acesso indevido a dados de terceiros

---

## 👤 Módulo de Usuários

* Cadastro de usuários e login
* Proteção de informações sensíveis

---

## 🍰 Módulo de Produtos

* Cadastro de produtos
* Consulta de produtos
* Atualização de produtos
* Exclusão de produtos

### Regras de acesso

* Apenas ADMIN pode criar, editar ou remover produtos

---

## 🛒 Módulo de Pedidos

* Criação de pedidos
* Carrinho baseado em estrutura temporária
* Associação de produtos ao pedido
* Controle de status utilizando ENUMs

### Status disponíveis

* PENDING
* CONCLUDED
* CANCELED

---

## ⚠️ Tratamento de Exceções

A aplicação possui tratamento global de erros para respostas padronizadas.

### Exemplos utilizados na aplicação

* Bad Request (400)
* Unauthorized (401)
* Forbidden (403)
* Not Found (404)
* Conflict (409)
* Erros de validação de DTOs (MethodArgumentNotValidException)

---

## 🏗️ Arquitetura

Estrutura baseada em separação de responsabilidades:

```text
src
└── main
    └── java
        ├── controllers
        ├── services
        ├── repository
        ├── entity
        ├── security
        ├── dto
        │   ├── request
        │   └── response
        ├── enums
        └── exception
```

---

## 🐳 Executando com Docker

```bash
git clone https://github.com/daviviana2602-maker/CakeShop-API.git

cd CakeShop-API

docker compose up --build
```

---

## ▶️ Executando localmente

### Pré-requisitos

* Java 21
* Maven
* PostgreSQL

### Instalação

```bash
git clone https://github.com/daviviana2602-maker/CakeShop-API.git

cd CakeShop-API

mvn clean install

mvn spring-boot:run
```

---

## 📚 Documentação da API

Após iniciar a aplicação:

```text
http://localhost:8080/swagger-ui/index.html
```

---

## 📈 Status atual

✔ Autenticação JWT

✔ Controle de acesso RBAC

✔ CRUD de Usuários

✔ CRUD de Produtos

✔ Sistema de Pedidos

✔ PostgreSQL

✔ Flyway

✔ Docker

✔ Swagger/OpenAPI

🚧 Novas funcionalidades em desenvolvimento

---

## 👨‍💻 Autor

Desenvolvido por Davi Viana como projeto de estudo e evolução profissional em desenvolvimento backend com Java e Spring Boot.
