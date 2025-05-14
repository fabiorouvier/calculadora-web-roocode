#!/bin/bash

# Script para verificar se o Node.js está instalado na EC2 e instalá-lo caso não esteja

# Configurações
EC2_USER="ubuntu"
EC2_HOST="44.204.82.30"
SSH_KEY="$HOME/aws/chave-frouvier.pem"

# Verifica se a chave SSH existe
if [ ! -f "$SSH_KEY" ]; then
  echo "Erro: Chave SSH não encontrada em $SSH_KEY"
  exit 1
fi

# Verifica se o Node.js está instalado
echo "Verificando se o Node.js está instalado..."
ssh -i "$SSH_KEY" "$EC2_USER@$EC2_HOST" "node -v || echo 'Node.js não está instalado'"

# Instala o Node.js caso não esteja instalado
echo "Instalando o Node.js..."
ssh -i "$SSH_KEY" "$EC2_USER@$EC2_HOST" "which node || (curl -fsSL https://deb.nodesource.com/setup_16.x | sudo -E bash - && sudo apt-get install -y nodejs)"

# Verifica a versão do Node.js
echo "Verificando a versão do Node.js..."
ssh -i "$SSH_KEY" "$EC2_USER@$EC2_HOST" "node -v"

# Verifica a versão do npm
echo "Verificando a versão do npm..."
ssh -i "$SSH_KEY" "$EC2_USER@$EC2_HOST" "npm -v"

echo "Verificação e instalação concluídas!"