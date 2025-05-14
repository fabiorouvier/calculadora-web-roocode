#!/bin/bash

# Script para atualizar o IP da EC2 em todos os arquivos relevantes

# Verifica se o novo IP foi fornecido
if [ $# -ne 1 ]; then
  echo "Uso: $0 <novo_ip>"
  exit 1
fi

# Novo IP da EC2
NEW_IP="$1"
OLD_IP="44.204.82.30"

echo "Atualizando o IP da EC2 de $OLD_IP para $NEW_IP em todos os arquivos relevantes..."

# Atualiza os scripts shell
find . -type f -name "*.sh" -exec grep -l "$OLD_IP" {} \; | xargs sed -i '' "s/$OLD_IP/$NEW_IP/g"

# Atualiza o arquivo WebDriverConfig.java
sed -i '' "s/$OLD_IP/$NEW_IP/g" backend/src/test/java/com/frouvier/backend/e2e/config/WebDriverConfig.java

echo "Atualização concluída!"
echo "Arquivos atualizados:"
find . -type f -name "*.sh" -exec grep -l "$NEW_IP" {} \;
grep -l "$NEW_IP" backend/src/test/java/com/frouvier/backend/e2e/config/WebDriverConfig.java

echo "Não se esqueça de fazer commit das alterações:"
echo "git add ."
echo "git commit -m \"Atualiza IP da EC2 para $NEW_IP\""
echo "git push"