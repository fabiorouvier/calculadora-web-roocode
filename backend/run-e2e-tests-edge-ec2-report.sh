#!/bin/bash

# Script para executar os testes E2E apontando para a EC2 usando o Edge
# e gerar um relatório detalhado com status, percentual de sucesso/falha e tempo de execução

echo "=== INICIANDO EXECUÇÃO DOS TESTES E2E COM EDGE APONTANDO PARA EC2 ==="
echo "URL: http://100.26.194.86:3000"
echo "Navegador: Edge"

# Registra o tempo de início
INICIO=$(date +%s)

# Define o navegador a ser usado
export BROWSER=edge
echo "Configurado para usar Edge para os testes..."

# Executa os testes usando o Edge
echo "Executando os testes E2E..."
./mvnw test -Dtest=com.frouvier.backend.e2e.CucumberTestRunner

# Registra o tempo de fim
FIM=$(date +%s)

# Calcula o tempo de execução
TEMPO_EXECUCAO=$((FIM - INICIO))
MINUTOS=$((TEMPO_EXECUCAO / 60))
SEGUNDOS=$((TEMPO_EXECUCAO % 60))

echo "Testes E2E concluídos em $MINUTOS minutos e $SEGUNDOS segundos"
echo "Relatório HTML disponível em: target/cucumber-reports/report.html"

# Cria um script Python para analisar o relatório JSON e gerar estatísticas
cat > analyze_report.py << 'EOF'
#!/usr/bin/env python3
import json
import sys
from datetime import datetime

def analyze_cucumber_report(json_file):
    try:
        with open(json_file, 'r') as f:
            data = json.load(f)
    except Exception as e:
        print(f"Erro ao ler o arquivo JSON: {e}")
        return
    
    # Inicializa contadores
    total_scenarios = 0
    passed_scenarios = 0
    failed_scenarios = 0
    skipped_scenarios = 0
    
    # Lista para armazenar detalhes dos cenários
    scenario_details = []
    
    # Analisa cada feature
    for feature in data:
        feature_name = feature.get('name', 'Feature sem nome')
        
        # Analisa cada elemento (cenário ou background)
        for element in feature.get('elements', []):
            if element.get('type') == 'scenario':
                total_scenarios += 1
                scenario_name = element.get('name', 'Cenário sem nome')
                
                # Verifica o status do cenário
                status = 'passed'
                for step in element.get('steps', []):
                    step_result = step.get('result', {})
                    step_status = step_result.get('status', 'unknown')
                    
                    if step_status == 'failed':
                        status = 'failed'
                        break
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
                scenario_details.append({
                    'feature': feature_name,
                    'scenario': scenario_name,
                    'status': status
                })
    
    # Calcula percentuais
    pass_percentage = (passed_scenarios / total_scenarios * 100) if total_scenarios > 0 else 0
    fail_percentage = (failed_scenarios / total_scenarios * 100) if total_scenarios > 0 else 0
    skip_percentage = (skipped_scenarios / total_scenarios * 100) if total_scenarios > 0 else 0
    
    # Imprime o relatório
    print("\n===== RELATÓRIO DE EXECUÇÃO DOS TESTES E2E =====")
    print(f"Data e hora: {datetime.now().strftime('%d/%m/%Y %H:%M:%S')}")
    print(f"Total de cenários: {total_scenarios}")
    print(f"Cenários com sucesso: {passed_scenarios} ({pass_percentage:.2f}%)")
    print(f"Cenários com falha: {failed_scenarios} ({fail_percentage:.2f}%)")
    print(f"Cenários ignorados: {skipped_scenarios} ({skip_percentage:.2f}%)")
    
    print("\n--- Detalhes dos Cenários ---")
    for detail in scenario_details:
        status_symbol = "✅" if detail['status'] == 'passed' else "❌" if detail['status'] == 'failed' else "⏭️"
        print(f"{status_symbol} {detail['feature']} - {detail['scenario']}")

if __name__ == "__main__":
    if len(sys.argv) > 1:
        analyze_cucumber_report(sys.argv[1])
    else:
        analyze_cucumber_report("target/cucumber-reports/report.json")
EOF

# Torna o script Python executável
chmod +x analyze_report.py

# Executa o script Python para analisar o relatório
echo -e "\nAnalisando resultados dos testes..."
python3 analyze_report.py

echo -e "\n=== EXECUÇÃO DOS TESTES E2E FINALIZADA ==="