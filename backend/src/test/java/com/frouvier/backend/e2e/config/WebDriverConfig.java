package com.frouvier.backend.e2e.config;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

import java.time.Duration;

/**
 * Configuração do WebDriver para os testes E2E
 */
public class WebDriverConfig {

    private static final String BASE_URL = "http://100.26.194.86:3000";
    private static WebDriver driver;

    /**
     * Inicializa o WebDriver com o navegador especificado
     * @param browser Nome do navegador (chrome, firefox, edge)
     * @return Instância do WebDriver configurada
     */
    public static WebDriver initializeDriver(String browser) {
        WebDriver webDriver;

        switch (browser.toLowerCase()) {
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                webDriver = new FirefoxDriver(firefoxOptions);
                break;
            case "edge":
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOptions = new EdgeOptions();
                // Desabilitar o modo headless para que o navegador seja visível
                edgeOptions.addArguments("--start-maximized");
                webDriver = new EdgeDriver(edgeOptions);
                break;
            case "safari":
                // Safari não precisa de WebDriverManager
                SafariOptions safariOptions = new SafariOptions();
                webDriver = new SafariDriver(safariOptions);
                break;
            case "chrome":
            default:
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--remote-allow-origins=*");
                webDriver = new ChromeDriver(chromeOptions);
                break;
        }

        webDriver.manage().window().maximize();
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        
        driver = webDriver;
        return webDriver;
    }

    /**
     * Obtém a instância atual do WebDriver
     * @return Instância do WebDriver
     */
    public static WebDriver getDriver() {
        if (driver == null) {
            String browser = System.getenv("BROWSER");
            if (browser == null || browser.isEmpty()) {
                browser = "chrome"; // Navegador padrão se não for especificado
            }
            driver = initializeDriver(browser);
        }
        return driver;
    }

    /**
     * Fecha o WebDriver
     */
    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    /**
     * Obtém a URL base da aplicação
     * @return URL base
     */
    public static String getBaseUrl() {
        return BASE_URL;
    }
}