#!/bin/bash

# Script para fazer o deploy do código local para o GitHub e depois para a EC2

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
git commit -m "Atualização do código"

# Faz o push para o repositório
echo "Enviando alterações para o repositório GitHub..."
git push

# Conecta na EC2 e faz o pull
echo "Conectando na EC2 e atualizando o código..."
ssh -i "$SSH_KEY" "$EC2_USER@$EC2_HOST" "cd $REMOTE_DIR && git pull"

echo "Deploy concluído! O código foi atualizado na EC2."
echo "Para iniciar o backend, execute: ./start-backend-only-ec2.sh"
echo "Para iniciar o frontend, execute: ./start-frontend-ec2.sh"