#!/usr/bin/env python3
"""
Script para converter o relatório Markdown para HTML que pode ser facilmente convertido para PDF.
"""

import os
import sys
import argparse
from datetime import datetime

def convert_md_to_pdf(markdown_file, output_pdf=None):
    """
    Converte um arquivo Markdown para HTML que pode ser facilmente convertido para PDF.
    
    Args:
        markdown_file (str): Caminho para o arquivo Markdown
        output_pdf (str, optional): Caminho sugerido para o arquivo PDF de saída
    
    Returns:
        str: Caminho para o arquivo HTML gerado
    """
    if not os.path.exists(markdown_file):
        print(f"Erro: Arquivo {markdown_file} não encontrado.")
        return None
    
    # Se o output_pdf não for especificado, usa o mesmo nome do arquivo markdown com extensão .pdf
    if output_pdf is None:
        output_pdf = os.path.splitext(markdown_file)[0] + '.pdf'
    
    # Lê o conteúdo do arquivo Markdown
    with open(markdown_file, 'r', encoding='utf-8') as f:
        markdown_content = f.read()
    
    # Cria um arquivo HTML temporário com o conteúdo Markdown formatado
    html_file = os.path.splitext(markdown_file)[0] + '.html'
    
    # Cria o conteúdo HTML
    html_head = """<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Relatório de Testes End-to-End</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            line-height: 1.6;
            max-width: 800px;
            margin: 0 auto;
            padding: 20px;
        }
        h1 {
            color: #2c3e50;
            border-bottom: 2px solid #3498db;
            padding-bottom: 10px;
        }
        h2 {
            color: #2c3e50;
            margin-top: 30px;
        }
        h3 {
            color: #3498db;
            margin-top: 25px;
        }
        table {
            border-collapse: collapse;
            width: 100%;
            margin: 20px 0;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
        }
        tr:nth-child(even) {
            background-color: #f9f9f9;
        }
        .success {
            color: #27ae60;
        }
        .failure {
            color: #e74c3c;
        }
        .skipped {
            color: #f39c12;
        }
    </style>
</head>
<body>
"""
    
    html_foot = """
</body>
</html>
"""
    
    # Processa o conteúdo Markdown para HTML
    content_html = markdown_content
    content_html = content_html.replace('# ', '<h1>')
    content_html = content_html.replace('\n## ', '</h1><h2>')
    content_html = content_html.replace('\n### ', '</h2><h3>')
    content_html = content_html.replace('\n\n', '</h3><p>')
    content_html = content_html.replace('\n', '<br>')
    content_html = content_html.replace('✅', '<span class="success">✅</span>')
    content_html = content_html.replace('❌', '<span class="failure">❌</span>')
    content_html = content_html.replace('⏭️', '<span class="skipped">⏭️</span>')
    
    # Combina tudo
    html_content = html_head + content_html + html_foot
    
    with open(html_file, 'w', encoding='utf-8') as f:
        f.write(html_content)
    
    print(f"Arquivo HTML temporário criado: {html_file}")
    print(f"Para gerar um PDF, você pode:")
    print("  1. Abrir o arquivo HTML em um navegador")
    print("  2. Usar a função de impressão do navegador (Ctrl+P ou Cmd+P)")
    print("  3. Selecionar 'Salvar como PDF' como destino da impressão")
    print(f"  4. Salvar o arquivo como {output_pdf}")
    
    return html_file

def main():
    parser = argparse.ArgumentParser(description='Converte relatório Markdown para PDF')
    parser.add_argument('markdown_file', help='Caminho para o arquivo Markdown')
    parser.add_argument('-o', '--output', help='Caminho para o arquivo PDF de saída')
    
    args = parser.parse_args()
    
    html_file = convert_md_to_pdf(args.markdown_file, args.output)
    if html_file:
        print("\nConversão para HTML concluída com sucesso!")

if __name__ == "__main__":
    main()