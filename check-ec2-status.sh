#!/bin/bash

# Script para verificar o status da aplicação na EC2

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

# Verifica o status dos processos na EC2
echo "Verificando processos na EC2..."
ssh -i "$SSH_KEY" "$EC2_USER@$EC2_HOST" "ps aux | grep -E 'java|node' | grep -v grep"

# Verifica se os serviços estão respondendo
echo -e "\nVerificando se o backend está respondendo..."
ssh -i "$SSH_KEY" "$EC2_USER@$EC2_HOST" "curl -s -o /dev/null -w '%{http_code}' http://localhost:8081/actuator/health || echo 'Falha ao conectar'"

echo -e "\nVerificando se o frontend está respondendo..."
ssh -i "$SSH_KEY" "$EC2_USER@$EC2_HOST" "curl -s -o /dev/null -w '%{http_code}' http://localhost:3000 || echo 'Falha ao conectar'"

# Verifica os logs recentes
echo -e "\nÚltimas linhas do log do backend:"
ssh -i "$SSH_KEY" "$EC2_USER@$EC2_HOST" "tail -n 10 ~/backend.log"

echo -e "\nÚltimas linhas do log do frontend:"
ssh -i "$SSH_KEY" "$EC2_USER@$EC2_HOST" "tail -n 10 ~/frontend.log"

echo -e "\nVerificação concluída!"