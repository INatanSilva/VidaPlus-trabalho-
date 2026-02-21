# SGHSS Back-end (VidaPlus) — Spring Boot

Projeto de protótipo Back‑end para o Sistema de Gestão Hospitalar e de Serviços de Saúde (SGHSS), implementado em Java 17 com Spring Boot 3, JPA/Hibernate e autenticação via JWT. Banco em memória H2 para facilitar testes e demonstrações.

## Tecnologias
- `Spring Boot 3` (Web, Security, Validation, Data JPA)
- `H2 Database` (memória)
- `JJWT` para geração/validação de tokens
- `BCrypt` para hash de senhas

## Como Rodar
1. Build do projeto:
   - `./mvnw -q clean package -DskipTests`
2. Executar aplicação:
   - `java -jar target/vidaplustrabalho-0.0.1-SNAPSHOT.jar`
3. Console H2 (opcional):
   - `http://localhost:8080/h2-console`
   - JDBC: `jdbc:h2:mem:vidaplustrabalho`
   - Usuário: `sa` | Senha: vazia

## Configuração
- `src/main/resources/application.properties`
  - `spring.datasource.url=jdbc:h2:mem:vidaplustrabalho`
  - `spring.jpa.hibernate.ddl-auto=update`
  - `app.jwt.secret=change-me-super-secret-key-vidaplus`
  - `app.jwt.expiration-ms=3600000`

## Entidades Essenciais
- `UserAccount`: `id`, `email`, `senha_criptografada`, `perfil` (`ADMIN`, `PROFISSIONAL`, `PACIENTE`).
- `Paciente`: `id`, `nome`, `cpf`, `dataNascimento`, `endereco`.
- `ProfissionalSaude`: `id`, `nome`, `categoria` (`MEDICO`, `ENFERMEIRO`), `crmCoren`.
- `Prontuario`: `id`, `data`, `descricao`, relaciona `Paciente` e `ProfissionalSaude`.

## Segurança
- Senhas armazenadas com `BCrypt`.
- Autenticação via `JWT`.
- Rotas liberadas: `/auth/**`, `/login`, `/h2-console/**`.
- Demais rotas exigem `Authorization: Bearer <token>`.

## Endpoints Principais

### Autenticação
- `POST /auth/signup`
  - Objetivo: Registrar novo usuário (Admin/Profissional).
  - Corpo (JSON):
    ```json
    { "email": "pro@vida.com", "senha": "123", "perfil": "PROFISSIONAL" }
    ```
  - 200 OK: `{ "id": 1, "email": "pro@vida.com", "perfil": "PROFISSIONAL" }`

- `POST /login`
  - Objetivo: Autenticar e gerar token.
  - Corpo (JSON):
    ```json
    { "email": "pro@vida.com", "senha": "123" }
    ```
  - 200 OK: `{ "token": "eyJhbGciOi..." }`
  - 401: `{ "erro": "Credenciais inválidas" }`

### Pacientes
- `POST /pacientes` (seguro)
  - Objetivo: Cadastrar paciente.
  - Corpo (JSON Exemplo):
    ```json
    { "nome": "João Silva", "cpf": "123.3", "endereco": "Rua X, 123" }
    ```
  - 201 Created + corpo do paciente.

- `GET /pacientes/{id}` (seguro)
  - Objetivo: Buscar paciente por ID.
  - 200 OK: `{ "id": 1, "nome": "João Silva", "cpf": "123.3", "endereco": "Rua X, 123" }`
  - 404: `{ "erro": "Paciente não encontrado" }`

- `PUT /pacientes/{id}` (seguro)
  - Objetivo: Atualizar dados do paciente.
  - Corpo (JSON Exemplo):
    ```json
    { "endereco": "Rua Y, 456" }
    ```
  - 200 OK: `{ "mensagem": "Paciente atualizado com sucesso.", "id": 1 }`

## Exemplos com curl
- Criar usuário:
  - `curl -X POST http://localhost:8080/auth/signup -H 'Content-Type: application/json' -d '{"email":"pro@vida.com","senha":"123","perfil":"PROFISSIONAL"}'`
- Login:
  - `curl -X POST http://localhost:8080/login -H 'Content-Type: application/json' -d '{"email":"pro@vida.com","senha":"123"}'`
- Cadastrar paciente:
  - `curl -X POST http://localhost:8080/pacientes -H "Authorization: Bearer <TOKEN>" -H "Content-Type: application/json" -d '{"nome":"João Silva","cpf":"123.3","endereco":"Rua X, 123"}'`
- Buscar por ID:
  - `curl http://localhost:8080/pacientes/1 -H "Authorization: Bearer <TOKEN>"`
- Atualizar por ID:
  - `curl -X PUT http://localhost:8080/pacientes/1 -H "Authorization: Bearer <TOKEN>" -H "Content-Type: application/json" -d '{"endereco":"Rua Y, 456"}'`

## Plano de Testes (Resumo)
- Autenticação:
  - `POST /login` com credenciais válidas → `token`.
  - `POST /login` com credenciais inválidas → `401`.
- Pacientes:
  - `POST /pacientes` com token → `201`.
  - `GET /pacientes/{id}` sem token → `401`.
  - `PUT /pacientes/{id}` com token → mensagem de sucesso.

## Requisitos Atendidos
- Segurança/Autenticação: registro e login com JWT.
- Pacientes: CRUD essencial (criar, consultar por ID, atualizar).
- Profissionais de Saúde e Prontuários: entidades e repositórios prontos para expansão.
- Não funcionais: hash de senhas (BCrypt), autenticação baseada em perfil/token, latência adequada em H2.

## Estrutura do Código
- `controller/`: `AuthController`, `PacienteController`.
- `model/`: entidades e enums (`Role`, `CategoriaProfissional`).
- `repository/`: JPA repositories.
- `security/`: `JwtService`, `JwtAuthFilter`, `SecurityConfig`.

## Próximos Passos (opcional)
- Expor CRUD para `ProfissionalSaude` e consultas/atualizações em `Prontuario`.
- Adicionar testes de integração (Spring Security + WebMvcTest).
- Adicionar verificação de autorização por perfil (ex.: ADMIN vs PROFISSIONAL).