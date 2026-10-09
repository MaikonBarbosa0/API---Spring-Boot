# API — Spring Boot

API REST desenvolvida com **Java e Spring Boot** para gerenciamento de usuários, persistência de dados e autenticação com Spring Security.

O projeto faz parte dos meus estudos de desenvolvimento backend e tem como objetivo aplicar conceitos utilizados no desenvolvimento de APIs REST, integração com banco de dados e segurança de aplicações.

## 🚀 Tecnologias

* Java
* Spring Boot
* Spring Web
* Spring Data JPA
* Spring Security
* Bean Validation
* BCrypt
* PostgreSQL
* Maven
* Git / GitHub

## 📁 Estrutura do projeto

```text
src/main/java/com/user/api
├── config
│   ├── CorsConfig.java
│   └── SecurityConfig.java
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
│   └── ErrorResponse.java
│
├── repository
│   └── CadUsuarioRepository.java
│
└── service
    ├── CadUsuarioService.java
    ├── CustomUserDetailsService.java
    └── LoginService.java
```

## 🔧 Funcionalidades

### Gerenciamento de usuários

* Cadastro de usuários
* Consulta de usuários
* Consulta de usuário por ID
* Atualização de dados
* Exclusão de usuários
* Persistência dos dados utilizando PostgreSQL

### Segurança e autenticação

* Configuração do Spring Security
* Autenticação HTTP Basic
* Busca de usuários cadastrados no PostgreSQL
* Verificação de senhas com BCrypt
* Proteção de endpoints por autenticação

## 🏗️ Arquitetura

O projeto utiliza uma arquitetura em camadas para separar as responsabilidades:

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
PostgreSQL
```

O Spring Security atua na camada de segurança, interceptando as requisições e verificando a autenticação antes de permitir o acesso aos endpoints protegidos.

Os DTOs são utilizados para controlar os dados recebidos e enviados pela API.

## 🌐 Endpoints da API

A aplicação utiliza a seguinte URL base:

```text
http://localhost:8080
```

| Método | Endpoint            | Descrição                |
| ------ | ------------------- | ------------------------ |
| POST   | `/usuario`          | Cadastrar usuário        |
| GET    | `/usuario`          | Listar usuários          |
| GET    | `/usuario/{cusuId}` | Consultar usuário por ID |
| PUT    | `/usuario/{cusuId}` | Atualizar usuário        |
| DELETE | `/usuario/{cusuId}` | Excluir usuário          |

Os endpoints protegidos exigem autenticação HTTP Basic.

### Cadastro de usuário

**Requisição:** `POST /usuario`

Content-Type: `application/json`

```json
{
  "cusuNome": "Administrador",
  "cusuLogin": "admin",
  "cusuEmail": "admin@example.com",
  "cusuSenha": "123456"
}
```

A senha é codificada com BCrypt antes de ser armazenada no banco de dados.

### Autenticação

Para testar os endpoints protegidos no Postman:

1. Acesse a aba **Authorization**.
2. Selecione **Basic Auth**.
3. Informe o login e a senha de um usuário cadastrado.
4. Envie a requisição.

O Spring Security utiliza o `CustomUserDetailsService` para buscar o usuário no banco de dados e o `PasswordEncoder` para verificar a senha.

## 🗄️ Banco de dados

O projeto utiliza PostgreSQL para persistência dos usuários.

As configurações de conexão são definidas em:

```text
src/main/resources/application.properties
```

O arquivo de configuração com credenciais locais não deve ser enviado ao repositório.

Para executar a aplicação, configure a conexão com seu próprio banco de dados.

## ▶️ Como executar

### Pré-requisitos

* Java compatível com a versão do projeto
* Maven ou Maven Wrapper
* PostgreSQL

### Clonar o repositório

```bash
git clone https://github.com/MaikonBarbosa0/API---Spring-Boot.git
```

Entre na pasta do projeto:

```bash
cd API---Spring-Boot
```

Configure o PostgreSQL e crie o arquivo `application.properties` com as propriedades necessárias.

Execute a aplicação no Windows:

```bash
mvnw.cmd spring-boot:run
```

Ou, caso tenha o Maven instalado globalmente:

```bash
mvn spring-boot:run
```

A API estará disponível em:

```text
http://localhost:8080
```

## 📌 Próximos passos

* [ ] Implementar autenticação com JWT
* [ ] Criar filtro para validar tokens JWT
* [ ] Integrar a autenticação com o frontend Angular
* [ ] Melhorar as respostas da API utilizando DTOs
* [ ] Evitar a exposição de hashes de senha nas respostas
* [ ] Melhorar o tratamento de exceções
* [ ] Criar testes automatizados
* [ ] Documentar a API com Swagger/OpenAPI

## 👨‍💻 Autor

**Maikon Barbosa**

Projeto desenvolvido para estudos e prática de desenvolvimento backend com Java, Spring Boot, PostgreSQL e Spring Security.
