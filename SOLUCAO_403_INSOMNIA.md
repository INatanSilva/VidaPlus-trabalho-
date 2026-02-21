# Solução 403 Forbidden no Insomnia

## Problema
O header `Authorization: Bearer token` está configurado, mas a variável `token` está vazia ou não está sendo resolvida.

---

## Solução Passo a Passo

### Passo 1: Verificar se o Login foi executado

1. Abra a requisição **TF07 - Login (sucesso)**
2. Clique em **Send**
3. Verifique se retorna `200 OK` com:
   ```json
   {
     "token": "eyJhbGciOiJIUzI1NiJ9..."
   }
   ```

### Passo 2: Copiar o token da resposta

1. Na resposta do **TF07**, localize o campo `token`
2. **Copie APENAS o valor** (sem as aspas, sem `{"token":`)
   - Exemplo: `eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0ZUB2aWRhLmNvbSIsInJvbGUiOiJQUk9GSVNTSU9OQUwiLCJpYXQiOjE3NzE2Nzc5MDEsImV4cCI6MTc3MTY4MTUwMX0.vtnDymlz3hmIHYe1AMd0-XBJE7iMMeJ2BhMgDnHngnU`

### Passo 3: Configurar a variável no Environment

1. **Clique no ícone de Environment** (canto superior direito, ao lado do botão Send)
   - Ou use o atalho: `Ctrl+E` (Windows/Linux) ou `Cmd+E` (Mac)

2. Selecione **VidaPlus Local** (ou o nome do seu environment)

3. Na lista de variáveis, encontre `token`

4. Na coluna **Current Value**, **cole o token** que você copiou
   - ⚠️ **IMPORTANTE:** Cole apenas o valor do token, NÃO cole `{"token": "..."}`

5. Clique em **Done** ou **Save**

### Passo 4: Verificar se a variável foi salva

1. Volte para a requisição **TF11 - Cadastrar paciente**
2. No header `Authorization`, clique no texto `token` (que está roxo)
3. Deve aparecer um popup mostrando o valor do token
4. Se aparecer vazio ou `token`, volte ao Passo 3

### Passo 5: Verificar o Environment ativo

1. No canto superior direito, verifique qual Environment está selecionado
2. Deve estar **VidaPlus Local** (ou o nome do seu environment)
3. Se estiver outro, selecione o correto

### Passo 6: Testar novamente

1. Na requisição **TF11**, clique em **Send**
2. Deve retornar `201 Created` ✅

---

## Se ainda não funcionar

### Opção A: Token direto no header (teste rápido)

1. Na requisição **TF11**, no header `Authorization`
2. Remova `Bearer {{ token }}` ou `Bearer token`
3. Cole diretamente: `Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0ZUB2aWRhLmNvbSIsInJvbGUiOiJQUk9GSVNTSU9OQUwiLCJpYXQiOjE3NzE2Nzc5MDEsImV4cCI6MTc3MTY4MTUwMX0.vtnDymlz3hmIHYe1AMd0-XBJE7iMMeJ2BhMgDnHngnU`
4. Clique em **Send**

Se funcionar, o problema é a variável. Se não funcionar, o token pode ter expirado.

### Opção B: Token expirado

1. Execute **TF07 - Login** novamente
2. Copie o **novo token** da resposta
3. Cole no Environment (Passo 3)
4. Teste novamente

---

## Checklist rápido

- [ ] TF01 executado (usuário criado)
- [ ] TF07 executado (login feito)
- [ ] Token copiado da resposta do TF07
- [ ] Token colado no Environment (variável `token`, Current Value)
- [ ] Environment correto selecionado (VidaPlus Local)
- [ ] Header `Authorization: Bearer {{ token }}` ou `Bearer token`
- [ ] TF11 executado novamente

---

## Dica: Verificar variável em tempo real

No Insomnia, você pode ver o valor da variável:
1. Clique no ícone de variável ao lado de `token` no header
2. Ou passe o mouse sobre `{{ token }}` ou `token`
3. Deve mostrar o valor real do token

Se mostrar vazio ou `token`, a variável não está configurada corretamente.
