# Instruções para agentes de IA

## Objetivo e colaboração

Este repositório é uma API REST de estudos para gerenciamento de usuários e autenticação. Explique as mudanças em português, indicando o que mudou, por quê e como verificar. Quando o usuário pedir orientação sem execução, apenas leia e ensine; quando autorizar uma implementação, faça somente o escopo solicitado. Não execute automaticamente o roadmap inteiro.

Antes de editar, leia os arquivos envolvidos e preserve alterações existentes do usuário. Prefira mudanças pequenas e compatíveis com a organização atual. Não adicione bibliotecas ou abstrações sem necessidade concreta. Não faça commits, publique ou altere dados reais sem solicitação.

## Tecnologias e estrutura

- Java 21, Spring Boot, Maven Wrapper, PostgreSQL e Spring Data JPA.
- Spring Security, BCrypt, JWT com JJWT e Jakarta Bean Validation.
- Confira versões no `pom.xml`; não atualize dependências como efeito colateral de outra tarefa.
- Código principal: `src/main/java/com/user/api`.
- Configuração: `src/main/resources/application.properties`.
- Testes: `src/test/java/com/user/api`.

Fluxo principal:

```text
Requisição HTTP → Controller → Service → Repository → PostgreSQL
                      ↓           ↓
                  DTO de entrada  DTO de resposta → JSON
```

Responsabilidades dos pacotes:

| Pacote | Responsabilidade |
| --- | --- |
| `controller` | Rotas, validação de entrada com `@Valid`, delegação e resposta HTTP. |
| `dto` | Contratos de entrada e saída; expor apenas os campos permitidos em cada operação. |
| `service` | Regras de negócio, coordenação da persistência e conversão para DTOs. |
| `repository` | Acesso ao banco via `JpaRepository` e consultas específicas. |
| `model` | Entidades persistidas e modelos auxiliares existentes. |
| `enums` | Valores de domínio, como os perfis em `TabUsuario`. |
| `security` | Emissão, leitura e validação de JWT e filtro de autenticação. |
| `config` | Configuração de segurança, CORS e beans. |
| `exception` | Exceções de negócio e tradução para respostas HTTP. |

## Funcionamento atual

### Usuários

`CadUsuario` representa a tabela `cad_usuario`. O identificador é `Integer`, gerado pelo banco. Os campos usam o prefixo `cusu`; preserve os nomes dos contratos e das colunas existentes.

- `POST /usuario`: público; recebe `CadUsuarioRequest`, codifica a senha com `PasswordEncoder` e define obrigatoriamente `TabUsuario.USER.getId()` no service.
- `GET /usuario`: restrito a `ADMIN`; devolve uma lista de `CadUsuarioResponse`.
- `GET /usuario/{cusuId}`: exige autenticação; busca a entidade e a converte em `CadUsuarioResponse`.
- `PUT /usuario/{cusuId}`: exige autenticação; recebe `CadUsuarioUpdateRequest` com nome, login e e-mail. Preserva senha e perfil. A resposta usa `CadUsuarioResponse`.
- `DELETE /usuario/{cusuId}`: restrito a `ADMIN`; busca e exclui o registro.

Consulta e atualização por ID validam titularidade em `findUser`, antes de devolver ou modificar o registro. A regra permite o dono identificado pelo ID de `UsuarioAutenticado` ou um administrador. As restrições administrativas de listagem e exclusão continuam no `SecurityConfig`.

### Autenticação

1. `POST /login` recebe e-mail e senha em `LoginRequest`.
2. O `AuthenticationManager` autentica as credenciais. `CustomUserDetailsService` busca pelo e-mail e monta o `UserDetails` com hash e autoridades.
3. `JwtService` emite um JWT com o ID do usuário como subject e expiração atualmente definida em 15 minutos no código. O login continua recebendo e-mail e senha.
4. O cliente envia `Authorization: Bearer <token>` nas próximas requisições.
5. `JwtAuthenticationFilter` valida o token, carrega o usuário e preenche o contexto de segurança.
6. `SecurityConfig` aplica as permissões. A autenticação é stateless, sem HTTP Basic ou formulário de login.

`TabUsuario` define `ADMIN` e `USER`. Use o enum em vez de números fixos. `User.withUsername(...).roles(...)` monta autoridades com prefixo `ROLE_`; mantenha consistência com `hasRole` e com eventuais verificações de autoridades.

O segredo é lido por `JwtService` em `security.jwt.secret` e decodificado de Base64. `extrairUsuarioId` lê o ID do subject; o filtro carrega o usuário por ID e disponibiliza `UsuarioAutenticado` no contexto. Tokens antigos com e-mail no subject exigem novo login. A identidade permanece vinculada ao mesmo ID após mudança de e-mail.

### Recuperação de senha

`POST /login/reset` está liberado publicamente no `SecurityConfig`. O `LoginService` consulta a combinação de e-mail e login e, quando encontrada, chama `EmailService.enviarLinkReset`. A implementação atual escreve no console um link com `token=EXEMPLO`.

Isso é uma simulação: não há geração e persistência de tokens reais, expiração, consumo único nem confirmação para gravar uma nova senha. Não anuncie recuperação completa nem envio real de e-mail. Confira as anotações atuais antes de afirmar que `EmailServiceConsole` está limitado ao perfil `dev`.

## Padrão de implementação baseado em CadUsuarioService

Use `CadUsuarioService` como referência de organização e fluxo, com as diretrizes abaixo. As inconsistências existentes descritas ao final não são um padrão a reproduzir.

### Dependências e estilo

- Classes de negócio recebem `@Service`.
- Declare dependências como `private final` e injete pelo construtor.
- Mantenha os nomes existentes ao editar métodos; para novas operações, prefira nomes claros em português, como `listar`, `buscarPorId`, `cadastrar`, `atualizar` e `excluir`.
- Use variáveis que indiquem o papel do objeto: `request`, `usuarioExistente`, `usuarioSalvo` e `response`.
- Siga o estilo atual de classes com getters/setters e mapeamento explícito. Não introduza Lombok ou um framework de mapeamento por conveniência.
- Remova imports sem uso nos arquivos modificados e evite reformatar arquivos sem relação com a tarefa.

### Cadastro

Siga esta ordem:

1. Receber um DTO específico de cadastro, validado no controller.
2. Aplicar as regras de negócio e verificar conflitos quando previstos no escopo.
3. Criar uma nova entidade e copiar explicitamente os campos permitidos.
4. Codificar a senha com o `PasswordEncoder` injetado.
5. Definir no servidor os campos controlados pelo sistema, como perfil.
6. Salvar pelo repository.
7. Converter o registro salvo em DTO de resposta.

Nunca aceite a escolha de `ADMIN` no cadastro público nem devolva senha ou hash.

### Busca e atualização

- Centralize a busca obrigatória por ID e use `orElseThrow` com exceção específica, como no `findUser` atual.
- Carregue a entidade existente antes de atualizar; não substitua o registro por uma entidade construída diretamente a partir do JSON.
- Receba DTO específico de atualização e copie somente os campos autorizados.
- Não altere ID, senha ou perfil como efeito colateral da atualização de nome, login e e-mail.
- Para o contrato atual, nome, login e e-mail são obrigatórios. Não transforme silenciosamente o `PUT` em atualização parcial.
- Quando implementar titularidade, derive a identidade da autenticação validada; não confie em ID, e-mail ou perfil informados no body para conceder acesso. Verifique a permissão antes de modificar a entidade, inclusive antes de alterar seu e-mail.
- Use transações quando necessárias para garantir atomicidade de regras com múltiplas operações de banco; não acrescente anotações sem avaliar o fluxo.

### Respostas e erros

- Reutilize `toResponse` para mapear entidade em DTO e evitar duplicação.
- `CadUsuarioResponse` contém somente ID, nome, login e e-mail.
- Listagens devem devolver DTOs e preferir o tipo de interface `List` nos contratos.
- Para código novo, mantenha detalhes HTTP (`ResponseEntity`, status e mensagens HTTP) no controller ou no handler. Services devem devolver resultados de negócio ou lançar exceções apropriadas.
- Não retorne entidades JPA diretamente em endpoints.
- Use `GlobalExceptionHandler` para tratamento consistente. Não capture toda exceção para devolver sucesso nem exponha detalhes internos do banco.
- Ao alterar contratos HTTP, preserve compatibilidade ou explique a mudança solicitada. Não troque status ou formatos existentes sem necessidade.

### Inconsistências atuais a considerar

- `delete` ainda devolve `ResponseEntity` no service; esse acoplamento HTTP é legado e não deve ser replicado em novos services.
- `atualizar` retorna entidade internamente e o controller chama `toResponse`. A saída HTTP está protegida, mas o padrão preferido para novas operações públicas do service é retornar o DTO quando adequado.
- `insert` repete o mapeamento já disponível em `toResponse`.
- Existem dois caminhos de listagem: `listar` faz o mapeamento no service e `listRespons` usa projeção do repository. Não crie uma terceira alternativa nem remova métodos sem verificar os usos.
- O README contém descrições antigas sobre reset e itens já implementados. Para determinar o comportamento atual, confira código e verificações; atualize a documentação quando fizer parte da tarefa.

Esses pontos não autorizam uma refatoração geral. Corrija-os somente quando estiverem relacionados ao trabalho solicitado.

## Segurança e configuração

- Nunca registre senhas, hashes, segredos JWT ou tokens de acesso em logs ou documentação.
- Não copie valores sensíveis de configurações locais para respostas ou arquivos versionados. Use referências a variáveis de ambiente para credenciais.
- Não imprima arquivos de configuração sensíveis integralmente para diagnosticar um campo.
- O log do link de reset é uma simulação de desenvolvimento; ao implementar tokens reais, restrinja essa prática ao ambiente apropriado.
- Alterações em autenticação, perfis e titularidade exigem verificações de acesso permitido e negado.
- Não afrouxe a segurança para fazer um teste passar.

## Verificação e entrega

Use o Maven Wrapper no Windows:

```powershell
.\mvnw.cmd -DskipTests compile
.\mvnw.cmd test
```

Escolha verificações proporcionais à mudança. O teste atual `contextLoads` carrega o contexto Spring e pode depender da configuração de banco e JWT; não suponha que seja isolado. Para regras de negócio e segurança, prefira testes que comprovem os comportamentos relevantes, incluindo rejeições e preservação de senha/perfil quando aplicável.

A última tentativa de compilação nesta sessão foi impedida por configuração inválida de `JAVA_HOME`. Verifique o ambiente antes de concluir que há erro de código. Não altere a configuração global da máquina sem autorização. Informe separadamente verificações que passaram, falharam ou não puderam executar; nunca declare sucesso sem evidência.

Ao entregar uma mudança, explique de forma breve:

- O comportamento implementado e os arquivos principais envolvidos.
- A razão da mudança, em linguagem adequada a quem está aprendendo.
- Como foi verificada e quais limitações permanecem.
- O próximo passo pertinente, sem executá-lo automaticamente.

## Sequência de evolução prevista

1. Validar titularidade na consulta e atualização por ID, com exceção explícita para administradores.
2. Verificar regras com usuários distintos e perfis diferentes.
3. Concluir recuperação de senha com token aleatório, armazenamento seguro, expiração e uso único; criar confirmação e gravação com BCrypt.
4. Revisar tratamento de erros e documentação conforme os contratos implementados.
5. Expandir testes, documentar a API e integrar com o frontend Angular conforme solicitação.

Este planejamento orienta decisões; a solicitação atual do usuário define o escopo de cada tarefa.
