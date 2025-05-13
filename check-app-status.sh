#!/bin/bash

# Script para verificar o status da aplicação na EC2

# Configurações
EC2_USER="ubuntu"
EC2_HOST="100.26.194.86"
SSH_KEY="$HOME/aws/chave-frouvier.pem"

# Verifica se a chave SSH existe
if [ ! -f "$SSH_KEY" ]; then
  echo "Erro: Chave SSH não encontrada em $SSH_KEY"
  exit 1
fi

# Verifica os processos em execução
echo "Verificando processos em execução..."
ssh -i "$SSH_KEY" "$EC2_USER@$EC2_HOST" "ps aux | grep -E 'java|node' | grep -v grep"

# Verifica se o backend está respondendo
echo -e "\nVerificando se o backend está respondendo..."
ssh -i "$SSH_KEY" "$EC2_USER@$EC2_HOST" "curl -s -o /dev/null -w '%{http_code}' http://localhost:8081/actuator/health || echo 'Falha ao conectar'"

# Verifica se o frontend está respondendo
echo -e "\nVerificando se o frontend está respondendo..."
ssh -i "$SSH_KEY" "$EC2_USER@$EC2_HOST" "curl -s -o /dev/null -w '%{http_code}' http://localhost:3000 || echo 'Falha ao conectar'"

echo -e "\nVerificação concluída!"