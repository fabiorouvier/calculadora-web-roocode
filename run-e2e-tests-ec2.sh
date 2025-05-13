#!/bin/bash

# Script para executar os testes end-to-end na EC2

# Configurações
EC2_USER="ubuntu"
EC2_HOST="44.204.82.30"
SSH_KEY="$HOME/aws/chave-frouvier.pem"
REMOTE_DIR="/home/ubuntu/calculadora-web-roocode"

# Verifica se a chave SSH existe
if [ ! -f "$SSH_KEY" ]; then
  echo "Erro: Chave SSH não encontrada em $SSH_KEY"
  exit 1
fi

# Executa os testes end-to-end na EC2
echo "Executando os testes end-to-end na EC2..."
ssh -i "$SSH_KEY" "$EC2_USER@$EC2_HOST" "cd $REMOTE_DIR/backend && ./mvnw test -Dtest=com.frouvier.backend.e2e.CucumberTestRunner"

# Copia o relatório de testes para o diretório local
echo "Copiando o relatório de testes para o diretório local..."
scp -i "$SSH_KEY" "$EC2_USER@$EC2_HOST:$REMOTE_DIR/backend/target/cucumber-reports/report.html" ./ec2-test-report.html

echo "Testes end-to-end concluídos! O relatório está disponível em ./ec2-test-report.html"