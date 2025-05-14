#!/bin/bash

# Script para iniciar apenas o backend na EC2

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

# Mata qualquer processo do backend que possa estar rodando
echo "Matando processos existentes do backend..."
ssh -i "$SSH_KEY" "$EC2_USER@$EC2_HOST" "pkill -f 'java -jar' || true"

# Inicia o backend
echo "Iniciando o backend..."
ssh -i "$SSH_KEY" "$EC2_USER@$EC2_HOST" "cd $REMOTE_DIR/backend && nohup java -jar target/backend-0.0.1-SNAPSHOT.jar > ~/backend.log 2>&1 &"

# Aguarda o backend iniciar
echo "Aguardando o backend iniciar..."
sleep 15

# Verifica se o backend está respondendo
echo "Verificando se o backend está respondendo..."
ssh -i "$SSH_KEY" "$EC2_USER@$EC2_HOST" "curl -s -o /dev/null -w '%{http_code}' http://localhost:8081/actuator/health || echo 'Falha ao conectar'"

# Verifica os processos em execução
echo "Verificando processos em execução..."
ssh -i "$SSH_KEY" "$EC2_USER@$EC2_HOST" "ps aux | grep java | grep -v grep"

echo "Backend iniciado com sucesso na EC2!"