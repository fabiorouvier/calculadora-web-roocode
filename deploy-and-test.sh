#!/bin/bash

# Script para fazer o deploy e executar os testes na EC2

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

# Faz o commit das alterações
echo "Commitando alterações..."
git add .
git commit -m "Atualizações nos testes E2E"

# Faz o push para o repositório
echo "Enviando alterações para o repositório..."
git push

# Conecta na EC2 e faz o pull
echo "Conectando na EC2 e atualizando o código..."
ssh -i "$SSH_KEY" "$EC2_USER@$EC2_HOST" "cd $REMOTE_DIR && git pull"

# Reinicia o backend e o frontend
echo "Reiniciando o backend e o frontend..."
ssh -i "$SSH_KEY" "$EC2_USER@$EC2_HOST" "pkill -f 'java -jar' || true"
ssh -i "$SSH_KEY" "$EC2_USER@$EC2_HOST" "pkill -f 'node app.js' || true"
ssh -i "$SSH_KEY" "$EC2_USER@$EC2_HOST" "cd $REMOTE_DIR/backend && nohup java -jar target/backend-0.0.1-SNAPSHOT.jar > ~/backend.log 2>&1 &"
ssh -i "$SSH_KEY" "$EC2_USER@$EC2_HOST" "cd $REMOTE_DIR/frontend && nohup node app.js > ~/frontend.log 2>&1 &"

# Aguarda os serviços iniciarem
echo "Aguardando os serviços iniciarem..."
sleep 15

# Executa os testes
echo "Executando os testes E2E na EC2..."
ssh -i "$SSH_KEY" "$EC2_USER@$EC2_HOST" "cd $REMOTE_DIR/backend && ./mvnw test -Dtest=com.frouvier.backend.e2e.CucumberTestRunner"

echo "Testes E2E concluídos"