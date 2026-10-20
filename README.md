# API — Spring Boot

API REST desenvolvida com **Java 21 e Spring Boot 4.1.0** para gerenciamento de usuários, persistência em PostgreSQL e autenticação com **Spring Security e JWT**.

O projeto faz parte dos meus estudos de desenvolvimento backend e aplica conceitos de APIs REST, arquitetura em camadas, integração com banco de dados e segurança de aplicações.

## 🚀 Tecnologias

- Java 21
- Spring Boot 4.1.0
- Spring Web
- Spring Data JPA
- Spring Security
- Bean Validation
- BCrypt
- JWT com JJWT 0.12.6
- PostgreSQL
- Maven / Maven Wrapper
- Git / GitHub

## 🔧 Funcionalidades

### Gerenciamento de usuários

- Cadastro de usuários.
- Listagem e consulta por ID.
- Atualização e exclusão de usuários.
- Persistência no PostgreSQL.
- Uso de DTOs para entrada e saída de dados.

### Segurança e autenticação

- Login por e-mail e senha em `POST /login`.
- Verificação de senhas com BCrypt.
- Emissão de token JWT pelo `JwtService`.
- Validação do token pelo `JwtAuthenticationFilter`.
- Configuração **stateless** no `SecurityConfig`, sem sessão HTTP para manter a autenticação.
- Perfis `ADMIN` e `USER`, representados pelo enum `TabUsuario` e associados ao usuário por `tusu_id`.
- Restrição da listagem e da exclusão de usuários ao perfil `ADMIN`.

**O fluxo de autenticação usa JWT Bearer. As instruções antigas de HTTP Basic foram substituídas.**

## 📁 Estrutura do projeto

Organização lógica dos principais componentes. A localização de `TabUsuario` e `JwtAuthenticationFilter` pode variar conforme os pacotes adotados no código.

```text
src/main/java/com/user/api
├── config
│   ├── CorsConfig.java
│   └── SecurityConfig.java
├── controller
│   ├── CadUsuarioController.java
│   └── LoginController.java
├── dto
│   ├── CadUsuarioRequest.java
│   ├── CadUsuarioResponse.java
│   ├── LoginRequest.java
│   └── LoginResponse.java
├── exception
│   ├── CadUsuarioException.java
│   └── GlobalExceptionHandler.java
├── model
│   ├── CadUsuario.java
│   ├── Login.java
│   ├── ErrorResponse.java
│   └── TabUsuario.java              # enum de perfis
├── repository
│   └── CadUsuarioRepository.java
├── security
│   └── JwtAuthenticationFilter.java
└── service
    ├── CadUsuarioService.java
    ├── CustomUserDetailsService.java
    ├── JwtService.java
    └── LoginService.java

src/main/resources
└── application.properties
```

## 🏗️ Arquitetura

```text
Controller → Service → Repository → PostgreSQL
```

Os controllers recebem as requisições, os services concentram as regras de negócio e os repositories acessam os dados. Os DTOs controlam o conteúdo recebido e devolvido pela API.

No login, as credenciais são verificadas e o `JwtService` emite o token. Nas requisições seguintes, o `JwtAuthenticationFilter` valida o JWT e estabelece a autenticação para aquela requisição. O `SecurityConfig` aplica as permissões dos endpoints.

## 🌐 Endpoints e permissões

URL base local: `http://localhost:8080`.

| Método | Endpoint | Descrição | Acesso |
| --- | --- | --- | --- |
| POST | `/login` | Autenticar e obter JWT | Público |
| POST | `/usuario` | Cadastrar usuário | Público |
| GET | `/usuario` | Listar usuários | `ADMIN` |
| GET | `/usuario/{id}` | Consultar usuário por ID | Regra de acesso ao próprio usuário pendente |
| PUT | `/usuario/{id}` | Atualizar usuário | Regra de acesso ao próprio usuário pendente |
| DELETE | `/usuario/{id}` | Excluir usuário | `ADMIN` |

`{id}` representa o identificador do usuário, também referido como `{cusuId}` no controller.

### Limitação atual: acesso aos próprios dados

As rotas de consulta e atualização por ID ainda não possuem proteção específica para garantir que um usuário comum acesse somente os próprios dados. A exigência de autenticação nessas rotas depende da regra geral configurada no `SecurityConfig`; ela, sozinha, não verifica a titularidade do registro.

Antes de disponibilizar a API em produção, implemente a comparação entre a identidade autenticada e o usuário solicitado, com exceções explícitas para administradores. Não considere essas rotas seguras apenas porque o cliente envia um JWT.

## 🔐 Perfis de usuário

O enum `TabUsuario` representa os perfis `ADMIN` e `USER`. O campo/coluna `tusu_id` identifica o perfil associado ao usuário.

- `ADMIN`: pode listar e excluir usuários.
- `USER`: não possui essas permissões administrativas.

Confira no enum e no mapeamento da entidade os valores associados a `tusu_id`; não presuma que um número específico corresponde a um perfil. O cadastro público não deve permitir que o cliente escolha ou promova seu próprio perfil para `ADMIN`. A criação de administradores deve ocorrer por um procedimento controlado.

## 🗄️ Banco de dados e configuração

### Pré-requisitos

- JDK 21 e `JAVA_HOME` configurado.
- PostgreSQL em execução, com banco e usuário disponíveis.
- Maven Wrapper do projeto ou Maven instalado.
- Postman para os exemplos de requisição.

As configurações ficam em `src/main/resources/application.properties`. Utilize variáveis de ambiente para os valores sensíveis.

Exemplo de conexão:

```properties
spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}
```

Para o JWT, vincule a propriedade lida pelo `JwtService` à variável `JWT_SECRET`. Por exemplo, **se o serviço lê `jwt.secret`**:

```properties
jwt.secret=${JWT_SECRET}
```

O nome da propriedade precisa corresponder ao utilizado pelo código. Configure também a expiração conforme a implementação do `JwtService`.

### Variáveis de ambiente no Windows (PowerShell)

```powershell
$env:DB_URL = "jdbc:postgresql://localhost:5432/api_usuarios"
$env:DB_USERNAME = "seu_usuario_local"
$env:DB_PASSWORD = "sua_senha_local"
$env:JWT_SECRET = "substitua_por_um_segredo_aleatorio_compativel_com_o_JwtService"
```

Esses comandos definem as variáveis somente na sessão atual. Execute a aplicação na mesma janela ou configure as variáveis na execução da sua IDE.

`JWT_SECRET` deve conter um segredo aleatório forte, compatível com o algoritmo e com a forma de leitura usada pelo serviço. Se o código decodifica Base64, forneça uma chave em Base64; não use uma frase comum como chave. Não mantenha o texto de exemplo acima ao executar a aplicação.

### Cuidados com credenciais

- Não envie senhas, tokens, segredos JWT ou configurações locais com credenciais para o Git.
- Mantenha somente referências a variáveis em arquivos versionados e ignore arquivos locais sensíveis.
- Um arquivo `.env` não é carregado automaticamente pelo Spring Boot sem configuração adicional.
- Se um segredo já foi publicado, troque-o; removê-lo do arquivo atual não o remove do histórico.
- Armazene senhas com BCrypt e nunca devolva senhas ou seus hashes nas respostas.
- Use HTTPS fora do ambiente local e proteja o token: quem o possui pode utilizá-lo até sua expiração, conforme as validações implementadas.

## ▶️ Como executar

Clone o repositório e entre na pasta:

```bash
git clone https://github.com/MaikonBarbosa0/API---Spring-Boot.git
cd API---Spring-Boot
```

Crie o banco no PostgreSQL, ajuste as propriedades e defina as variáveis de ambiente descritas acima.

No Windows (PowerShell):

```powershell
.\mvnw.cmd spring-boot:run
```

No Linux/macOS:

```bash
./mvnw spring-boot:run
```

Com Maven instalado globalmente:

```bash
mvn spring-boot:run
```

A API ficará disponível em `http://localhost:8080`, caso a porta não tenha sido alterada na configuração.

## 🧪 Exemplos no Postman

Crie uma variável de ambiente `baseUrl` com o valor `http://localhost:8080`.

### 1. Cadastrar usuário

- Método: `POST`
- URL: `{{baseUrl}}/usuario`
- Authorization: **No Auth**
- Body: **raw → JSON**
- Header: `Content-Type: application/json`

```json
{
  "cusuNome": "Usuario Exemplo",
  "cusuLogin": "usuario.exemplo",
  "cusuEmail": "usuario@example.com",
  "cusuSenha": "SenhaExemplo123!"
}
```

Os dados são ilustrativos. A senha é codificada com BCrypt antes da persistência. O cadastro público não deve conceder privilégios administrativos.

### 2. Fazer login

- Método: `POST`
- URL: `{{baseUrl}}/login`
- Authorization: **No Auth**
- Body: **raw → JSON**
- Header: `Content-Type: application/json`

```json
{
  "cusuEmail": "usuario@example.com",
  "cusuSenha": "SenhaExemplo123!"
}
```

Use o e-mail e a senha de um usuário existente. O campo usado no login é `cusuEmail`, não `cusuLogin`.

Formato da resposta de sucesso:

```json
{
  "token": "<JWT gerado pela API>",
  "type": "Bearer"
}
```

O token exibido é um marcador ilustrativo. Copie o valor real retornado pela API. Não é necessário enviar um token para fazer login.

Opcionalmente, salve o token no ambiente do Postman usando um script em **Scripts → Post-response**:

```javascript
if (pm.response.code >= 200 && pm.response.code < 300) {
  const body = pm.response.json();
  if (body.token) pm.environment.set("token", body.token);
}
```

### 3. Acessar uma rota protegida

Para listar usuários, faça login com uma conta que possua o perfil `ADMIN`:

- Método: `GET`
- URL: `{{baseUrl}}/usuario`
- Authorization: **Bearer Token**
- Token: `{{token}}`

O Postman enviará:

```http
Authorization: Bearer <token>
```

Na aba **Bearer Token**, informe somente o token, sem repetir o prefixo `Bearer`. Não utilize **Basic Auth**.

Para excluir um usuário, use `DELETE {{baseUrl}}/usuario/123` com token de `ADMIN`, substituindo `123` pelo ID desejado. Essa operação remove o registro; utilize dados de teste.

### Diagnóstico rápido

- Confirme o método, a URL e o body JSON em `POST /login`.
- Verifique se a conta existe e se a senha armazenada foi codificada com BCrypt.
- Em rotas protegidas, confira o header Bearer e a validade do token.
- Um token válido não concede automaticamente o perfil `ADMIN`.
- Em geral, `401` indica falha de autenticação e `403` indica falta de permissão; os códigos e corpos efetivos dependem dos handlers configurados na API.
- Depois de alterar perfis ou a chave JWT, faça novo login quando necessário.

## 📌 Roadmap

- [x] Implementar autenticação JWT.
- [x] Criar `JwtService` e `JwtAuthenticationFilter`.
- [x] Configurar segurança stateless.
- [x] Restringir listagem e exclusão ao perfil `ADMIN`.
- [ ] Proteger consulta e atualização dos próprios dados com validação de titularidade.
- [ ] Revisar o cadastro público para impedir atribuição indevida de perfis.
- [ ] Integrar a autenticação com o frontend Angular.
- [ ] Revisar os DTOs e garantir que nenhuma resposta exponha hashes de senha.
- [ ] Melhorar e padronizar o tratamento de exceções, inclusive erros de autenticação e autorização.
- [ ] Criar testes automatizados de login, expiração, perfis e acesso aos próprios dados.
- [ ] Documentar a API com Swagger/OpenAPI.
- [ ] Definir uma estratégia de renovação e revogação de tokens, se necessária.

## 👨‍💻 Autor

**Maikon Barbosa**

Projeto desenvolvido para estudos e prática de desenvolvimento backend com Java, Spring Boot, PostgreSQL e Spring Security.
