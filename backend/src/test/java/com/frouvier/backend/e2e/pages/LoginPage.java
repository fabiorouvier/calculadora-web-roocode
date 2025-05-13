package com.frouvier.backend.e2e.pages;

import com.frouvier.backend.e2e.config.WebDriverConfig;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Page Object para a página de login
 */
public class LoginPage extends BasePage {

    @FindBy(id = "username")
    private WebElement usernameInput;

    @FindBy(id = "password")
    private WebElement passwordInput;

    @FindBy(css = "button[type='submit']")
    private WebElement loginButton;

    @FindBy(css = ".error-message")
    private WebElement errorMessage;
    
    @FindBy(css = ".success-message")
    private WebElement successMessage;

    /**
     * Construtor da página de login
     */
    public LoginPage() {
        super();
    }

    /**
     * Navega para a página de login
     * @return A própria página para encadeamento de métodos
     */
    public LoginPage navigateToLoginPage() {
        navigateTo(WebDriverConfig.getBaseUrl() + "/login");
        return this;
    }

    /**
     * Preenche o campo de usuário
     * @param username Nome de usuário
     * @return A própria página para encadeamento de métodos
     */
    public LoginPage enterUsername(String username) {
        waitForElementVisible(usernameInput).clear();
        usernameInput.sendKeys(username);
        return this;
    }

    /**
     * Preenche o campo de senha
     * @param password Senha
     * @return A própria página para encadeamento de métodos
     */
    public LoginPage enterPassword(String password) {
        waitForElementVisible(passwordInput).clear();
        passwordInput.sendKeys(password);
        return this;
    }

    /**
     * Clica no botão de login
     * @return A própria página para encadeamento de métodos
     */
    public LoginPage clickLoginButton() {
        waitForElementClickable(loginButton).click();
        return this;
    }

    /**
     * Realiza o login com as credenciais fornecidas
     * @param username Nome de usuário
     * @param password Senha
     * @return A própria página para encadeamento de métodos
     */
    public LoginPage login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
        return this;
    }

    /**
     * Verifica se está na página de login
     * @return true se estiver na página de login, false caso contrário
     */
    public boolean isAtLoginPage() {
        try {
            // Verificar se a URL contém "/login"
            boolean urlCheck = currentUrlContains("/login");
            
            // Verificar se os elementos do formulário de login estão presentes
            boolean elementsCheck = false;
            try {
                wait.until(ExpectedConditions.visibilityOf(usernameInput));
                wait.until(ExpectedConditions.visibilityOf(passwordInput));
                wait.until(ExpectedConditions.visibilityOf(loginButton));
                elementsCheck = true;
            } catch (Exception e) {
                System.out.println("Elementos do formulário de login não encontrados: " + e.getMessage());
            }
            
            // Retornar true se a URL contém "/login" ou se os elementos do formulário estão presentes
            return urlCheck || elementsCheck;
        } catch (Exception e) {
            System.out.println("Erro ao verificar página de login: " + e.getMessage());
            return false;
        }
    }

    /**
     * Verifica se há mensagem de erro na página
     * @return true se houver mensagem de erro, false caso contrário
     */
    public boolean hasErrorMessage() {
        try {
            return waitForElementVisible(errorMessage).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Obtém o texto da mensagem de erro
     * @return Texto da mensagem de erro
     */
    public String getErrorMessage() {
        return waitForElementVisible(errorMessage).getText();
    }
    
    /**
     * Verifica se há mensagem de sucesso na página
     * @return true se houver mensagem de sucesso, false caso contrário
     */
    public boolean hasSuccessMessage() {
        try {
            return waitForElementVisible(successMessage).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Obtém o texto da mensagem de sucesso
     * @return Texto da mensagem de sucesso
     */
    public String getSuccessMessage() {
        return waitForElementVisible(successMessage).getText();
    }
}