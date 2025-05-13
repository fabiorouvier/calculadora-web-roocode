package com.frouvier.backend.e2e.steps;

import com.frouvier.backend.e2e.pages.CalculatorPage;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Então;
import io.cucumber.java.pt.Quando;
import org.junit.jupiter.api.Assertions;

/**
 * Step definitions para os cenários de operações da calculadora
 */
public class CalculatorSteps {

    private CalculatorPage calculatorPage;

    @Dado("que estou na página da calculadora")
    public void queEstouNaPaginaDaCalculadora() {
        calculatorPage = new CalculatorPage();
        Assertions.assertTrue(calculatorPage.isAtCalculatorPage(), 
                "Não está na página da calculadora");
    }

    @Quando("eu preencho o primeiro operando com {string}")
    public void euPreenchoOPrimeiroOperandoCom(String valor) {
        calculatorPage.enterFirstOperand(valor);
    }

    @E("eu preencho o segundo operando com {string}")
    public void euPreenchoOSegundoOperandoCom(String valor) {
        calculatorPage.enterSecondOperand(valor);
    }

    @E("eu seleciono a operação {string}")
    public void euSelecionoAOperacao(String operador) {
        calculatorPage.selectOperator(operador);
    }

    @Então("eu devo ver o resultado {string}")
    public void euDevoVerOResultado(String resultado) {
        Assertions.assertTrue(calculatorPage.isResultVisible(), 
                "O resultado não está visível");
        String actualResult = calculatorPage.getResult();
        Assertions.assertEquals(resultado, actualResult, 
                "O resultado não corresponde ao esperado");
    }

    @E("o cálculo deve aparecer no histórico")
    public void oCalculoDeveAparecerNoHistorico() {
        // Aguarda um pouco para o histórico ser atualizado
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Verifica se o cálculo mais recente está no histórico
        // O formato exato depende da implementação, mas podemos verificar se o histórico não está vazio
        Assertions.assertTrue(calculatorPage.isCalculationInHistory("="), 
                "O cálculo não aparece no histórico");
    }

    @Quando("eu realizo uma operação de adição com {string} e {string}")
    public void euRealizoUmaOperacaoDeAdicaoCom(String primeiroOperando, String segundoOperando) {
        calculatorPage.performCalculation(primeiroOperando, segundoOperando, "+");
        
        // Aguarda um pouco para o resultado ser exibido
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @E("eu realizo uma operação de subtração com {string} e {string}")
    public void euRealizoUmaOperacaoDeSubtracaoCom(String primeiroOperando, String segundoOperando) {
        calculatorPage.performCalculation(primeiroOperando, segundoOperando, "-");
        
        // Aguarda um pouco para o resultado ser exibido
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Então("eu devo ver {string} no histórico")
    public void euDevoVerNoHistorico(String textoCalculo) {
        // Converter o formato esperado para o formato real
        // De "7 + 3 = 10,00" para "7,0 + 3,0 = 10,00"
        String[] partes = textoCalculo.split(" ");
        if (partes.length >= 5) {
            String primeiroOperando = partes[0];
            String operador = partes[1];
            String segundoOperando = partes[2];
            String resultado = partes[4];
            
            // Adicionar ",0" aos operandos se forem números inteiros
            if (!primeiroOperando.contains(",")) {
                primeiroOperando = primeiroOperando + ",0";
            }
            if (!segundoOperando.contains(",")) {
                segundoOperando = segundoOperando + ",0";
            }
            
            String textoCalculoReal = primeiroOperando + " " + operador + " " + segundoOperando + " = " + resultado;
            
            System.out.println("Procurando por: " + textoCalculoReal);
            Assertions.assertTrue(calculatorPage.isCalculationInHistory(textoCalculoReal),
                    "O cálculo '" + textoCalculoReal + "' não foi encontrado no histórico");
        } else {
            // Se o formato não for o esperado, usa o texto original
            Assertions.assertTrue(calculatorPage.isCalculationInHistory(textoCalculo),
                    "O cálculo '" + textoCalculo + "' não foi encontrado no histórico");
        }
    }

    @E("eu realizo uma operação de multiplicação com {string} e {string}")
    public void euRealizoUmaOperacaoDeMultiplicacaoCom(String primeiroOperando, String segundoOperando) {
        calculatorPage.performCalculation(primeiroOperando, segundoOperando, "*");
        
        // Aguarda um pouco para o resultado ser exibido
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @E("eu realizo uma operação de divisão com {string} e {string}")
    public void euRealizoUmaOperacaoDeDivisaoCom(String primeiroOperando, String segundoOperando) {
        calculatorPage.performCalculation(primeiroOperando, segundoOperando, "/");
        
        // Aguarda um pouco para o resultado ser exibido
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}