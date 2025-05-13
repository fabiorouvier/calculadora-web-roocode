package com.frouvier.backend.e2e.pages;

import com.frouvier.backend.e2e.config.WebDriverConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Classe base para todas as páginas (Page Objects)
 */
public abstract class BasePage {
    
    protected WebDriver driver;
    protected WebDriverWait wait;
    
    /**
     * Construtor base para todas as páginas
     */
    public BasePage() {
        this.driver = WebDriverConfig.getDriver();
        // Aumentar o tempo de espera para 30 segundos para lidar com latência da EC2
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        PageFactory.initElements(driver, this);
    }
    
    /**
     * Navega para uma URL específica
     * @param url URL para navegação
     */
    public void navigateTo(String url) {
        driver.get(url);
    }
    
    /**
     * Aguarda até que um elemento esteja visível
     * @param element Elemento a ser aguardado
     * @return O elemento após estar visível
     */
    protected WebElement waitForElementVisible(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element));
    }
    
    /**
     * Aguarda até que um elemento esteja clicável
     * @param element Elemento a ser aguardado
     * @return O elemento após estar clicável
     */
    protected WebElement waitForElementClickable(WebElement element) {
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }
    
    /**
     * Aguarda até que um elemento esteja presente no DOM
     * @param locator Localizador do elemento
     * @return O elemento após estar presente
     */
    protected WebElement waitForElementPresent(By locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }
    
    /**
     * Clica em um elemento com JavaScript (útil quando o clique normal falha)
     * @param element Elemento a ser clicado
     */
    protected void clickWithJS(WebElement element) {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", element);
    }
    
    /**
     * Verifica se a URL atual contém um texto específico
     * @param urlText Texto a ser verificado na URL
     * @return true se a URL contém o texto, false caso contrário
     */
    public boolean currentUrlContains(String urlText) {
        return driver.getCurrentUrl().contains(urlText);
    }
    
    /**
     * Obtém o título da página atual
     * @return Título da página
     */
    public String getPageTitle() {
        return driver.getTitle();
    }
}