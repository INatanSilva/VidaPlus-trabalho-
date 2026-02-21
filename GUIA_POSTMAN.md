# Guia de Testes no Postman — VidaPlus API

## Passo 1: Iniciar a aplicação

No terminal, na pasta do projeto:

```bash
java -jar target/vidaplustrabalho-0.0.1-SNAPSHOT.jar
```

Aguarde até aparecer algo como: `Started VidaplustrabalhoApplication`

---

## Passo 2: Importar a coleção no Postman

1. Abra o **Postman**
2. Clique em **Import** (canto superior esquerdo)
3. Arraste o arquivo ou clique em **Upload Files**
4. Selecione: `postman/VidaPlus_API_Testes.postman_collection.json`
5. Clique em **Import**

A coleção **"VidaPlus API - Testes Funcionais"** aparecerá na barra lateral.

---

## Passo 3: Ordem de execução dos testes

Siga esta ordem para os testes funcionarem corretamente:

### 1. Cadastrar usuário
- Abra: **1. Autenticação - Signup** → **TF01 - Cadastrar usuário (sucesso)**
- Clique em **Send**
- **Esperado:** Status `200 OK` e resposta com `id`, `email` e `perfil`

### 2. Fazer login (salvar o token)
- Abra: **2. Autenticação - Login** → **TF07 - Login (sucesso) - SALVAR TOKEN**
- Clique em **Send**
- **Esperado:** Status `200 OK` e resposta com `{"token": "eyJ..."}`
- O token é salvo automaticamente na variável da coleção

### 3. Cadastrar paciente
- Abra: **3. Pacientes - Cadastro** → **TF11 - Cadastrar paciente (sucesso)**
- Clique em **Send**
- **Esperado:** Status `201 Created` e paciente retornado com `id`

### 4. Buscar paciente
- Abra: **4. Pacientes - Consulta** → **TF17 - Buscar paciente por ID**
- Clique em **Send**
- **Esperado:** Status `200 OK` e dados do paciente

### 5. Atualizar paciente
- Abra: **5. Pacientes - Atualização** → **TF20 - Atualizar paciente (sucesso)**
- Clique em **Send**
- **Esperado:** Status `200 OK` e `{"mensagem":"Paciente atualizado com sucesso.","id":1}`

---

## Passo 4: Testes de erro (cenários negativos)

Depois do fluxo acima, execute estes para validar erros:

| Teste | O que valida | Resultado esperado |
|-------|--------------|-------------------|
| **TF03** - Email já cadastrado | Não permite email duplicado | 400 Bad Request |
| **TF04** - Sem email | Validação de campos obrigatórios | 400 Bad Request |
| **TF08** - Login senha incorreta | Credenciais inválidas | 401 Unauthorized |
| **TF13** - Cadastrar sem token | Endpoint protegido | 401 Unauthorized |
| **TF14** - Cadastrar sem CPF | Validação de CPF obrigatório | 400 Bad Request |
| **TF18** - Buscar paciente inexistente | ID não encontrado | 404 Not Found |
| **TF19** - Buscar sem token | Endpoint protegido | 401 Unauthorized |
| **TF22** - Atualizar inexistente | ID não encontrado | 404 Not Found |

---

## Erro 403 Forbidden no TF11?

O **403** significa que o token não está sendo enviado ou está vazio. Faça:

1. **Execute TF01** (Cadastrar usuário) — se já executou, pule
2. **Execute TF07** (Login) — deve retornar `{"token": "eyJ..."}`
3. **Copie o token manualmente:**
   - Na resposta do TF07, copie todo o valor de `token` (sem as aspas)
   - Clique com o botão direito na coleção → **Edit** → aba **Variables**
   - Cole o token na coluna **Current Value** da variável `token`
   - Clique em **Save**
4. **Na requisição TF11:** aba **Auth** deve estar em **No Auth** (não use Bearer Token na Auth, o token vai no header)
5. Execute **TF11** novamente

---

## Se o token não for salvo automaticamente

1. Execute **TF07 - Login**
2. Na resposta, copie o valor do campo `token` (todo o texto entre aspas)
3. Clique com o botão direito na coleção **VidaPlus API - Testes Funcionais**
4. Selecione **Edit**
5. Vá na aba **Variables**
6. Na variável `token`, cole o valor na coluna **Current Value**
7. Clique em **Save**

---

## Executar todos os testes de uma vez

1. Clique com o botão direito na coleção **VidaPlus API - Testes Funcionais**
2. Selecione **Run collection**
3. Clique em **Run VidaPlus API - Testes Funcionais**
4. Os testes serão executados em sequência (alguns podem falhar se a ordem não for a correta)

**Dica:** Para o Runner funcionar bem, execute primeiro manualmente: TF01, TF07 e TF11. Assim o token estará salvo e o paciente com ID 1 existirá.

---

## Resumo — Checklist de testes

- [ ] TF01 — Cadastrar usuário (200)
- [ ] TF07 — Login e obter token (200)
- [ ] TF11 — Cadastrar paciente (201)
- [ ] TF17 — Buscar paciente (200)
- [ ] TF20 — Atualizar paciente (200)
- [ ] TF03 — Email duplicado (400)
- [ ] TF08 — Senha incorreta (401)
- [ ] TF13 — Sem token (401)
- [ ] TF18 — Paciente inexistente (404)
