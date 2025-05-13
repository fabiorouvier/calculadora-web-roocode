# language: pt
Funcionalidade: Login no sistema
  Como um usuário da calculadora web
  Eu quero fazer login no sistema
  Para poder utilizar as funcionalidades da calculadora

  Cenário: Login com sucesso
    Dado que estou na página de login
    Quando eu preencho o campo "username" com "admin"
    E eu preencho o campo "password" com "admin123"
    E eu clico no botão "Entrar"
    Então eu devo ser redirecionado para a página da calculadora
    E eu devo ver a mensagem "Olá, admin"

  Cenário: Login com credenciais inválidas
    Dado que estou na página de login
    Quando eu preencho o campo "username" com "usuario_invalido"
    E eu preencho o campo "password" com "senha_invalida"
    E eu clico no botão "Entrar"
    Então eu devo permanecer na página de login
    E eu devo ver uma mensagem de erro