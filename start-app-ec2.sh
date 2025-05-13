#!/bin/bash

# Script para iniciar o backend e o frontend na EC2

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

# Mata qualquer processo do backend e frontend que possa estar rodando
echo "Matando processos existentes..."
ssh -i "$SSH_KEY" "$EC2_USER@$EC2_HOST" "pkill -f 'java -jar' || true"
ssh -i "$SSH_KEY" "$EC2_USER@$EC2_HOST" "pkill -f 'node app.js' || true"

# Inicia o backend
echo "Iniciando o backend..."
ssh -i "$SSH_KEY" "$EC2_USER@$EC2_HOST" "cd $REMOTE_DIR/backend && nohup java -jar target/backend-0.0.1-SNAPSHOT.jar > ~/backend.log 2>&1 &"

# Aguarda o backend iniciar
echo "Aguardando o backend iniciar..."
sleep 15

# Inicia o frontend
echo "Iniciando o frontend..."
ssh -i "$SSH_KEY" "$EC2_USER@$EC2_HOST" "cd $REMOTE_DIR/frontend && nohup node app.js > ~/frontend.log 2>&1 &"

# Aguarda o frontend iniciar
echo "Aguardando o frontend iniciar..."
sleep 5

# Verifica se o backend está respondendo
echo "Verificando se o backend está respondendo..."
ssh -i "$SSH_KEY" "$EC2_USER@$EC2_HOST" "curl -s -o /dev/null -w '%{http_code}' http://localhost:8081/actuator/health || echo 'Falha ao conectar'"

# Verifica se o frontend está respondendo
echo "Verificando se o frontend está respondendo..."
ssh -i "$SSH_KEY" "$EC2_USER@$EC2_HOST" "curl -s -o /dev/null -w '%{http_code}' http://localhost:3000 || echo 'Falha ao conectar'"

# Verifica os processos em execução
echo "Verificando processos em execução..."
ssh -i "$SSH_KEY" "$EC2_USER@$EC2_HOST" "ps aux | grep -E 'java|node' | grep -v grep"

echo "Aplicação iniciada com sucesso na EC2!"