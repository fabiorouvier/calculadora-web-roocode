#!/bin/bash

# Script para fazer o deploy via Git

# Faz o commit das alterações
echo "Commitando alterações..."
git add .
git commit -m "Adiciona testes end-to-end para a funcionalidade de registro"

# Faz o push para o repositório
echo "Enviando alterações para o repositório..."
git push

echo "Deploy concluído! As alterações foram enviadas para o repositório Git."
echo "A instância EC2 precisa ser atualizada manualmente ou via webhook."