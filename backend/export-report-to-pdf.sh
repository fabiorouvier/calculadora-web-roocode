#!/bin/bash

# Script para exportar o relatório de testes Cucumber para Markdown e PDF

echo "=== EXPORTANDO RELATÓRIO DE TESTES ==="

# Verificar se o relatório JSON existe
JSON_REPORT="target/cucumber-reports/report.json"
if [ ! -f "$JSON_REPORT" ]; then
    echo "Erro: Relatório JSON não encontrado em $JSON_REPORT"
    echo "Execute os testes primeiro usando ./run-e2e-tests-edge-ec2-report.sh"
    exit 1
fi

# Criar diretório para relatórios se não existir
REPORT_DIR="target/test-reports"
mkdir -p "$REPORT_DIR"

# Nome dos arquivos com timestamp
TIMESTAMP=$(date +"%Y%m%d_%H%M%S")
MARKDOWN_REPORT="$REPORT_DIR/cucumber_report_$TIMESTAMP.md"
HTML_REPORT="$REPORT_DIR/cucumber_report_$TIMESTAMP.html"

# Gerar o relatório em Markdown
echo "Gerando relatório em formato Markdown..."

# Cabeçalho do relatório
cat > "$MARKDOWN_REPORT" << EOF
# Relatório de Execução dos Testes End-to-End

**Data e hora:** $(date +"%d/%m/%Y %H:%M:%S")

## Resumo da Execução

EOF

# Usar Python para analisar o JSON e gerar estatísticas
python3 - <<EOF >> "$MARKDOWN_REPORT"
import json
import sys
from datetime import datetime

try:
    with open("$JSON_REPORT", 'r') as f:
        data = json.load(f)
except Exception as e:
    print(f"Erro ao ler o arquivo JSON: {e}")
    sys.exit(1)

# Inicializa contadores
total_scenarios = 0
passed_scenarios = 0
failed_scenarios = 0
skipped_scenarios = 0
scenario_details = []
feature_details = {}

# Analisa cada feature
for feature in data:
    feature_name = feature.get('name', 'Feature sem nome')
    feature_details[feature_name] = []
    
    # Analisa cada elemento (cenário ou background)
    for element in feature.get('elements', []):
        if element.get('type') == 'scenario':
            total_scenarios += 1
            scenario_name = element.get('name', 'Cenário sem nome')
            
            # Verifica o status do cenário
            status = 'passed'
            steps_details = []
            
            for step in element.get('steps', []):
                step_name = step.get('name', '')
                step_keyword = step.get('keyword', '').strip()
                step_result = step.get('result', {})
                step_status = step_result.get('status', 'unknown')
                step_duration = step_result.get('duration', 0) / 1000000000  # Converter de nano para segundos
                
                steps_details.append({
                    'name': step_name,
                    'keyword': step_keyword,
                    'status': step_status,
                    'duration': step_duration
                })
                
                if step_status == 'failed':
                    status = 'failed'
                elif step_status == 'skipped' and status != 'failed':
                    status = 'skipped'
            
            # Incrementa o contador apropriado
            if status == 'passed':
                passed_scenarios += 1
            elif status == 'failed':
                failed_scenarios += 1
            elif status == 'skipped':
                skipped_scenarios += 1
            
            # Adiciona detalhes do cenário
            scenario_detail = {
                'name': scenario_name,
                'status': status,
                'steps': steps_details
            }
            
            feature_details[feature_name].append(scenario_detail)
            scenario_details.append({
                'feature': feature_name,
                'scenario': scenario_name,
                'status': status
            })

# Calcula percentuais
pass_percentage = (passed_scenarios / total_scenarios * 100) if total_scenarios > 0 else 0
fail_percentage = (failed_scenarios / total_scenarios * 100) if total_scenarios > 0 else 0
skip_percentage = (skipped_scenarios / total_scenarios * 100) if total_scenarios > 0 else 0

# Escreve o resumo
print(f"- **Total de cenários executados**: {total_scenarios}")
print(f"- **Cenários com sucesso**: {passed_scenarios} ({pass_percentage:.2f}%)")
print(f"- **Cenários com falha**: {failed_scenarios} ({fail_percentage:.2f}%)")
print(f"- **Cenários ignorados**: {skipped_scenarios} ({skip_percentage:.2f}%)")
print(f"- **Tempo total de execução**: Aproximadamente {sum(sum(step['duration'] for step in scenario['steps']) for feature_scenarios in feature_details.values() for scenario in feature_scenarios):.2f} segundos")

# Escreve os detalhes por feature
print("\n## Detalhes dos Cenários\n")

for feature_name, scenarios in feature_details.items():
    print(f"### {feature_name}\n")
    
    for scenario in scenarios:
        status_symbol = "✅" if scenario['status'] == 'passed' else "❌" if scenario['status'] == 'failed' else "⏭️"
        print(f"{status_symbol} **{scenario['name']}**\n")
        
        # Detalhes dos passos
        for step in scenario['steps']:
            step_status = "✅" if step['status'] == 'passed' else "❌" if step['status'] == 'failed' else "⏭️"
            print(f"  {step_status} {step['keyword']} {step['name']} ({step['duration']:.2f}s)")
        
        print("")  # Linha em branco entre cenários
EOF

echo "Relatório Markdown gerado com sucesso: $MARKDOWN_REPORT"

# Converter para HTML se possível
if command -v pandoc &> /dev/null; then
    echo "Convertendo relatório para HTML usando pandoc..."
    pandoc "$MARKDOWN_REPORT" -o "$HTML_REPORT" --standalone --metadata title="Relatório de Testes End-to-End" --css=https://cdn.jsdelivr.net/npm/water.css@2/out/water.css
    echo "Relatório HTML gerado com sucesso: $HTML_REPORT"
    
    # Tentar converter para PDF se possível
    if command -v wkhtmltopdf &> /dev/null; then
        PDF_REPORT="$REPORT_DIR/cucumber_report_$TIMESTAMP.pdf"
        echo "Convertendo relatório HTML para PDF usando wkhtmltopdf..."
        wkhtmltopdf \
            --enable-local-file-access \
            --page-size A4 \
            --margin-top 20 \
            --margin-right 20 \
            --margin-bottom 20 \
            --margin-left 20 \
            --header-center "Relatório de Testes End-to-End" \
            --header-line \
            --footer-center "Página [page] de [topage]" \
            --footer-line \
            "$HTML_REPORT" "$PDF_REPORT"
        
        if [ $? -eq 0 ]; then
            echo "Relatório PDF gerado com sucesso: $PDF_REPORT"
        else
            echo "Erro ao gerar o relatório PDF."
        fi
    else
        echo "wkhtmltopdf não está instalado. O relatório PDF não foi gerado."
        echo "Para gerar um PDF, você pode:"
        echo "  1. Instalar wkhtmltopdf e executar este script novamente"
        echo "  2. Abrir o arquivo HTML em um navegador e usar a função de impressão para salvar como PDF"
        echo "  3. Usar um serviço online para converter o arquivo Markdown ou HTML para PDF"
    fi
else
    echo "pandoc não está instalado. O relatório HTML não foi gerado."
    echo "Para visualizar o relatório, abra o arquivo Markdown em um editor compatível."
fi

echo "=== EXPORTAÇÃO DO RELATÓRIO CONCLUÍDA ==="
echo "Relatório Markdown: $MARKDOWN_REPORT"
if [ -f "$HTML_REPORT" ]; then
    echo "Relatório HTML: $HTML_REPORT"
fi
if [ -f "$PDF_REPORT" ]; then
    echo "Relatório PDF: $PDF_REPORT"
fi