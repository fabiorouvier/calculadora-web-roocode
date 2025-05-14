#!/bin/bash

# Script para configurar uma nova instância EC2 para a aplicação calculadora-web-roocode

# Verifica se o IP da EC2 foi fornecido
if [ $# -ne 1 ]; then
  echo "Uso: $0 <ip_da_ec2>"
  exit 1
fi

# Configurações
EC2_USER="ubuntu"
EC2_IP="$1"
SSH_KEY="$HOME/aws/chave-frouvier.pem"
REPO_URL="https://github.com/fabiorouvier/calculadora-web-roocode.git"
REMOTE_DIR="/home/ubuntu/calculadora-web-roocode"

# Verifica se a chave SSH existe
if [ ! -f "$SSH_KEY" ]; then
  echo "Erro: Chave SSH não encontrada em $SSH_KEY"
  exit 1
fi

# Verifica se consegue se conectar à EC2
echo "Verificando conexão com a EC2..."
ssh -i "$SSH_KEY" -o ConnectTimeout=10 "$EC2_USER@$EC2_IP" "echo 'Conexão bem-sucedida!'" || {
  echo "Erro: Não foi possível conectar à EC2. Verifique o IP e a chave SSH."
  exit 1
}

# Atualiza o sistema
echo "Atualizando o sistema..."
ssh -i "$SSH_KEY" "$EC2_USER@$EC2_IP" "sudo apt-get update && sudo apt-get upgrade -y"

# Instala o Java
echo "Instalando o Java..."
ssh -i "$SSH_KEY" "$EC2_USER@$EC2_IP" "sudo apt-get install -y openjdk-17-jdk"

# Verifica a versão do Java
echo "Verificando a versão do Java..."
ssh -i "$SSH_KEY" "$EC2_USER@$EC2_IP" "java -version"

# Instala o Node.js
echo "Instalando o Node.js..."
ssh -i "$SSH_KEY" "$EC2_USER@$EC2_IP" "curl -fsSL https://deb.nodesource.com/setup_16.x | sudo -E bash - && sudo apt-get install -y nodejs"

# Verifica a versão do Node.js
echo "Verificando a versão do Node.js..."
ssh -i "$SSH_KEY" "$EC2_USER@$EC2_IP" "node -v && npm -v"

# Instala o Git
echo "Instalando o Git..."
ssh -i "$SSH_KEY" "$EC2_USER@$EC2_IP" "sudo apt-get install -y git"

# Clona o repositório
echo "Clonando o repositório..."
ssh -i "$SSH_KEY" "$EC2_USER@$EC2_IP" "git clone $REPO_URL $REMOTE_DIR"

# Instala as dependências do frontend
echo "Instalando as dependências do frontend..."
ssh -i "$SSH_KEY" "$EC2_USER@$EC2_IP" "cd $REMOTE_DIR/frontend && npm install"

# Compila o backend
echo "Compilando o backend..."
ssh -i "$SSH_KEY" "$EC2_USER@$EC2_IP" "cd $REMOTE_DIR/backend && ./mvnw clean package -DskipTests"

echo "Configuração concluída com sucesso!"
echo "Para iniciar a aplicação, execute: ./start-app-ec2.sh"