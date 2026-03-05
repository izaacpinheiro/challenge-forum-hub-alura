# Challenge ForumHUB 💻
o ForumHUB é uma API Rest baseada no fórum da Alura, local onde os estudantes da Alura podem postar suas dúvidas sobre algum curso ou assunto e são ajudados pelos outros estudantes.
Essa API é parte da Trilha Spring Boot do programa ONE da ALura e é a tentativa de criar um CRUD básico de simulação dessa premissa apresentada.

## 🛠️ Tecnologias Utilizadas

- **Java**
- **Spring Boot**
  - Spring Data JPA
  - Spring Security + JWT
- **MySQL**
- **Flyway**
- **Validation**
- **Maven**

## 📃 Funcionalidades

### Autenticação
- Registro de usuários com senha criptografada
- Login com token JWT

### CRUD
- Criação de tópicos
- Listar tópicos
- Buscar tópico por ID
- Atualizar tópico
- Deletar tópico
> Requisições protegidas com validação de token JWT

## 📃 Pré-Requisitos
- Java JDK 21
- Maven 4+
- MySQL Server

## ⚙️ Como Rodar o Projeto

### 1️⃣ Clone o repositório
```bash
git clone https://github.com/izaacpinheiro/challenge-forum-hub-alura.git
```

### 2️⃣ Abra o projeto em alguma IDE
Após clonar o repositório abre ele em alguma IDE de sua preferência e dê reload na aplicação com o Maven para baixar todas as dependências.

### 3️⃣ Crie o banco de dados
No terminal, crie um novo database com MySQL que será utilizado na aplicação.
```bash
CREATE DATABASE forumhub;
```

### 4️⃣ Configure as variáveis de ambiente
No `application.properties`, defina as suas variáveis de ambiente (na IDE ou no computador):
```bash
spring.datasource.url=sua_url_do_banco_de_dados
spring.datasource.username=sua_user_do_banco_de_dados
spring.datasource.password=sua_senha_do_banco_de_dados
api.security.token.secret=sua_senha_segura_token
```

### 5️⃣ Inicie a aplicação
Inicie a aplicação rodando o `ForumhubApplication.java`.

## 📖 Documentação

### Registro
- Faz registro no sistema
- Rota não protegida
```
POST /auth/registro

Body JSON {
  "nome": "nome",
  "email": "nome@email.com",
  "senha": "senha123"
}
```

### Login
- Faz login no sistema
- Retora token JWT
- Rota não protegida
```
POST /auth/login

Body JSON {
  "email": "nome@email.com",
  "senha": "senha123"
}

Response {
  "token": "token"
}
```

### Criar tópico
- Criar um novo tópico
- Rota protegida
- Campos titulo (unique), mensagem (unique) e curso **obrigatórios**
```
POST /topicos
Authorization: Bearer {token}
Body JSON {
  "titulo": "Titulo 1",
  "mensagem": "Mensagem legal / dúvida...",
  "curso": "Curso 1"
}

Response {
  "id": id,
  "titulo": "Titulo 1",
  "mensagem": "Mensagem legal / dúvida...",
  "data": "data_criacao",
  "status": "NAO_RESPONDIDO",
  "autorId": autorId,
  "curso": "Curso 1"
}
```

### Listar tópicos
- Lista todos os tópicos do sistema com paginação
- Rota protegida
```
GET /topicos
Authorization: Bearer {token}

Response {
  "id": id,
  "titulo": "Titulo 1",
  "mensagem": "Mensagem legal / dúvida...",
  "data": "data_criacao",
  "status": "NAO_RESPONDIDO",
  "autorId": autorId,
  "curso": "Curso 1"
},
{ . . . }
```

### Detalhar tópico específico
- Detalha informações sobre algum tópico especifico
- Rota protegida
```
GET /topicos/{id}
Authorization: Bearer {token}

Response {
  "id": id,
  "titulo": "titulo",
  "mensagem": "mensagem",
  "data": "data_criacao",
  "status": "STATUS",
  "autorId": autorId,
  "curso": "curso"
}
```

### Atualizar tópico
- Atualiza titulo, mensagem ou status de algum tópico
- Rota protegida
- Apenas titulo e mensagem são **obrigatórios**
```
PUT /topicos/{id}
Authorization: Bearer {token}
Body JSON {
  "titulo": "Titulo 2",
  "mensagem": "Mensagem",
  "status": "FECHADO"
}

Response {
  "id": id,
  "titulo": "Titulo 1",
  "mensagem": "Mensagem legal / dúvida...",
  "status": "NAO_RESPONDIDO",
  "data": "data_criacao"
}
```

### Excluir tópico
- Exclui um tópico do banco de dados
- Rota protegida
```
DELETE /topicos/{id}
Authorization: Bearer {token}
```
