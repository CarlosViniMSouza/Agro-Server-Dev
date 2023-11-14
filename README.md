# QRMenu Server

- **Nome do Projeto:** QRMenu Server
- **Objetivo do Projeto:** Criar uma API RESTful capaz de gerenciar os `Restaurantes`, `Consumidores` e `Produtos` para criação dos `Carrinho` de compras.
- **Tecnologias:** Spring Boot Ecossystem | Docker |

### To-do

- Funcionalidades:
    - [x] Registrar um novo consumidor
    - [ ] Login de um consumidor
    - [x] Alterar um consumidor
    - [x] Deletar um consumidor

    - [x] Cadastrar novos restaurantes
    - [x] Alterar os dados um restaurante
    - [x] Excluir um restaurante

    - [x] Listar todos os produtos de um restaurante
    - [x] Listar todos os dados de um produto
    - [x] Cadastrar um produto de um restaurante
    - [x] Alterar um produto de um restaurante
    - [x] Excluir um produto de um restaurante

- Dados de Consumidor:
    - [x] Nome do consumidor
    - [x] Email do consumidor
    - [x] Senha do consumidor

- Dados de Restaurante:
    - [x] Nome do restaurante
    - [x] Email do restaurante
    - [x] Senha do restaurante

- Dados de Produto:
    - [x] Link da Imagem do produto
    - [x] Nome do produto
    - [x] Preço do produto
    - [x] Categoria do produto (ex.: Doce, Salgado, Suco)
    - [x] Descrição do produto
    - [x] Disponibilidade (Sim == True) ou (Não == False)

## Como executar o projeto

### Pré-requisitos

Antes de começar, você vai precisar ter instalado em sua máquina as seguintes ferramentas:
[Git](https://git-scm.com), [JDK 17](https://www.oracle.com/java/technologies/downloads/#java17) e um Editor de Código/IDE (preferência ao [VS Code](https://code.visualstudio.com/) e baixe as extensões recomendadas de Java e Spring Boot)

### 🧭 Executar o projeto (com o JDK local)

1. Baixe o projeto diretamente na sua máquina com o comando: 

```shell
$ git clone https://github.com/CarlosViniMSouza/QRMenu-Server.git
```

2. Para abrir o projeto no VS Code, execute 2 linhas de comando em sequência:

```shell
$ cd QRMenu-Server
$ code .
```

3. Estando no VS Code (e com as extensões para Java e Spring Boot devidamente instaladas), procure pelo arquivo `QrmenuServerApplication.java` e clique no *RUN* logo acima da função main()

### 🧭 Executar o projeto (com Docker)

1. Caso queira executar o projeto usando `Docker`, execute os seguintes comandos:

```shell
$ docker build -t qrmenu-server .
```

- Aguarde o processo de *build* terminar (geralmente demora)

2. Em seguida, rode o container construido:

```shell
$ docker run -p 8080:8080 qrmenu-server
```

### 🪼 Como rodar o Postgres usando Docker

1. Primeiro, puxe a imagem Posgres oficial da Docker Hub:

```shell
$ git pull postgres
```

2. Com isso, podemos criar & executar nosso container usando a imagem do Postgres:

```shell
$ docker run -d --name qrmenu-database -p 5432:5432 -e POSTGRES_PASSWORD=admin postgres
```

3. Com isso, podemos *start*ar nossa aplicação e realizar o CRUD com o Postgres rodando localmente

## Rotas Disponiveis

**Consumidor**

| Verbo HTTP | URL | Descrição |
|--|--|--|
| POST | [http://localhost:8080/consumers/register]() | Cadastrar novo consumidor |
| POST | [http://localhost:8080/consumers/login]() | Autenticar um consumidor |
| PUT | [http://localhost:8080/consumers/{id}/]() | Alterar os dados do consumidor |
| DELETE | [http://localhost:8080/consumers/{id}/]() | Excluir um consumidor |

**Produto**

| Verbo HTTP | URL | Descrição |
|--|--|--|
| POST | [http://localhost:8080/products/]() | Cadastrar um novo produto | (Exige Nome & Senha)
| GET | [http://localhost:8080/products/]() | Visualizar produtos do restaurante | (Exige Nome & Senha)
| PUT | [http://localhost:8080/products/{id}/]() | Alterar um produto | (Exige Nome & Senha)
| DELETE | [http://localhost:8080/consumers/{id}/]() | Excluir um produto | (Exige Nome & Senha)

**Restaurante**

| Verbo HTTP | URL | Descrição |
|--|--|--|
| POST | [http://localhost:8080/restaurants/]() | Cadastrar um novo restaurante |
| GET | [http://localhost:8080/restaurants/{id}]() | Visualizar informações do restaurante |
| GET | [http://localhost:8080/restaurants/{id}/products]() | Visualizar lista de produtos do restaurante |
| PUT | [http://localhost:8080/restaurants/{id}/]() | Alterar os dados do restaurante |
| DELETE | [http://localhost:8080/restaurants/{id}/]() | Excluir um restaurante |

## Como é o *response* de cada rota disponivel (usando Exemplos)

### Consumidor

1. (POST) [http://localhost:8080/consumers/register]()

```json
{
    "id": "1e62359e-2010-4388-ac02-6a2676c3fd47",
    "name": "UserTest02",
    "email": "emailtest02@gmail.com",
    "password": "$2a$12$Gq/5fQsbew5Oko3BBJjqde0Z0.WnuHwUjgtRl/p8jVH7y8nsl74sK"
}
```

2. (POST) [http://localhost:8080/consumers/login]()

```json
(EXPECTED) "Consumer logged"
```

3. (PUT) [http://localhost:8080/products/1e62359e-2010-4388-ac02-6a2676c3fd47]()

```json
{
    "id": "1e62359e-2010-4388-ac02-6a2676c3fd47",
    "name": "UserTest01 (Edited)",
    "email": "emailtest01edited@gmail.com",
    "password": "pwd-hashed-edited"
}
```

4. (DEL) [http://localhost:8080/consumers/1e62359e-2010-4388-ac02-6a2676c3fd47]()

```json
"Consumer deleted"
```

### Produto 

(OBS1.: Precisa do NOME e da SENHA do restaurante do cardápio)

(OBS2.: A imagem a baixo está com o campo `username` pois o APIDog não permite trocar por `nome`, mas funciona do mesmo jeito -> cheque o `FilterProductAuth.java` para mais detalhes)

![image](image.png)

1. (POST) [http://localhost:8080/products/]()

```json
{
    "id": "939c7ceb-feca-4b01-93e0-23c6cf002de1",
    "idRestaurant": "9bf0a404-797e-4863-9413-a44cdc698f0d",
    "name": "Combo X-Salada + Coca-Cola + Fritas + Kikão",
    "description": "Um combo enorme para 6 pessoas",
    "category": "Salgado",
    "linkImage": "https://raw.githubusercontent.com/CarlosViniMSouza/qrmenu-server/assets/restaurants/sample01.jpg",
    "availability": false,
    "price": 75.99
}
```

2. (GET) [http://localhost:8080/products/]()

```json
[
    {
        "id": "939c7ceb-feca-4b01-93e0-23c6cf002de1",
        "idRestaurant": "9bf0a404-797e-4863-9413-a44cdc698f0d",
        "name": "Combo X-Salada + Coca-Cola + Fritas + Kikão",
        "description": "Um combo enorme para 6 pessoas",
        "category": "Salgado",
        "linkImage": "https://raw.githubusercontent.com/CarlosViniMSouza/qrmenu-server/assets/restaurants/sample01.jpg",
        "availability": false,
        "price": 75.99
    }
]
```

3. (PUT) [http://localhost:8080/products/939c7ceb-feca-4b01-93e0-23c6cf002de1]()

```json
{
    "id": "939c7ceb-feca-4b01-93e0-23c6cf002de1",
    "idRestaurant": "9bf0a404-797e-4863-9413-a44cdc698f0d",
    "name": "Combo X-Tudo + Refri + Fritas + Kikão (PROMOÇÃO)",
    "description": "Um super combo de x-tudo com refri, kikão e fritas",
    "category": "Salgado + Diversos",
    "linkImage": "https://raw.githubusercontent.com/CarlosViniMSouza/qrmenu-server/assets/restaurants/sample01.jpg",
    "availability": true,
    "price": 65.99
}
```

4. (DEL) [http://localhost:8080/products/939c7ceb-feca-4b01-93e0-23c6cf002de1]()

```json
"Product deleted"
```

### Restaurante

1. (POST) [http://localhost:8080/restaurants/]()

```json
{
    "id": "52f7f347-afe1-4fd0-a61f-0ae2bf35b863",
    "name": "Hamburgueria ZL",
    "email": "email02@email.com",
    "password": "$2a$12$9F4/R7HTU27F33E2G34Dc.zsqjpwp7JI31w7Xwp2p87KyO0RT/u8a",
    "products": null
}
```

2. (GET) [http://localhost:8080/restaurants/52f7f347-afe1-4fd0-a61f-0ae2bf35b863]() [OBS.: COM PRODUTO ADICIONADO!!]

```json
{
    "id": "52f7f347-afe1-4fd0-a61f-0ae2bf35b863",
    "name": "Hamburgueria ZL",
    "email": "email02@email.com",
    "password": "$2a$12$9F4/R7HTU27F33E2G34Dc.zsqjpwp7JI31w7Xwp2p87KyO0RT/u8a",
    "products": [
        {
            "id": "0d8519d4-9bb9-437c-8ab8-79ffc8ffbc02",
            "idRestaurant": "52f7f347-afe1-4fd0-a61f-0ae2bf35b863",
            "name": "Combo X-Salada + Coca-Cola + Fritas + Kikão",
            "description": "Um combo enorme para 6 pessoas",
            "category": "Salgado",
            "linkImage": "https://raw.githubusercontent.com/CarlosViniMSouza/qrmenu-server/assets/restaurants/sample01.jpg",
            "availability": false,
            "price": 75.99
        }
    ]
}
```

3. (GET) [http://localhost:8080/restaurants/52f7f347-afe1-4fd0-a61f-0ae2bf35b863/products]() [OBS.: COM PRODUTO ADICIONADO!!]

```json
[
    {
        "id": "0d8519d4-9bb9-437c-8ab8-79ffc8ffbc02",
        "idRestaurant": "52f7f347-afe1-4fd0-a61f-0ae2bf35b863",
        "name": "Combo X-Salada + Coca-Cola + Fritas + Kikão",
        "description": "Um combo enorme para 6 pessoas",
        "category": "Salgado",
        "linkImage": "https://raw.githubusercontent.com/CarlosViniMSouza/qrmenu-server/assets/restaurants/sample01.jpg",
        "availability": false,
        "price": 75.99
    }
]
```

4. (PUT) [http://localhost:8080/restaurants/52f7f347-afe1-4fd0-a61f-0ae2bf35b863]() [OBS.: COM PRODUTO ADICIONADO!!]

```json
{
    "id": "52f7f347-afe1-4fd0-a61f-0ae2bf35b863",
    "name": "Espetinho & Churrascaria PQ10",
    "email": "email01test@email.com",
    "password": "owner12345",
    "products": [
        {
            "id": "0d8519d4-9bb9-437c-8ab8-79ffc8ffbc02",
            "idRestaurant": "52f7f347-afe1-4fd0-a61f-0ae2bf35b863",
            "name": "Combo X-Salada + Coca-Cola + Fritas + Kikão",
            "description": "Um combo enorme para 6 pessoas",
            "category": "Salgado",
            "linkImage": "https://raw.githubusercontent.com/CarlosViniMSouza/qrmenu-server/assets/restaurants/sample01.jpg",
            "availability": false,
            "price": 75.99
        }
    ]
}
```

5. (DEL) [http://localhost:8080/restaurants/52f7f347-afe1-4fd0-a61f-0ae2bf35b863]()

```json
"Restaurant deleted"
```

## Observações

1. Ver como se dará o prosseguimento do trabalho
