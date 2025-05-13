package com.frouvier.backend.e2e;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.FILTER_TAGS_PROPERTY_NAME;

/**
 * Classe responsável por executar os testes Cucumber
 */
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com.frouvier.backend.e2e.steps,com.frouvier.backend.e2e.config")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME, value = "pretty, html:target/cucumber-reports/report.html, json:target/cucumber-reports/report.json")
public class CucumberTestRunner {
    // Esta classe serve apenas como ponto de entrada para os testes Cucumber
}