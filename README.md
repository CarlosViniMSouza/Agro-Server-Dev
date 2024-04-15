# Agro Server (DEV)

- **Nome do Projeto:** Agro Server (DEV)
- **Objetivo do Projeto:** Criar uma API RESTful capaz de gerenciar os `usuarios` e `noticias`.
- **Tecnologias:** Spring Boot Ecossystem | Docker |

### To-do

- Funcionalidades:
    - [x] Registrar um novo usuario
    - [x] Visualizar infos de usuario
    - [x] Alterar um usuario
    - [x] Deletar um usuario

    - [x] Registrar uma nova noticia
    - [x] Visualizar infos de noticia
    - [x] Alterar um noticia
    - [x] Deletar um noticia

- Dados de usuario:
    - [x] Nome do usuario
    - [x] Email do usuario
    - [x] Senha do usuario

- Dados da noticia:
    - [x] Titulo da noticia
    - [x] SubTitulo da noticia
    - [x] Paragrafo 1 da noticia
    - [x] Paragrafo 2 da noticia
    - [x] Paragrafo 3 da noticia

## Como executar o projeto

### Pré-requisitos

Antes de começar, você vai precisar ter instalado em sua máquina as seguintes ferramentas:
[Git](https://git-scm.com), [JDK 17](https://www.oracle.com/java/technologies/downloads/#java17) e um Editor de Código/IDE (preferência ao [VS Code](https://code.visualstudio.com/) e baixe as extensões recomendadas de Java e Spring Boot)

### 🧭 Executar o projeto (com o JDK local)

1. Baixe o projeto diretamente na sua máquina com o comando: 

```shell
$ git clone https://github.com/CarlosViniMSouza/Agro-Server-Dev.git
```

2. Para abrir o projeto no VS Code, execute 2 linhas de comando em sequência:

```shell
$ cd Agro-Server-Dev
$ code .
```

3. Estando no VS Code (e com as extensões para Java e Spring Boot devidamente instaladas), procure pelo arquivo `QrmenuServerApplication.java` e clique no *RUN* logo acima da função main()

### 🧭 Executar o projeto (com Docker)

1. Caso queira executar o projeto usando `Docker`, execute os seguintes comandos:

```shell
$ docker build -t agro-server-dev .
```

- Aguarde o processo de *build* terminar (geralmente demora)

2. Em seguida, rode o container construido:

```shell
$ docker run -p 8080:8080 agro-server-dev
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

## Rotas Disponiveis (RE-EDITAR O QUANTO ANTES!)

**Usuario**

| Verbo HTTP | URL | Descrição |
|--|--|--|
| POST | [http://localhost:8080/users/register]() | Cadastrar novo usuario |
| POST | [http://localhost:8080/users/login]() | Autenticar um usuario |
| GET | [http://localhost:8080/users/{id}/news]() | Visualizar noticias do usuario |
| GET | [http://localhost:8080/users/{id}]() | Visualizar infos do usuario |
| PUT | [http://localhost:8080/users/{id}/]() | Alterar os dados do usuario |
| DEL | [http://localhost:8080/users/{id}/]() | Excluir um usuario |

**Noticia**

| Verbo HTTP | URL | Descrição |
|--|--|--|
| POST | [http://localhost:8080/news/]() | Cadastrar uma nova Noticia |
| GET | [http://localhost:8080/news/]() | Visualizar Noticias do Usuario |
| PUT | [http://localhost:8080/news/{id}/]() | Alterar uma Noticia |
| DEL | [http://localhost:8080/news/{id}/]() | Excluir uma Noticia |

(NOTA: TODAS Exige Nome & Senha)
