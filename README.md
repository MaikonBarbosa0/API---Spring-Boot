# API — Spring Boot

API REST desenvolvida com **Spring Boot** para gerenciamento de usuários, autenticação e operações CRUD.

O projeto foi desenvolvido como parte dos meus estudos de desenvolvimento backend com Java e tem como objetivo aplicar conceitos utilizados em aplicações REST reais.

## 🚀 Tecnologias

* Java
* Spring Boot
* Spring Web
* Spring Data JPA
* PostgreSQL
* Maven
* Git / GitHub

## 📁 Estrutura do projeto

```text
src/main/java/com/user/api
├── config
│   └── CorsConfig.java
│
├── controller
│   ├── CadUsuarioController.java
│   └── LoginController.java
│
├── dto
│   ├── CadUsuarioRequest.java
│   ├── CadUsuarioResponse.java
│   ├── LoginRequest.java
│   └── LoginResponse.java
│
├── exception
│   ├── CadUsuarioException.java
│   └── GlobalExceptionHandler.java
│
├── model
│   ├── CadUsuario.java
│   └── Login.java
│
├── repository
│   └── CadUsuarioRepository.java
│
└── service
    ├── CadUsuarioService.java
    └── LoginService.java
```

## 🔧 Funcionalidades

### Usuários

A API possui operações para gerenciamento de usuários:

* Cadastro de usuário
* Consulta de usuário
* Atualização de usuário
* Exclusão de usuário

### Login

Foi implementado um fluxo inicial de autenticação utilizando:

* `LoginRequest`
* `LoginResponse`
* `LoginService`
* `LoginController`

## 🏗️ Arquitetura

O projeto utiliza uma separação de responsabilidades baseada em camadas:

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
PostgreSQL
```

Os **DTOs** são utilizados para controlar os dados recebidos e enviados pela API, evitando expor diretamente os objetos de domínio nas requisições.

## 🌐 API

A aplicação disponibiliza endpoints REST para interação com os recursos.

Exemplo de endpoint:

```http
POST /login
```

Exemplo de requisição:

```json
{
  "login": "usuario",
  "senha": "senha"
}
```

## 🗄️ Banco de dados

O projeto utiliza **PostgreSQL** como banco de dados.

As configurações de conexão ficam no arquivo:

```text
src/main/resources/application.properties
```

> O arquivo `application.properties` não é versionado neste repositório para evitar o compartilhamento de credenciais e informações sensíveis.

Para executar o projeto, é necessário configurar as propriedades de conexão com o seu próprio banco de dados.

## ▶️ Como executar

### Pré-requisitos

Antes de executar a aplicação, tenha instalado:

* Java
* Maven
* PostgreSQL

### Clone o projeto

```bash
git clone https://github.com/MaikonBarbosa0/API---Spring-Boot.git
```

Entre na pasta:

```bash
cd API---Spring-Boot
```

Configure o banco PostgreSQL e crie o arquivo:

```text
src/main/resources/application.properties
```

com as configurações necessárias para sua instalação.

Depois execute a aplicação pelo IntelliJ ou utilizando o Maven:

```bash
./mvnw spring-boot:run
```

No Windows:

```bash
mvnw.cmd spring-boot:run
```

A API estará disponível, por padrão, em:

```text
http://localhost:8080
```

## 📌 Próximos passos

Este projeto continuará evoluindo conforme avanço nos estudos de Spring Boot e desenvolvimento backend.

Alguns dos próximos objetivos são:

* [ ] Implementar autenticação completa
* [ ] Adicionar Spring Security
* [ ] Implementar JWT
* [ ] Melhorar validações dos dados
* [ ] Criar documentação com Swagger/OpenAPI
* [ ] Adicionar mais testes automatizados
* [ ] Melhorar tratamento de exceções
* [ ] Integrar com frontend Angular

## 👨‍💻 Autor

**Maikon Barbosa**

Projeto desenvolvido para estudos e prática de desenvolvimento de APIs REST com Java e Spring Boot.
