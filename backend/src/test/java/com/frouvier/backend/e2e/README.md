# Testes End-to-End (E2E) com Cucumber e Selenium WebDriver

Este diretório contém os testes automatizados end-to-end para a aplicação web de calculadora, implementados usando BDD com Cucumber e Selenium WebDriver.

## Estrutura dos Testes

- **features/**: Arquivos `.feature` escritos em português usando a sintaxe Gherkin
- **steps/**: Classes Java com as implementações dos passos definidos nos arquivos `.feature`
- **pages/**: Classes Java implementando o padrão Page Object Model (POM)
- **config/**: Classes de configuração para o Cucumber e WebDriver

## Cenários de Teste

Os testes cobrem os seguintes fluxos:

1. **Login**
   - Login com sucesso
   - Login com credenciais inválidas

2. **Operações Matemáticas**
   - Adição
   - Subtração
   - Multiplicação
   - Divisão

3. **Histórico de Cálculos**
   - Verificação do histórico de cálculos

4. **Logout**
   - Logout do sistema

## Executando os Testes

### Localmente

Para executar os testes localmente, use o script `run-e2e-tests.sh`:

```bash
cd backend
chmod +x run-e2e-tests.sh
./run-e2e-tests.sh
```

Este script:
1. Verifica se o backend e o frontend estão rodando
2. Inicia os serviços se necessário
3. Modifica a URL base para apontar para localhost
4. Executa os testes
5. Restaura a URL base original

### Na EC2

Para executar os testes na EC2, use o script `deploy-and-test.sh`:

```bash
chmod +x deploy-and-test.sh
./deploy-and-test.sh
```

Este script:
1. Faz o commit e push das alterações
2. Conecta na EC2 e faz o pull
3. Reinicia o backend e o frontend
4. Executa os testes

## Configuração do WebDriver

Os testes estão configurados para usar o Microsoft Edge por padrão, mas também suportam Chrome, Firefox e Safari. A configuração está em `config/WebDriverConfig.java`.

## Melhorias Implementadas

1. **Tempo de espera aumentado**: O tempo de espera foi aumentado para 30 segundos para lidar com latência da EC2.
2. **Verificação robusta do resultado**: Melhorias na verificação do resultado para aguardar explicitamente pela visibilidade e pelo texto não vazio.
3. **Verificação robusta do histórico**: Uso de JavaScript para obter o texto do histórico e melhor tratamento do formato dos números.
4. **Verificação robusta da página de login**: Verificação tanto da URL quanto da presença dos elementos do formulário.