package com.frouvier.backend.e2e.config;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

/**
 * Hooks para gerenciar o ciclo de vida do WebDriver durante os testes
 */
public class CucumberHooks {

    /**
     * Inicializa o WebDriver antes de cada cenário
     */
    @Before
    public void setupTest() {
        // Inicializa o WebDriver com Edge por padrão
        WebDriverConfig.initializeDriver("edge");
    }

    /**
     * Fecha o WebDriver após cada cenário e captura screenshot em caso de falha
     * @param scenario Cenário atual
     */
    @After
    public void tearDown(Scenario scenario) {
        WebDriver driver = WebDriverConfig.getDriver();
        
        // Captura screenshot se o cenário falhar
        if (scenario.isFailed() && driver != null) {
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", "screenshot-" + scenario.getName());
        }
        
        // Fecha o WebDriver
        WebDriverConfig.quitDriver();
    }
}