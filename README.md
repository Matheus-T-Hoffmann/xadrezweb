# Xadrez Web API

## Descrição

API backend para gerenciamento de partidas de xadrez, incluindo autenticação de usuários, criação de partidas, execução de movimentos e matchmaking simples.

---

## Tecnologias

* Java
* Spring Boot
* Spring Data JPA
* H2 Database

---

## Como rodar o projeto

```bash
git clone <repo>
cd projeto
./mvnw spring-boot:run
```

A API estará disponível em:

```
http://localhost:8080
```

---

## Autenticação

### Criar usuário

POST `/users`

```json
{
  "username": "joao",
  "email": "joao@email.com",
  "password": "12345678"
}
```

---

### Login

POST `/auth/login`

```json
{
  "email": "joao@email.com",
  "password": "12345678"
}
```

---

## Partidas

### Criar partida manual

POST `/matches?playerWhiteId=1&playerBlackId=2`

---

### Matchmaking

POST `/matches/find?userId=1`

---

### Cancelar busca

POST `/matches/cancel`

---

### Fazer jogada

POST `/matches/{id}/moves`

```json
{
  "source": "e2",
  "target": "e4"
}
```

---

### Ver tabuleiro

GET `/matches/{id}/board`

---

## Usuários

### Obter perfil de usuário

GET `/users/{id}`

---

### Histórico de partidas

GET `/users/{id}/matches`

---

## Observações

* Matchmaking em memória (não persistente)
* Sistema simplificado para fins acadêmicos

---
