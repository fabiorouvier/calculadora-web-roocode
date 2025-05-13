#!/bin/bash

# Script para abrir o relatório de testes no navegador

# Verifica se o arquivo de relatório existe
if [ ! -f "test-summary.html" ]; then
  echo "Erro: Arquivo de relatório não encontrado em test-summary.html"
  exit 1
fi

# Abre o relatório no navegador padrão
echo "Abrindo o relatório de testes no navegador..."

# Detecta o sistema operacional e abre o navegador de acordo
if [[ "$OSTYPE" == "darwin"* ]]; then
  # macOS
  open test-summary.html
elif [[ "$OSTYPE" == "linux-gnu"* ]]; then
  # Linux
  xdg-open test-summary.html
elif [[ "$OSTYPE" == "msys" || "$OSTYPE" == "win32" ]]; then
  # Windows
  start test-summary.html
else
  echo "Sistema operacional não suportado: $OSTYPE"
  echo "Por favor, abra o arquivo test-summary.html manualmente."
  exit 1
fi

echo "Relatório de testes aberto no navegador."