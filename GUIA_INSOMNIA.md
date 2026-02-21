# Guia de Testes no Insomnia — VidaPlus API

## Passo 1: Iniciar a aplicação

No terminal, na pasta do projeto:

```bash
./mvnw spring-boot:run
```

Aguarde até aparecer: `Started VidaplustrabalhoApplication`

---

## Passo 2: Criar requisições no Insomnia

### 2.1 Criar uma Environment (Ambiente)

1. Clique em **Environments** (canto superior esquerdo)
2. Clique em **Create** → **New Environment**
3. Nome: `VidaPlus Local`
4. Adicione variáveis:
   - `baseUrl` = `http://localhost:8080`
   - `token` = (deixe vazio por enquanto)
5. Clique em **Done**

### 2.2 Criar requisições

Crie as seguintes requisições na ordem:

---

## Passo 3: Ordem de execução dos testes

### 1. TF01 - Cadastrar usuário

**Criar requisição:**
- Método: `POST`
- URL: `{{ baseUrl }}/auth/signup`
- Body (JSON):
  ```json
  {
    "email": "teste@vida.com",
    "senha": "123456",
    "perfil": "PROFISSIONAL"
  }
  ```
- Headers: `Content-Type: application/json`

**Executar:** Clique em **Send**  
**Esperado:** Status `200 OK` com `id`, `email` e `perfil`

---

### 2. TF07 - Login (OBTER TOKEN)

**Criar requisição:**
- Método: `POST`
- URL: `{{ baseUrl }}/login`
- Body (JSON):
  ```json
  {
    "email": "teste@vida.com",
    "senha": "123456"
  }
  ```
- Headers: `Content-Type: application/json`

**Executar:** Clique em **Send**  
**Esperado:** Status `200 OK` com `{"token": "eyJ..."}`

**IMPORTANTE - Salvar o token:**
1. Na resposta, copie o valor completo de `token` (todo o texto longo)
2. Vá em **Environments** → `VidaPlus Local`
3. Na variável `token`, cole o valor na coluna **Current Value**
4. Clique em **Done**

---

### 3. TF11 - Cadastrar paciente

**Criar requisição:**
- Método: `POST`
- URL: `{{ baseUrl }}/pacientes`
- Body (JSON):
  ```json
  {
    "nome": "Maria Santos",
    "cpf": "111.222.333-44",
    "dataNascimento": "1990-05-15",
    "endereco": "Rua A, 100"
  }
  ```
- Headers:
  - `Content-Type: application/json`
  - `Authorization: Bearer {{ token }}`

**Executar:** Clique em **Send**  
**Esperado:** Status `201 Created` com dados do paciente

---

### 4. TF17 - Buscar paciente por ID

**Criar requisição:**
- Método: `GET`
- URL: `{{ baseUrl }}/pacientes/1`
- Headers:
  - `Authorization: Bearer {{ token }}`

**Executar:** Clique em **Send**  
**Esperado:** Status `200 OK` com dados do paciente

---

### 5. TF20 - Atualizar paciente

**Criar requisição:**
- Método: `PUT`
- URL: `{{ baseUrl }}/pacientes/1`
- Body (JSON):
  ```json
  {
    "endereco": "Rua Nova, 200"
  }
  ```
- Headers:
  - `Content-Type: application/json`
  - `Authorization: Bearer {{ token }}`

**Executar:** Clique em **Send**  
**Esperado:** Status `200 OK` com `{"mensagem":"Paciente atualizado com sucesso.","id":1}`

---

## Passo 4: Testes de erro (cenários negativos)

### TF03 - Email já cadastrado
- Método: `POST`
- URL: `{{ baseUrl }}/auth/signup`
- Body: mesmo do TF01
- **Esperado:** 400 Bad Request

### TF08 - Login senha incorreta
- Método: `POST`
- URL: `{{ baseUrl }}/login`
- Body: `{"email":"teste@vida.com","senha":"senhaerrada"}`
- **Esperado:** 401 Unauthorized

### TF13 - Cadastrar sem token
- Método: `POST`
- URL: `{{ baseUrl }}/pacientes`
- Body: mesmo do TF11
- **Sem header Authorization**
- **Esperado:** 401 Unauthorized

### TF18 - Buscar paciente inexistente
- Método: `GET`
- URL: `{{ baseUrl }}/pacientes/9999`
- Headers: `Authorization: Bearer {{ token }}`
- **Esperado:** 404 Not Found

---

## Erro 403 Forbidden?

O **403** significa que o token está vazio ou inválido. Faça:

1. **Execute TF07** (Login) novamente
2. **Copie o token** da resposta
3. **Cole no Environment:**
   - Clique em **Environments** → `VidaPlus Local`
   - Na variável `token`, cole o valor
   - Clique em **Done**
4. **Verifique o header Authorization:**
   - Deve ser: `Bearer {{ token }}`
   - O Insomnia substitui `{{ token }}` pelo valor da variável
5. Execute a requisição novamente

---

## Dica: Usar Response Tagging (Insomnia Pro)

Se você tem Insomnia Pro, pode usar **Response Tagging** para salvar o token automaticamente:

1. Na requisição **TF07 - Login**
2. Vá em **Tests** (aba abaixo da resposta)
3. Adicione:
   ```javascript
   const response = await insomnia.response.json();
   if (response.token) {
     insomnia.environment.set('token', response.token);
   }
   ```

---

## Resumo — Checklist

- [ ] TF01 — Cadastrar usuário (200)
- [ ] TF07 — Login e **salvar token manualmente** (200)
- [ ] TF11 — Cadastrar paciente (201)
- [ ] TF17 — Buscar paciente (200)
- [ ] TF20 — Atualizar paciente (200)
- [ ] TF03 — Email duplicado (400)
- [ ] TF08 — Senha incorreta (401)
- [ ] TF13 — Sem token (401)
- [ ] TF18 — Paciente inexistente (404)
