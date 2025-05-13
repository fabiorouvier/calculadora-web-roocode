# Testes End-to-End da Calculadora Web

Este documento contém instruções sobre como executar os testes end-to-end da Calculadora Web e gerar relatórios de testes.

## Pré-requisitos

- Java 17 ou superior
- Maven
- Node.js 14 ou superior
- Navegador Edge
- Acesso SSH à instância EC2

## Estrutura dos Testes

Os testes end-to-end estão organizados da seguinte forma:

- **Features**: Arquivos `.feature` escritos em Gherkin que descrevem os cenários de teste.
- **Step Definitions**: Classes Java que implementam os passos definidos nas features.
- **Page Objects**: Classes Java que encapsulam a interação com as páginas da aplicação.

## Scripts Disponíveis

### 1. Deploy na EC2

O script `deploy-and-test.sh` faz o deploy da aplicação na EC2 e executa os testes:

```bash
./deploy-and-test.sh
```

### 2. Verificação do Status na EC2

O script `check-ec2-status.sh` verifica o status da aplicação na EC2:

```bash
./check-ec2-status.sh
```

### 3. Iniciar o Frontend na EC2

O script `start-frontend-ec2.sh` inicia o frontend na EC2:

```bash
./start-frontend-ec2.sh
```

### 4. Executar Testes na EC2

O script `run-e2e-tests-ec2.sh` executa os testes end-to-end na EC2:

```bash
./run-e2e-tests-ec2.sh
```

### 5. Gerar Relatório de Testes

O script `generate-test-report.sh` gera um relatório HTML com os resultados dos testes:

```bash
./generate-test-report.sh
```

### 6. Abrir Relatório de Testes

O script `open-test-report.sh` abre o relatório de testes no navegador:

```bash
./open-test-report.sh
```

## Executando os Testes Localmente

Para executar os testes localmente, siga os passos abaixo:

1. Inicie o backend:

```bash
cd backend
./mvnw spring-boot:run
```

2. Inicie o frontend:

```bash
cd frontend
node app.js
```

3. Execute os testes:

```bash
cd backend
./mvnw test -Dtest=com.frouvier.backend.e2e.CucumberTestRunner
```

## Relatórios de Testes

Os relatórios de testes são gerados nos seguintes formatos:

- **HTML**: `target/cucumber-reports/report.html`
- **JSON**: `target/cucumber-reports/report.json`

## Problemas Conhecidos

- Os testes de registro estão falhando porque as mensagens de sucesso, erro e validação não estão sendo exibidas corretamente.
- É necessário implementar corretamente a validação de formulários no frontend e exibir mensagens apropriadas.
- A integração entre o frontend e o backend para o registro de usuários precisa ser revisada.

## Próximos Passos

1. Corrigir os problemas nos testes de registro.
2. Adicionar mais cenários de teste para cobrir casos de borda.
3. Configurar um pipeline de CI/CD para executar os testes automaticamente.