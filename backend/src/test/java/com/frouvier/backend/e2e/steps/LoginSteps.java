package com.frouvier.backend.e2e.steps;

import com.frouvier.backend.e2e.pages.BasePage;
import com.frouvier.backend.e2e.pages.CalculatorPage;
import com.frouvier.backend.e2e.pages.LoginPage;
import com.frouvier.backend.e2e.pages.RegisterPage;
import org.openqa.selenium.WebDriver;
import com.frouvier.backend.e2e.config.WebDriverConfig;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import org.junit.jupiter.api.Assertions;

/**
 * Step definitions para os cenários de login e logout
 */
public class LoginSteps {

    private LoginPage loginPage;
    private CalculatorPage calculatorPage;
    private WebDriver driver;
    
    public LoginSteps() {
        this.driver = WebDriverConfig.getDriver();
    }
    
    /**
     * Verifica se a URL atual contém o texto especificado
     * @param text Texto a ser verificado na URL
     * @return true se a URL contém o texto, false caso contrário
     */
    private boolean currentUrlContains(String text) {
        try {
            String currentUrl = driver.getCurrentUrl();
            return currentUrl != null && currentUrl.contains(text);
        } catch (Exception e) {
            return false;
        }
    }

    @Dado("que estou na página de login")
    public void queEstouNaPaginaDeLogin() {
        loginPage = new LoginPage();
        loginPage.navigateToLoginPage();
        Assertions.assertTrue(loginPage.isAtLoginPage(), "Não está na página de login");
    }

    @Quando("eu preencho o campo {string} com {string}")
    public void euPreenchoOCampoCom(String campo, String valor) {
        // Verifica se estamos na página de registro
        if (currentUrlContains("/register")) {
            RegisterPage registerPage = new RegisterPage();
            if (campo.equals("username")) {
                registerPage.enterUsername(valor);
            } else if (campo.equals("password")) {
                registerPage.enterPassword(valor);
            } else {
                throw new IllegalArgumentException("Campo não reconhecido: " + campo);
            }
        } else {
            // Estamos na página de login
            if (loginPage == null) {
                loginPage = new LoginPage();
            }
            
            if (campo.equals("username")) {
                loginPage.enterUsername(valor);
            } else if (campo.equals("password")) {
                loginPage.enterPassword(valor);
            } else {
                throw new IllegalArgumentException("Campo não reconhecido: " + campo);
            }
        }
    }

    @E("eu clico no botão {string}")
    public void euClicoNoBotao(String botao) {
        if (botao.equals("Entrar")) {
            loginPage.clickLoginButton();
        } else if (botao.equals("Sair")) {
            loginPage = calculatorPage.clickLogoutButton();
        } else if (botao.equals("Calcular")) {
            calculatorPage.clickCalculateButton();
        } else if (botao.equals("Registrar")) {
            // Acessa a página de registro através da injeção de dependência
            RegisterPage registerPage = new RegisterPage();
            registerPage.clickRegisterButton();
        } else {
            throw new IllegalArgumentException("Botão não reconhecido: " + botao);
        }
    }

    @Então("eu devo ser redirecionado para a página da calculadora")
    public void euDevoSerRedirecionadoParaAPaginaDaCalculadora() {
        calculatorPage = new CalculatorPage();
        Assertions.assertTrue(calculatorPage.isAtCalculatorPage(), 
                "Não foi redirecionado para a página da calculadora");
    }

    @E("eu devo ver a mensagem {string}")
    public void euDevoVerAMensagem(String mensagem) {
        String welcomeText = calculatorPage.getUserWelcomeText();
        Assertions.assertEquals(mensagem, welcomeText, 
                "A mensagem de boas-vindas não corresponde ao esperado");
    }

    @Então("eu devo permanecer na página de login")
    public void euDevoPermanecerNaPaginaDeLogin() {
        Assertions.assertTrue(loginPage.isAtLoginPage(), 
                "Não permaneceu na página de login");
    }

    @E("eu devo ver uma mensagem de erro")
    public void euDevoVerUmaMensagemDeErro() {
        Assertions.assertTrue(loginPage.hasErrorMessage(), 
                "Não foi exibida uma mensagem de erro");
    }

    @Dado("que estou logado no sistema com o usuário {string} e senha {string}")
    public void queEstouLogadoNoSistemaComOUsuarioESenha(String usuario, String senha) {
        loginPage = new LoginPage();
        loginPage.navigateToLoginPage();
        loginPage.login(usuario, senha);
        calculatorPage = new CalculatorPage();
        Assertions.assertTrue(calculatorPage.isAtCalculatorPage(), 
                "Não foi possível fazer login com as credenciais fornecidas");
    }

    @Então("eu devo ser redirecionado para a página de login")
    public void euDevoSerRedirecionadoParaAPaginaDeLogin() {
        loginPage = new LoginPage();
        Assertions.assertTrue(loginPage.isAtLoginPage(), 
                "Não foi redirecionado para a página de login");
    }
}