# language: pt
Funcionalidade: Logout do sistema
  Como um usuário logado na calculadora web
  Eu quero fazer logout do sistema
  Para encerrar minha sessão com segurança

  Cenário: Logout com sucesso
    Dado que estou logado no sistema com o usuário "admin" e senha "admin123"
    E que estou na página da calculadora
    Quando eu clico no botão "Sair"
    Então eu devo ser redirecionado para a página de login