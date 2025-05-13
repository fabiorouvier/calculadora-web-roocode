#!/bin/bash

# Script para verificar o status dos testes e gerar o relatório quando eles terminarem

# Verifica se os testes estão em execução
check_tests_running() {
  ps aux | grep run-e2e-tests-ec2.sh | grep -v grep > /dev/null
  return $?
}

# Verifica se o arquivo de relatório existe
check_report_exists() {
  if [ -f "ec2-test-report.html" ]; then
    return 0
  else
    return 1
  fi
}

# Função para gerar o relatório
generate_report() {
  echo "Gerando relatório de testes..."
  ./generate-test-report.sh
  
  echo "Abrindo relatório de testes no navegador..."
  ./open-test-report.sh
}

# Loop principal
echo "Verificando o status dos testes..."
while check_tests_running; do
  echo "Os testes ainda estão em execução. Aguardando 10 segundos..."
  sleep 10
done

echo "Os testes foram concluídos."

# Verifica se o relatório foi gerado
if check_report_exists; then
  echo "O relatório de testes foi gerado com sucesso."
  generate_report
else
  echo "O relatório de testes não foi gerado. Verificando se houve algum erro..."
  
  # Verifica se houve algum erro na execução dos testes
  if [ -f "ec2-test-error.log" ]; then
    echo "Erro na execução dos testes:"
    cat ec2-test-error.log
  else
    echo "Não foi possível determinar o motivo da falha na geração do relatório."
    echo "Verifique o log de execução dos testes para mais informações."
  fi
fi

echo "Script concluído."