# language: pt
Funcionalidade: Registro no sistema
  Como um novo usuário da calculadora web
  Eu quero me registrar no sistema
  Para poder criar uma conta e utilizar as funcionalidades da calculadora

  Cenário: Registro com sucesso
    Dado que estou na página de registro
    Quando eu preencho o campo "username" com "novo_usuario"
    E eu preencho o campo "password" com "senha123"
    E eu clico no botão "Registrar"
    Então eu devo ser redirecionado para a página de login
    E eu devo ver uma mensagem de sucesso

  Cenário: Registro com nome de usuário já existente
    Dado que estou na página de registro
    Quando eu preencho o campo "username" com "admin"
    E eu preencho o campo "password" com "senha123"
    E eu clico no botão "Registrar"
    Então eu devo permanecer na página de registro
    E eu devo ver uma mensagem de erro de usuário existente

  Cenário: Registro com campos vazios
    Dado que estou na página de registro
    Quando eu clico no botão "Registrar" sem preencher os campos
    Então eu devo permanecer na página de registro
    E eu devo ver mensagens de validação