```markdown
# Tech Challenge FIAP – API de Usuários

## 📌 Visão Geral

Este projeto é uma API REST desenvolvida em **Java** utilizando o **Spring Framework** e **Maven** para gerenciamento de dependências.  
A aplicação implementa autenticação via **JWT (JSON Web Token)** e segue princípios de **Clean Code** e **SOLID**, garantindo escalabilidade e manutenção a longo prazo.  
O deploy e execução local podem ser feitos via **Docker**.

---

## 🚀 Tecnologias Utilizadas

- **Java 17+** – Linguagem principal
- **Spring Boot** – Framework para criação da API REST
- **Spring Security** – Autenticação e autorização
- **Spring Data JPA** – Persistência de dados
- **Maven** – Gerenciador de dependências
- **Docker** – Containerização
- **PostgreSQL** – Banco de dados relacional
- **JWT** – Autenticação baseada em tokens

---

## 📂 Estrutura do Projeto
```

src/
├── main/
│ ├── java/com/example/tc/
│ │ ├── controller/ # Endpoints REST
│ │ ├── service/ # Regras de negócio
│ │ ├── repository/ # Interfaces do JPA
│ │ ├── dto/ # Objetos de transferência de dados
│ │ ├── domain/ # Entidades do banco
│ │ └── security/ # Configuração JWT + Spring Security
│ └── resources/
│ ├── application.yml # Configurações
│ └── schema.sql # Scripts de banco (opcional)
└── test/ # Testes automatizados

```

---

## 🔐 Autenticação JWT

A autenticação é feita via **JWT** seguindo o fluxo:

1. Usuário envia **login** e **senha** para `/auth/login`.
2. Se válido, o sistema retorna um **token JWT** assinado.
3. As requisições subsequentes enviam o token no **header Authorization**:
```

Authorization: Bearer <token>

````
4. O filtro JWT (`OncePerRequestFilter`) valida o token antes de acessar recursos protegidos.

---

## 📏 Princípios SOLID Aplicados

- **S** – *Single Responsibility*: cada classe com uma única responsabilidade.
- **O** – *Open/Closed*: código aberto para extensão, fechado para modificação.
- **L** – *Liskov Substitution*: abstrações permitem substituição segura.
- **I** – *Interface Segregation*: interfaces específicas e enxutas.
- **D** – *Dependency Inversion*: dependência de abstrações, não de implementações concretas.

---

## ⚙️ Como Rodar Localmente

### 1️⃣ Usando Maven
```bash
# Build
mvn clean package

# Rodar
java -jar target/tech-challenge-0.0.1-SNAPSHOT.jar
````

### 2️⃣ Usando Docker

```bash
# Build da imagem
docker build -t tech-challenge-fiap .

# Executar container
docker run -p 8080:8080 tech-challenge-fiap
```

---

## 🧪 Testando a API

### Criar Usuário

```bash
curl -X POST http://localhost:8080/users \
-H "Content-Type: application/json" \
-d '{"name":"João","email":"joao@email.com","login":"joao","password":"123456"}'
```

### Login

```bash
curl -X POST http://localhost:8080/auth/login \
-H "Content-Type: application/json" \
-d '{"login":"joao","password":"123456"}'
```

### Listar Usuários (com token)

```bash
curl -X GET http://localhost:8080/users \
-H "Authorization: Bearer <token>"
```

---

## 📄 Licença

Este projeto é de uso acadêmico e segue a licença **MIT**.
