#!/bin/bash

# Script para executar os testes end-to-end e gerar um relatório em HTML/PDF

echo "=== INICIANDO EXECUÇÃO DOS TESTES E GERAÇÃO DE RELATÓRIO ==="

# Executar os testes end-to-end
echo "Executando testes end-to-end com Edge apontando para EC2..."
./run-e2e-tests-edge-ec2-report.sh

# Verificar se os testes foram executados com sucesso
if [ $? -ne 0 ]; then
    echo "Erro ao executar os testes end-to-end."
    exit 1
fi

# Encontrar o relatório JSON mais recente
JSON_REPORT="target/cucumber-reports/report.json"
if [ ! -f "$JSON_REPORT" ]; then
    echo "Erro: Relatório JSON não encontrado em $JSON_REPORT"
    exit 1
fi

# Gerar relatório em HTML
echo "Gerando relatório em HTML..."
TIMESTAMP=$(date +"%Y%m%d_%H%M%S")
REPORT_DIR="target/test-reports"
mkdir -p "$REPORT_DIR"

# Executar o script Python para converter o relatório Markdown para HTML
python3 ./convert_md_to_pdf.py "$REPORT_DIR/cucumber_report_$TIMESTAMP.md"

# Verificar se o relatório HTML foi gerado
HTML_REPORT="$REPORT_DIR/cucumber_report_$TIMESTAMP.html"
if [ ! -f "$HTML_REPORT" ]; then
    echo "Erro: Relatório HTML não foi gerado."
    exit 1
fi

# Abrir o relatório HTML no navegador
echo "Abrindo o relatório HTML no navegador..."
open "$HTML_REPORT"

echo "=== EXECUÇÃO DOS TESTES E GERAÇÃO DE RELATÓRIO CONCLUÍDAS ==="
echo "Para gerar um PDF a partir do relatório HTML:"
echo "  1. No navegador aberto, use a função de impressão (Ctrl+P ou Cmd+P)"
echo "  2. Selecione 'Salvar como PDF' como destino da impressão"
echo "  3. Salve o arquivo PDF"
echo ""
echo "Relatório HTML: $HTML_REPORT"
echo "Sugestão de nome para o PDF: $REPORT_DIR/cucumber_report_$TIMESTAMP.pdf"