#!/bin/bash

# Script para iniciar o frontend na EC2

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

# Mata qualquer processo do frontend que possa estar rodando
echo "Matando processos do frontend existentes..."
ssh -i "$SSH_KEY" "$EC2_USER@$EC2_HOST" "pkill -f 'node app.js' || true"

# Inicia o frontend
echo "Iniciando o frontend..."
ssh -i "$SSH_KEY" "$EC2_USER@$EC2_HOST" "cd $REMOTE_DIR/frontend && nohup node app.js > ~/frontend.log 2>&1 &"

# Aguarda o serviço iniciar
echo "Aguardando o serviço iniciar..."
sleep 5

# Verifica se o frontend está respondendo
echo "Verificando se o frontend está respondendo..."
ssh -i "$SSH_KEY" "$EC2_USER@$EC2_HOST" "curl -s -o /dev/null -w '%{http_code}' http://localhost:3000 || echo 'Falha ao conectar'"

# Verifica os logs recentes
echo -e "\nÚltimas linhas do log do frontend:"
ssh -i "$SSH_KEY" "$EC2_USER@$EC2_HOST" "tail -n 10 ~/frontend.log"

echo -e "\nFrontend iniciado!"