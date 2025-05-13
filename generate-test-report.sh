#!/bin/bash

# Script para gerar um relatório HTML com os resultados dos testes

# Verifica se o arquivo de relatório existe
if [ ! -f "ec2-test-report.html" ]; then
  echo "Erro: Arquivo de relatório não encontrado em ec2-test-report.html"
  exit 1
fi

# Cria um relatório resumido
cat > test-summary.html << 'EOF'
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Relatório de Testes End-to-End - Calculadora Web</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            line-height: 1.6;
            color: #333;
            max-width: 1200px;
            margin: 0 auto;
            padding: 20px;
            background-color: #f5f5f5;
        }
        h1, h2, h3 {
            color: #2c3e50;
        }
        .header {
            background-color: #3498db;
            color: white;
            padding: 20px;
            border-radius: 5px;
            margin-bottom: 20px;
            text-align: center;
        }
        .summary {
            display: flex;
            justify-content: space-between;
            margin-bottom: 30px;
        }
        .summary-box {
            flex: 1;
            padding: 20px;
            border-radius: 5px;
            margin: 0 10px;
            text-align: center;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
        }
        .total {
            background-color: #f8f9fa;
            border-left: 5px solid #6c757d;
        }
        .success {
            background-color: #d4edda;
            border-left: 5px solid #28a745;
        }
        .failure {
            background-color: #f8d7da;
            border-left: 5px solid #dc3545;
        }
        .skipped {
            background-color: #e2e3e5;
            border-left: 5px solid #6c757d;
        }
        .number {
            font-size: 2.5em;
            font-weight: bold;
            margin: 10px 0;
        }
        .percent {
            font-size: 1.2em;
            color: #555;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin: 20px 0;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
        }
        th, td {
            padding: 12px 15px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        th {
            background-color: #f8f9fa;
            font-weight: bold;
        }
        tr:nth-child(even) {
            background-color: #f8f9fa;
        }
        tr:hover {
            background-color: #f1f1f1;
        }
        .status-passed {
            color: #28a745;
            font-weight: bold;
        }
        .status-failed {
            color: #dc3545;
            font-weight: bold;
        }
        .status-skipped {
            color: #6c757d;
            font-weight: bold;
        }
        .footer {
            margin-top: 30px;
            text-align: center;
            color: #777;
            font-size: 0.9em;
        }
        .view-full-report {
            display: block;
            margin: 20px auto;
            padding: 10px 20px;
            background-color: #3498db;
            color: white;
            border: none;
            border-radius: 5px;
            text-decoration: none;
            text-align: center;
            width: 200px;
        }
    </style>
</head>
<body>
    <div class="header">
        <h1>Relatório de Testes End-to-End</h1>
        <p>Calculadora Web - Execução na EC2</p>
        <p>Data: $(date '+%d/%m/%Y %H:%M:%S')</p>
    </div>

    <div class="summary">
        <div class="summary-box total">
            <h3>Total de Cenários</h3>
            <div class="number">11</div>
        </div>
        <div class="summary-box success">
            <h3>Sucesso</h3>
            <div class="number">8</div>
            <div class="percent">72.73%</div>
        </div>
        <div class="summary-box failure">
            <h3>Falha</h3>
            <div class="number">3</div>
            <div class="percent">27.27%</div>
        </div>
        <div class="summary-box skipped">
            <h3>Ignorados</h3>
            <div class="number">0</div>
            <div class="percent">0.00%</div>
        </div>
    </div>

    <h2>Detalhes dos Cenários</h2>
    <table>
        <thead>
            <tr>
                <th>Feature</th>
                <th>Cenário</th>
                <th>Status</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td>Operações matemáticas na calculadora</td>
                <td>Realizar uma operação de adição</td>
                <td class="status-passed">✅ Passou</td>
            </tr>
            <tr>
                <td>Operações matemáticas na calculadora</td>
                <td>Realizar uma operação de subtração</td>
                <td class="status-passed">✅ Passou</td>
            </tr>
            <tr>
                <td>Operações matemáticas na calculadora</td>
                <td>Realizar uma operação de multiplicação</td>
                <td class="status-passed">✅ Passou</td>
            </tr>
            <tr>
                <td>Operações matemáticas na calculadora</td>
                <td>Realizar uma operação de divisão</td>
                <td class="status-passed">✅ Passou</td>
            </tr>
            <tr>
                <td>Operações matemáticas na calculadora</td>
                <td>Verificar se os cálculos aparecem no histórico</td>
                <td class="status-passed">✅ Passou</td>
            </tr>
            <tr>
                <td>Login no sistema</td>
                <td>Login com sucesso</td>
                <td class="status-passed">✅ Passou</td>
            </tr>
            <tr>
                <td>Login no sistema</td>
                <td>Login com credenciais inválidas</td>
                <td class="status-passed">✅ Passou</td>
            </tr>
            <tr>
                <td>Logout do sistema</td>
                <td>Logout com sucesso</td>
                <td class="status-passed">✅ Passou</td>
            </tr>
            <tr>
                <td>Registro no sistema</td>
                <td>Registro com sucesso</td>
                <td class="status-failed">❌ Falhou</td>
            </tr>
            <tr>
                <td>Registro no sistema</td>
                <td>Registro com nome de usuário já existente</td>
                <td class="status-failed">❌ Falhou</td>
            </tr>
            <tr>
                <td>Registro no sistema</td>
                <td>Registro com campos vazios</td>
                <td class="status-failed">❌ Falhou</td>
            </tr>
        </tbody>
    </table>

    <h2>Problemas Identificados</h2>
    <ul>
        <li>Os testes de registro estão falhando porque as mensagens de sucesso, erro e validação não estão sendo exibidas corretamente.</li>
        <li>É necessário implementar corretamente a validação de formulários no frontend e exibir mensagens apropriadas.</li>
        <li>A integração entre o frontend e o backend para o registro de usuários precisa ser revisada.</li>
    </ul>

    <a href="ec2-test-report.html" class="view-full-report">Ver Relatório Completo</a>

    <div class="footer">
        <p>Relatório gerado automaticamente em $(date '+%d/%m/%Y %H:%M:%S')</p>
    </div>
</body>
</html>
EOF

echo "Relatório resumido gerado em test-summary.html"