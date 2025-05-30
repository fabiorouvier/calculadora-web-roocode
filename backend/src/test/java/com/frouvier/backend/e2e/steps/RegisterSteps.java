package com.frouvier.backend.e2e.steps;

import com.frouvier.backend.e2e.pages.LoginPage;
import com.frouvier.backend.e2e.pages.RegisterPage;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import org.junit.jupiter.api.Assertions;

/**
 * Step definitions para os cenários de registro
 */
public class RegisterSteps {

    private RegisterPage registerPage;
    private LoginPage loginPage;

    @Dado("que estou na página de registro")
    public void queEstouNaPaginaDeRegistro() {
        registerPage = new RegisterPage();
        registerPage.navigateToRegisterPage();
        Assertions.assertTrue(registerPage.isAtRegisterPage(), "Não está na página de registro");
    }

    // Reutilizando os steps de LoginSteps para preencher campos e clicar em botões

    @Quando("eu clico no botão {string} sem preencher os campos")
    public void euClicoNoBotaoSemPreencherOsCampos(String botao) {
        // Cria uma instância de RegisterPage e clica no botão diretamente
        RegisterPage registerPage = new RegisterPage();
        registerPage.clickRegisterButton();
    }

    @Então("eu devo permanecer na página de registro")
    public void euDevoPermanecerNaPaginaDeRegistro() {
        Assertions.assertTrue(registerPage.isAtRegisterPage(), 
                "Não permaneceu na página de registro");
    }

    @E("eu devo ver uma mensagem de erro de usuário existente")
    public void euDevoVerUmaMensagemDeErroDeUsuarioExistente() {
        Assertions.assertTrue(registerPage.hasErrorMessage(), 
                "Não foi exibida uma mensagem de erro");
        String errorMessage = registerPage.getErrorMessage();
        Assertions.assertTrue(errorMessage.contains("já existe") || 
                              errorMessage.contains("already exists"), 
                "A mensagem de erro não indica que o usuário já existe");
    }

    @E("eu devo ver uma mensagem de sucesso")
    public void euDevoVerUmaMensagemDeSucesso() {
        // Verificar se há uma mensagem de sucesso na página de login
        loginPage = new LoginPage();
        Assertions.assertTrue(loginPage.isAtLoginPage(),
                "Não foi redirecionado para a página de login");
        
        Assertions.assertTrue(loginPage.hasSuccessMessage(),
                "Não foi exibida uma mensagem de sucesso");
        
        String successMessage = loginPage.getSuccessMessage();
        Assertions.assertTrue(successMessage.contains("sucesso") ||
                              successMessage.contains("success"),
                "A mensagem não indica que o registro foi bem-sucedido");
    }

    @E("eu devo ver mensagens de validação")
    public void euDevoVerMensagensDeValidacao() {
        Assertions.assertTrue(registerPage.hasValidationMessages(), 
                "Não foram exibidas mensagens de validação");
    }
}