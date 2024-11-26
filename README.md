# Desafio Back-End, Front-End e Infraestrutura

## Descrição do Projeto

Este projeto foi criado como parte de uma avaliação técnica para a posição de **Desenvolvedor Java**. O desafio é composto por três partes:

1. **Arquitetura Back-End**: Construção de uma API para gerenciar a entidade "Produto" utilizando Java e Spring Boot.
2. **Arquitetura Front-End**: Desenvolvimento de uma interface gráfica para gerenciar os produtos utilizando ReactJS.
3. **Infraestrutura**: Implementação de infraestrutura utilizando Docker e Docker Compose, com três servidores: Front-End, Back-End e Banco de Dados.

Neste repositório, você encontrará o código necessário para rodar as três partes do projeto, usando Docker e Docker Compose para facilitar a execução em sua máquina local.

---

## Pré-requisitos

Antes de rodar o projeto, certifique-se de ter as seguintes ferramentas instaladas:

- [Docker](https://www.docker.com/get-started)
- [Docker Compose](https://docs.docker.com/compose/install/)
- [Git](https://git-scm.com/)

---

## Como Rodar o Projeto

1. **Clone o repositório**

   Primeiro, faça o clone deste repositório para sua máquina local:

   ```bash
   git clone https://github.com/GustavoSwDaniel/InventoryProject.git
   cd InventoryProject
   ```

## Configuração do Docker Compose

O Docker Compose é utilizado para orquestrar a execução dos três servidores (Front-End, Back-End e Banco de Dados). Para iniciar o ambiente de desenvolvimento, você pode utilizar o arquivo `docker-compose.yml` que já está configurado no projeto.

### Subir os containers

Execute o seguinte comando para iniciar os containers:

```bash
docker-compose up --build
```

### Acessando a aplicação

- **Back-End**: Após a execução do Docker Compose, a API estará disponível em [http://localhost:8080](http://localhost:8080).
- **Front-End**: A interface gráfica estará disponível em [http://localhost:3000](http://localhost:3000).
- **Banco de Dados (PostgreSQL)**: O banco de dados estará acessível no host `localhost` na porta `5432`.

### Parar os containers

Para parar os containers e liberar os recursos, basta rodar o seguinte comando:

```bash
docker-compose down
```

## Estrutura do Projeto

- **Back-End**: Implementação da API para gerenciamento dos produtos, utilizando **Spring Boot** e banco de dados **PostgreSQL**.
- **Front-End**: Interface gráfica feita com **ReactJS**, que consome a API do Back-End para gerenciamento de produtos.
- **Banco de Dados**: **PostgreSQL** para armazenar os dados dos produtos.

## Funcionalidades

- **Back-End**: API RESTful que permite:
  - Cadastrar um novo produto.
  - Remover um produto.
  - Listar todos os produtos cadastrados.
  - Editar os detalhes de um produto.

- **Front-End**: Interface gráfica que permite:
  - Visualizar a lista de produtos.
  - Adicionar, editar e remover produtos.
  - Visualizar os logs de auditoria
