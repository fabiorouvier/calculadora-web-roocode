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
