#!/bin/bash

# Script para executar os testes end-to-end e gerar o relatório HTML

echo "=== INICIANDO EXECUÇÃO DOS TESTES E2E COM EDGE APONTANDO PARA EC2 ==="
echo "URL: http://44.204.82.30:3000"
echo "Navegador: Edge"

# Registra o tempo de início
INICIO=$(date +%s)

# Define o navegador a ser usado
export BROWSER=edge
echo "Configurado para usar Edge para os testes..."

# Executa os testes usando o Edge
echo "Executando os testes E2E..."
cd /Users/fabiodasilvarouvier/Downloads/calculadora-web-roocode/backend && ./mvnw test -Dtest=com.frouvier.backend.e2e.CucumberTestRunner

# Registra o tempo de fim
FIM=$(date +%s)

# Calcula o tempo de execução
TEMPO_EXECUCAO=$((FIM - INICIO))
MINUTOS=$((TEMPO_EXECUCAO / 60))
SEGUNDOS=$((TEMPO_EXECUCAO % 60))

echo "Testes E2E concluídos em $MINUTOS minutos e $SEGUNDOS segundos"
echo "Relatório HTML disponível em: backend/target/cucumber-reports/report.html"

# Cria um diretório para os relatórios se não existir
mkdir -p /Users/fabiodasilvarouvier/Downloads/calculadora-web-roocode/reports

# Copia o relatório para o diretório de relatórios
cp /Users/fabiodasilvarouvier/Downloads/calculadora-web-roocode/backend/target/cucumber-reports/report.html /Users/fabiodasilvarouvier/Downloads/calculadora-web-roocode/reports/e2e-test-report-$(date +%Y%m%d%H%M%S).html
cp /Users/fabiodasilvarouvier/Downloads/calculadora-web-roocode/backend/target/cucumber-reports/report.html /Users/fabiodasilvarouvier/Downloads/calculadora-web-roocode/reports/latest-e2e-test-report.html

# Abre o relatório no navegador
echo "Abrindo o relatório de testes no navegador..."
if [[ "$OSTYPE" == "darwin"* ]]; then
  # macOS
  open /Users/fabiodasilvarouvier/Downloads/calculadora-web-roocode/reports/latest-e2e-test-report.html
elif [[ "$OSTYPE" == "linux-gnu"* ]]; then
  # Linux
  xdg-open /Users/fabiodasilvarouvier/Downloads/calculadora-web-roocode/reports/latest-e2e-test-report.html
elif [[ "$OSTYPE" == "msys" || "$OSTYPE" == "win32" ]]; then
  # Windows
  start /Users/fabiodasilvarouvier/Downloads/calculadora-web-roocode/reports/latest-e2e-test-report.html
else
  echo "Sistema operacional não suportado: $OSTYPE"
  echo "Por favor, abra o arquivo /Users/fabiodasilvarouvier/Downloads/calculadora-web-roocode/reports/latest-e2e-test-report.html manualmente."
fi

echo "=== EXECUÇÃO DOS TESTES E2E FINALIZADA ==="