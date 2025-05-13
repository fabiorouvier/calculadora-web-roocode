package com.frouvier.backend.e2e.config;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Configuração do Spring para os testes Cucumber
 */
@CucumberContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class CucumberSpringConfiguration {
    // Esta classe serve apenas para configuração do contexto do Spring para o Cucumber
}