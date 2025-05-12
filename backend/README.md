# Calculadora REST API

API REST em Spring Boot que recebe operações matemáticas básicas, executa o cálculo e salva o histórico com o usuário logado.

## Tecnologias Utilizadas

- Spring Boot 3.4.5
- Spring Security
- Spring Data JPA
- H2 Database (banco de dados em memória)

## Estrutura do Projeto

- **model**: Entidades JPA
- **repository**: Interfaces de repositório para acesso ao banco de dados
- **service**: Serviços de negócio
- **controller**: Controladores REST
- **dto**: Objetos de transferência de dados
- **config**: Configurações do Spring
- **exception**: Classes de exceção e manipuladores

## Endpoints da API

### Autenticação

- **POST /api/users/register**: Registra um novo usuário
  ```json
  {
    "username": "novousuario",
    "password": "senha123"
  }
  ```

- **POST /api/login**: Autentica um usuário (formulário padrão do Spring Security)
  - username: nome de usuário
  - password: senha

- **POST /api/logout**: Encerra a sessão do usuário

### Calculadora

- **POST /api/calculator/calculate**: Executa uma operação matemática
  ```json
  {
    "firstOperand": 10,
    "secondOperand": 5,
    "operator": "+"  // Operadores válidos: +, -, *, /
  }
  ```

- **GET /api/calculator/history**: Retorna o histórico de cálculos do usuário logado

## Usuários Pré-configurados

A aplicação cria automaticamente dois usuários para teste:

1. **Usuário comum**:
   - Username: user
   - Password: password

2. **Usuário administrador**:
   - Username: admin
   - Password: admin123

## Como Executar

1. Clone o repositório
2. Execute o comando: `./mvnw spring-boot:run`
3. Acesse a API em: http://localhost:8081

## Console H2 (Banco de Dados)

O console do H2 está habilitado e pode ser acessado em:
- URL: http://localhost:8081/h2-console
- JDBC URL: jdbc:h2:mem:testdb
- Username: sa
- Password: (vazio)

## Exemplos de Uso

### Registrar um novo usuário
```bash
curl -X POST http://localhost:8081/api/users/register -H "Content-Type: application/json" -d '{"username":"novousuario","password":"senha123"}'
```

### Fazer login
```bash
curl -X POST http://localhost:8081/api/login -d "username=user&password=password" -c cookies.txt
```

### Executar um cálculo
```bash
curl -X POST http://localhost:8081/api/calculator/calculate -H "Content-Type: application/json" -d '{"firstOperand":10,"secondOperand":5,"operator":"+"}' -b cookies.txt
```

### Obter histórico de cálculos
```bash
curl -X GET http://localhost:8081/api/calculator/history -b cookies.txt