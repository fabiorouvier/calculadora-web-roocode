package com.frouvier.backend.e2e.pages;

import com.frouvier.backend.e2e.config.WebDriverConfig;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Page Object para a página de registro
 */
public class RegisterPage extends BasePage {

    @FindBy(id = "username")
    private WebElement usernameInput;

    @FindBy(id = "password")
    private WebElement passwordInput;

    @FindBy(css = "button[type='submit']")
    private WebElement registerButton;

    @FindBy(css = ".error-message")
    private WebElement errorMessage;

    @FindBy(css = ".success-message")
    private WebElement successMessage;

    /**
     * Construtor da página de registro
     */
    public RegisterPage() {
        super();
    }

    /**
     * Navega para a página de registro
     * @return A própria página para encadeamento de métodos
     */
    public RegisterPage navigateToRegisterPage() {
        navigateTo(WebDriverConfig.getBaseUrl() + "/register");
        return this;
    }

    /**
     * Preenche o campo de usuário
     * @param username Nome de usuário
     * @return A própria página para encadeamento de métodos
     */
    public RegisterPage enterUsername(String username) {
        waitForElementVisible(usernameInput).clear();
        usernameInput.sendKeys(username);
        return this;
    }

    /**
     * Preenche o campo de senha
     * @param password Senha
     * @return A própria página para encadeamento de métodos
     */
    public RegisterPage enterPassword(String password) {
        waitForElementVisible(passwordInput).clear();
        passwordInput.sendKeys(password);
        return this;
    }

    /**
     * Clica no botão de registro
     * @return A própria página para encadeamento de métodos
     */
    public RegisterPage clickRegisterButton() {
        waitForElementClickable(registerButton).click();
        return this;
    }

    /**
     * Realiza o registro com as credenciais fornecidas
     * @param username Nome de usuário
     * @param password Senha
     * @return A própria página para encadeamento de métodos
     */
    public RegisterPage register(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickRegisterButton();
        return this;
    }

    /**
     * Verifica se está na página de registro
     * @return true se estiver na página de registro, false caso contrário
     */
    public boolean isAtRegisterPage() {
        try {
            // Verificar se a URL contém "/register"
            boolean urlCheck = currentUrlContains("/register");
            
            // Verificar se os elementos do formulário de registro estão presentes
            boolean elementsCheck = false;
            try {
                wait.until(ExpectedConditions.visibilityOf(usernameInput));
                wait.until(ExpectedConditions.visibilityOf(passwordInput));
                wait.until(ExpectedConditions.visibilityOf(registerButton));
                elementsCheck = true;
            } catch (Exception e) {
                System.out.println("Elementos do formulário de registro não encontrados: " + e.getMessage());
            }
            
            // Retornar true se a URL contém "/register" ou se os elementos do formulário estão presentes
            return urlCheck || elementsCheck;
        } catch (Exception e) {
            System.out.println("Erro ao verificar página de registro: " + e.getMessage());
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

    /**
     * Verifica se há mensagens de validação na página
     * @return true se houver mensagens de validação, false caso contrário
     */
    public boolean hasValidationMessages() {
        try {
            // Verificar se os campos obrigatórios estão marcados como inválidos
            return usernameInput.getAttribute("validity").contains("false") ||
                   passwordInput.getAttribute("validity").contains("false");
        } catch (Exception e) {
            return false;
        }
    }
}