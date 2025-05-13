package com.frouvier.backend.e2e.pages;

import com.frouvier.backend.e2e.config.WebDriverConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

/**
 * Page Object para a página da calculadora
 */
public class CalculatorPage extends BasePage {

    @FindBy(id = "firstOperand")
    private WebElement firstOperandInput;

    @FindBy(id = "secondOperand")
    private WebElement secondOperandInput;

    @FindBy(id = "operator")
    private WebElement operatorSelect;

    @FindBy(css = "button[type='submit']")
    private WebElement calculateButton;

    @FindBy(id = "resultContainer")
    private WebElement resultContainer;

    @FindBy(id = "resultDisplay")
    private WebElement resultDisplay;

    @FindBy(id = "historyList")
    private WebElement historyList;

    @FindBy(css = ".user-info span")
    private WebElement userInfoSpan;

    @FindBy(css = ".user-info a.btn")
    private WebElement logoutButton;

    /**
     * Construtor da página da calculadora
     */
    public CalculatorPage() {
        super();
    }

    /**
     * Navega para a página da calculadora
     * @return A própria página para encadeamento de métodos
     */
    public CalculatorPage navigateToCalculatorPage() {
        navigateTo(WebDriverConfig.getBaseUrl() + "/calculator");
        return this;
    }

    /**
     * Preenche o primeiro operando
     * @param value Valor do primeiro operando
     * @return A própria página para encadeamento de métodos
     */
    public CalculatorPage enterFirstOperand(String value) {
        waitForElementVisible(firstOperandInput).clear();
        firstOperandInput.sendKeys(value);
        return this;
    }

    /**
     * Preenche o segundo operando
     * @param value Valor do segundo operando
     * @return A própria página para encadeamento de métodos
     */
    public CalculatorPage enterSecondOperand(String value) {
        waitForElementVisible(secondOperandInput).clear();
        secondOperandInput.sendKeys(value);
        return this;
    }

    /**
     * Seleciona a operação
     * @param operator Operador (+, -, *, /)
     * @return A própria página para encadeamento de métodos
     */
    public CalculatorPage selectOperator(String operator) {
        Select select = new Select(waitForElementVisible(operatorSelect));
        select.selectByValue(operator);
        return this;
    }

    /**
     * Clica no botão de calcular
     * @return A própria página para encadeamento de métodos
     */
    public CalculatorPage clickCalculateButton() {
        waitForElementClickable(calculateButton).click();
        return this;
    }

    /**
     * Realiza uma operação completa
     * @param firstOperand Primeiro operando
     * @param secondOperand Segundo operando
     * @param operator Operador (+, -, *, /)
     * @return A própria página para encadeamento de métodos
     */
    public CalculatorPage performCalculation(String firstOperand, String secondOperand, String operator) {
        enterFirstOperand(firstOperand);
        enterSecondOperand(secondOperand);
        selectOperator(operator);
        clickCalculateButton();
        return this;
    }

    /**
     * Obtém o resultado da operação
     * @return Texto do resultado
     */
    public String getResult() {
        wait.until(ExpectedConditions.visibilityOf(resultContainer));
        return waitForElementVisible(resultDisplay).getText();
    }

    /**
     * Verifica se o resultado está visível
     * @return true se o resultado estiver visível, false caso contrário
     */
    public boolean isResultVisible() {
        try {
            // Aguardar explicitamente pela visibilidade do container de resultado
            wait.until(ExpectedConditions.visibilityOf(resultContainer));
            // Aguardar explicitamente pela visibilidade do display de resultado
            wait.until(ExpectedConditions.visibilityOf(resultDisplay));
            // Aguardar explicitamente pelo texto não vazio no display de resultado
            wait.until(driver -> !resultDisplay.getText().trim().isEmpty());
            return true;
        } catch (Exception e) {
            System.out.println("Erro ao verificar resultado: " + e.getMessage());
            return false;
        }
    }

    /**
     * Verifica se um cálculo específico está no histórico
     * @param calculationText Texto do cálculo a ser verificado
     * @return true se o cálculo estiver no histórico, false caso contrário
     */
    public boolean isCalculationInHistory(String calculationText) {
        try {
            // Aguardar explicitamente pela visibilidade da lista de histórico
            wait.until(ExpectedConditions.visibilityOf(historyList));
            
            // Aguardar um pouco para garantir que o histórico foi atualizado
            Thread.sleep(2000);
            
            // Usar JavaScript para obter o texto do histórico (mais confiável)
            JavascriptExecutor js = (JavascriptExecutor) driver;
            String historyContent = (String) js.executeScript(
                "return document.getElementById('historyList').textContent");
            
            // Verificar se o texto do cálculo está no conteúdo do histórico
            if (historyContent.contains(calculationText)) {
                return true;
            }
            
            // Se não encontrou pelo JavaScript, tenta pelo método tradicional
            List<WebElement> historyItems = historyList.findElements(
                By.cssSelector(".history-item"));
            
            for (WebElement item : historyItems) {
                if (item.getText().contains(calculationText)) {
                    return true;
                }
            }
            
            // Imprimir o conteúdo do histórico para depuração
            System.out.println("Conteúdo do histórico: " + historyContent);
            System.out.println("Texto procurado: " + calculationText);
            
            return false;
        } catch (Exception e) {
            System.out.println("Erro ao verificar histórico: " + e.getMessage());
            return false;
        }
    }

    /**
     * Obtém o texto de boas-vindas com o nome do usuário
     * @return Texto de boas-vindas
     */
    public String getUserWelcomeText() {
        return waitForElementVisible(userInfoSpan).getText();
    }

    /**
     * Clica no botão de logout
     * @return Uma nova instância da página de login
     */
    public LoginPage clickLogoutButton() {
        waitForElementClickable(logoutButton).click();
        return new LoginPage();
    }

    /**
     * Verifica se está na página da calculadora
     * @return true se estiver na página da calculadora, false caso contrário
     */
    public boolean isAtCalculatorPage() {
        return currentUrlContains("/calculator");
    }
}