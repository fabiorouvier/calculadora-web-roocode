#!/bin/bash

# Script para executar os testes E2E localmente sem depender do banco de dados

echo "Configurando ambiente para testes E2E locais..."

# Criar um arquivo de propriedades temporário para os testes
cat > src/test/resources/application-test.properties << EOF
# Desabilitar a conexão com o banco de dados real
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.username=sa
spring.datasource.password=
spring.datasource.driver-class-name=org.h2.Driver
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=create-drop

# Configurações da aplicação
server.port=8081
EOF

# Configurar o navegador a ser usado
export BROWSER=chrome
echo "Configurado para usar Chrome para os testes..."

# Executar os testes com o perfil de teste
echo "Executando os testes E2E localmente..."
./mvnw test -Dtest=com.frouvier.backend.e2e.CucumberTestRunner -Dspring.profiles.active=test

# Remover o arquivo de propriedades temporário
rm src/test/resources/application-test.properties

echo "Testes E2E concluídos"
echo "Relatório disponível em: target/cucumber-reports/report.html"