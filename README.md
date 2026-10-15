# AutoManager

> **Projeto AV2 Dev Web.**

## Como funciona

O **AutoManager** é um microsserviço desenvolvido com **Spring Boot** para realizar operações CRUD de clientes e seus dados relacionados.

O sistema possui quatro recursos principais:

- **Cliente**
- **Documento**
- **Endereço**
- **Telefone**

Cada cliente pode possuir:

- um endereço;
- um ou mais documentos;
- um ou mais telefones.

Os recursos podem ser cadastrados, consultados, atualizados e removidos por meio de requisições HTTP. Além das operações individuais, o cliente também pode ser atualizado **junto com** seu endereço, documentos e telefones, através de uma única requisição `PUT /cliente/{id}`.

---

## Passos para utilizar o microsserviço

### 1. Clonar o repositório

```bash
git clone https://github.com/guilhermefpo/AV1-DevWeb-III.git
```

### 2. Acessar a pasta do projeto

```bash
cd AV1-DevWeb-III/automanager
```

### 3. Executar o projeto

Requisito: **Java 17**.

**Windows:**

```bash
.\mvnw.cmd spring-boot:run
```

**Linux / macOS:**

```bash
bash mvnw spring-boot:run
```

Ou, para poder usar `./mvnw` diretamente:

```bash
chmod +x mvnw
./mvnw spring-boot:run
```

### 4. Utilizar a API

Com o microsserviço em execução, utilize as rotas abaixo para realizar as requisições.

Você pode utilizar ferramentas como **Postman**, **Insomnia** ou outra ferramenta de sua preferência.

> **Obs:** Verifique a documentação abaixo para saber quais endpoints estão disponíveis, quais métodos HTTP devem ser utilizados e quais dados devem ser enviados nas requisições.

## Níveis de maturidade REST (RMM)

O AutoManager atende aos quatro níveis do Richardson Maturity Model:

1. **Nível 0 — HTTP:** a comunicação é realizada por HTTP.
2. **Nível 1 — Recursos:** clientes, documentos, endereços e telefones possuem recursos e URLs próprios.
3. **Nível 2 — Verbos e status:** a API utiliza `GET`, `POST`, `PUT` e `DELETE`, além dos status HTTP adequados (`200`, `201`, `204`, `400`, `404` e `409`).
4. **Nível 3 — HATEOAS:** as respostas incluem links de navegação no campo `_links`, como `self`, `clientes`, `documentos`, `enderecos` e `telefones`.

Exemplo resumido de resposta HATEOAS:

```json
{
  "id": 1,
  "nome": "João da Silva",
  "cpf": "52982546727",
  "_links": {
    "self": { "href": "http://localhost:8080/cliente/1" },
    "clientes": { "href": "http://localhost:8080/cliente" }
  }
}
```

Os links permitem que o consumidor navegue pela API sem precisar montar manualmente todas as URLs.

---

## Resumo importante sobre os IDs

O significado do `{id}` na URL **muda de acordo com a operação**:

| Operação                                                   | O `{id}` da URL representa                                                                   |
| :--------------------------------------------------------- | :------------------------------------------------------------------------------------------- |
| `POST /documento/{id}`, `/endereco/{id}`, `/telefone/{id}` | O **ID do cliente** que receberá o recurso                                                   |
| `PUT` / `DELETE` de documento, endereço ou telefone        | O **ID do próprio recurso**                                                                  |
| `PUT /cliente/{id}`                                        | O **ID do cliente** — os IDs dentro do JSON identificam os recursos relacionados a atualizar |

Nos cadastros (`POST`), **não é necessário informar o `id` no corpo do JSON**. Se enviado, ele é ignorado.

---

## Documentação da API

### 1. Clientes (`/cliente`)

O cliente é o recurso principal do sistema. Documentos, endereço e telefones são associados a ele.

| Método     | Endpoint        | Status | Descrição                                    |
| :--------- | :-------------- | :----: | :------------------------------------------- |
| **GET**    | `/cliente`      | `200`  | Lista todos os clientes                      |
| **GET**    | `/cliente/{id}` | `200`  | Busca um cliente pelo ID                     |
| **POST**   | `/cliente`      | `201`  | Cadastra um novo cliente                     |
| **PUT**    | `/cliente/{id}` | `200`  | Atualiza o cliente e seus dados relacionados |
| **DELETE** | `/cliente/{id}` | `204`  | Remove um cliente                            |

<details>
<summary><b>Cadastro — POST /cliente</b></summary>

```json
{
  "nome": "João da Silva",
  "cpf": "52982546727",
  "nomeSocial": "João",
  "dataNascimento": "1990-05-15T00:00:00.000+00:00",
  "dataCadastro": "2026-08-24T00:00:00.000+00:00",
  "endereco": {
    "estado": "SP",
    "cidade": "São José dos Campos",
    "bairro": "Centro",
    "rua": "Avenida Central",
    "numero": "123",
    "codigoPostal": "12200-000"
  },
  "documentos": [{ "tipo": "RG", "numero": "123456789" }],
  "telefones": [{ "ddd": "12", "numero": "999998888" }]
}
```

Campos obrigatórios: `nome`, `cpf` (11 dígitos) e `dataCadastro`. Endereço, documentos e telefones são opcionais, mas, se enviados, os campos obrigatórios deles também são validados (veja "Regras de validação").

Não é necessário informar `id` no cadastro. Se enviado, é ignorado.

</details>

<details>
<summary><b>Atualização completa — PUT /cliente/{id}</b></summary>

O `{id}` da URL é o ID do cliente que será atualizado. Essa operação permite atualizar, em uma única requisição: dados do cliente, endereço, documentos e telefones.

```json
{
  "nome": "João da Silva Atualizado",
  "nomeSocial": "João Atualizado",
  "dataNascimento": "1991-06-20T00:00:00.000+00:00",
  "endereco": {
    "id": 2,
    "estado": "RJ",
    "cidade": "Rio de Janeiro",
    "bairro": "Copacabana",
    "rua": "Rua Nova",
    "numero": "200",
    "codigoPostal": "22000-000",
    "informacoesAdicionais": "Apto 20"
  },
  "documentos": [
    {
      "id": 3,
      "tipo": "RG",
      "numero": "987654321"
    }
  ],
  "telefones": [
    {
      "id": 2,
      "ddd": "21",
      "numero": "988888888"
    }
  ]
}
```

Regras do `PUT /cliente/{id}`:

- Todos os campos são opcionais; só os informados são alterados.
- **O CPF não pode ser alterado.** Se o `cpf` for enviado com valor diferente do atual, a API retorna `400`.
- Telefones e documentos são atualizados pelo `id` informado no JSON. O `id` deve pertencer ao cliente.

> **Importante:** no `PUT /cliente/{id}`, o `{id}` da URL identifica o cliente. Já os `id` dentro do JSON identificam os recursos relacionados que serão atualizados.

</details>

---

### 2. Documentos (`/documento`)

Os documentos são associados a um cliente. Um cliente pode possuir mais de um documento.

| Método     | Endpoint          | Status | Descrição                             |
| :--------- | :---------------- | :----: | :------------------------------------ |
| **GET**    | `/documento`      | `200`  | Lista todos os documentos             |
| **GET**    | `/documento/{id}` | `200`  | Busca um documento pelo ID            |
| **POST**   | `/documento/{id}` | `201`  | Cadastra um documento para um cliente |
| **PUT**    | `/documento/{id}` | `200`  | Atualiza um documento                 |
| **DELETE** | `/documento/{id}` | `204`  | Remove um documento                   |

<details>
<summary><b>Cadastro — POST /documento/{idCliente}</b></summary>

No cadastro, o `{id}` da URL é o **ID do cliente** que receberá o documento.

```
POST /documento/2
```

```json
{
  "tipo": "RG",
  "numero": "123456789"
}
```

Não é necessário informar o `id` do documento no JSON.

</details>

<details>
<summary><b>Atualização — PUT /documento/{idDocumento}</b></summary>

Nesse caso, o `{id}` da URL é o **ID do documento** que será atualizado.

```
PUT /documento/3
```

```json
{
  "tipo": "RG",
  "numero": "987654321"
}
```

Atualização parcial: só os campos enviados são alterados.

</details>

---

### 3. Endereços (`/endereco`)

Cada cliente possui um endereço associado.

| Método     | Endpoint         | Status | Descrição                            |
| :--------- | :--------------- | :----: | :----------------------------------- |
| **GET**    | `/endereco`      | `200`  | Lista todos os endereços             |
| **GET**    | `/endereco/{id}` | `200`  | Busca um endereço pelo ID            |
| **POST**   | `/endereco/{id}` | `201`  | Cadastra um endereço para um cliente |
| **PUT**    | `/endereco/{id}` | `200`  | Atualiza um endereço                 |
| **DELETE** | `/endereco/{id}` | `204`  | Remove um endereço                   |

<details>
<summary><b>Cadastro — POST /endereco/{idCliente}</b></summary>

O `{id}` da URL é o **ID do cliente** que receberá o endereço.

```
POST /endereco/2
```

```json
{
  "estado": "SP",
  "cidade": "São José dos Campos",
  "bairro": "Centro",
  "rua": "Avenida Central",
  "numero": "123",
  "codigoPostal": "12200-000",
  "informacoesAdicionais": "Apto 42"
}
```

Campos obrigatórios: `cidade`, `rua` e `numero`. Não é necessário informar o `id` do endereço no JSON.

</details>

<details>
<summary><b>Atualização — PUT /endereco/{idEndereco}</b></summary>

O `{id}` da URL passa a ser o **ID do endereço** que será atualizado.

```
PUT /endereco/2
```

```json
{
  "estado": "SP",
  "cidade": "São José dos Campos",
  "bairro": "Jardim das Indústrias",
  "rua": "Rua das Flores",
  "numero": "456",
  "codigoPostal": "12200-000",
  "informacoesAdicionais": "Bloco B, Apto 12"
}
```

Atualização parcial: só os campos enviados são alterados.

</details>

---

### 4. Telefones (`/telefone`)

Um cliente pode possuir mais de um telefone.

| Método     | Endpoint         | Status | Descrição                            |
| :--------- | :--------------- | :----: | :----------------------------------- |
| **GET**    | `/telefone`      | `200`  | Lista todos os telefones             |
| **GET**    | `/telefone/{id}` | `200`  | Busca um telefone pelo ID            |
| **POST**   | `/telefone/{id}` | `201`  | Cadastra um telefone para um cliente |
| **PUT**    | `/telefone/{id}` | `200`  | Atualiza um telefone                 |
| **DELETE** | `/telefone/{id}` | `204`  | Remove um telefone                   |

<details>
<summary><b>Cadastro — POST /telefone/{idCliente}</b></summary>

O `{id}` da URL é o **ID do cliente** que receberá o telefone.

```
POST /telefone/2
```

```json
{
  "ddd": "12",
  "numero": "999998888"
}
```

Não é necessário informar o `id` do telefone no JSON.

</details>

<details>
<summary><b>Atualização — PUT /telefone/{idTelefone}</b></summary>

O `{id}` da URL é o **ID do telefone** que será atualizado.

```
PUT /telefone/2
```

```json
{
  "ddd": "12",
  "numero": "988887777"
}
```

Atualização parcial: só os campos enviados são alterados. O número não pode repetir o de outro telefone já cadastrado.

</details>

---

## Regras de validação

| Recurso   | Campo                     | Regra                                                            |
| :-------- | :------------------------ | :--------------------------------------------------------------- |
| Cliente   | `nome`                    | Obrigatório                                                      |
| Cliente   | `cpf`                     | Obrigatório, exatamente 11 números, único, não pode ser alterado |
| Cliente   | `dataCadastro`            | Obrigatório no cadastro                                          |
| Endereço  | `cidade`, `rua`, `numero` | Obrigatórios                                                     |
| Endereço  | `codigoPostal`            | Opcional                                                         |
| Telefone  | `ddd`                     | Obrigatório, exatamente 2 números                                |
| Telefone  | `numero`                  | Obrigatório, único (não pode repetir, mesmo com outro DDD)       |
| Documento | `numero`                  | Obrigatório, único                                               |

## Códigos de erro

| Status | Quando acontece                                                                             |
| :----: | :------------------------------------------------------------------------------------------ |
| `400`  | Campo obrigatório ausente ou inválido, JSON inválido, ID inválido, tentativa de alterar CPF |
| `404`  | Cliente, documento, endereço ou telefone não encontrado                                     |
| `409`  | CPF, telefone ou documento já cadastrado                                                    |

---

## Fluxo básico de utilização

1. Cadastrar o cliente com `POST /cliente`.
2. Utilizar o ID retornado para cadastrar seu endereço, documentos e telefones.
3. Consultar o cliente através de `GET /cliente/{id}`.
4. Atualizar cada recurso individualmente **ou** utilizar `PUT /cliente/{id}` para atualizar o cliente e seus dados relacionados de uma vez.
5. Remover documentos, telefones ou endereço utilizando seus respectivos endpoints.
6. Remover o cliente através de `DELETE /cliente/{id}`.

> **Nota:** Certifique-se de informar corretamente os parâmetros de caminho (`{id}`) e o formato do payload em JSON no software de testes de requisições de sua preferência, como Postman, Insomnia ou Swagger.
>
> **Resumo:** nos `POST` de documento, endereço e telefone, o ID informado na URL é o **ID do cliente**. Nos `PUT` e `DELETE` desses recursos, o ID informado na URL é o **ID do próprio recurso**. No `PUT /cliente/{id}`, o ID da URL é o **ID do cliente**, enquanto os IDs presentes no JSON identificam os dados relacionados que serão atualizados.
