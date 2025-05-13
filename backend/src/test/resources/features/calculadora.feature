# language: pt
Funcionalidade: Operações matemáticas na calculadora
  Como um usuário logado na calculadora web
  Eu quero realizar operações matemáticas
  Para obter resultados precisos e visualizar o histórico

  Contexto:
    Dado que estou logado no sistema com o usuário "admin" e senha "admin123"
    E que estou na página da calculadora

  Cenário: Realizar uma operação de adição
    Quando eu preencho o primeiro operando com "10"
    E eu preencho o segundo operando com "5"
    E eu seleciono a operação "+"
    E eu clico no botão "Calcular"
    Então eu devo ver o resultado "15,00"
    E o cálculo deve aparecer no histórico

  Cenário: Realizar uma operação de subtração
    Quando eu preencho o primeiro operando com "10"
    E eu preencho o segundo operando com "5"
    E eu seleciono a operação "-"
    E eu clico no botão "Calcular"
    Então eu devo ver o resultado "5,00"
    E o cálculo deve aparecer no histórico

  Cenário: Realizar uma operação de multiplicação
    Quando eu preencho o primeiro operando com "10"
    E eu preencho o segundo operando com "5"
    E eu seleciono a operação "*"
    E eu clico no botão "Calcular"
    Então eu devo ver o resultado "50,00"
    E o cálculo deve aparecer no histórico

  Cenário: Realizar uma operação de divisão
    Quando eu preencho o primeiro operando com "10"
    E eu preencho o segundo operando com "5"
    E eu seleciono a operação "/"
    E eu clico no botão "Calcular"
    Então eu devo ver o resultado "2,00"
    E o cálculo deve aparecer no histórico

  Cenário: Verificar se os cálculos aparecem no histórico
    Quando eu realizo uma operação de adição com "7" e "3"
    E eu realizo uma operação de subtração com "9" e "4"
    Então eu devo ver "7 + 3 = 10,00" no histórico
    E eu devo ver "9 - 4 = 5,00" no histórico