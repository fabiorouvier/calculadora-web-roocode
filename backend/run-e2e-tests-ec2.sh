#!/bin/bash

# Script para executar os testes E2E apontando para a EC2 usando o Edge

# Define o navegador a ser usado (ignorando a verificação de instalação)
export BROWSER=edge
echo "Configurado para usar Edge para os testes..."

echo "Executando os testes E2E apontando para a EC2 (http://44.204.82.30:3000)..."

# Executa os testes usando o Edge
./mvnw test -Dtest=com.frouvier.backend.e2e.CucumberTestRunner

echo "Testes E2E concluídos"
echo "Relatório disponível em: target/cucumber-reports/report.html"