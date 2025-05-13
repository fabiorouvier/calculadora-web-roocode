package com.frouvier.backend.model.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDateTime;
import java.util.Locale;

import org.junit.jupiter.api.Test;

import com.frouvier.backend.model.CalculationHistory;
import com.frouvier.backend.model.User;

class CalculationHistoryTest {

    @Test
    void testGettersAndSetters() {
        // Arrange
        CalculationHistory history = new CalculationHistory();
        Long id = 1L;
        String operation = "10.5 + 5.5";
        Double firstOperand = 10.5;
        Double secondOperand = 5.5;
        String operator = "+";
        Double result = 16.0;
        LocalDateTime timestamp = LocalDateTime.now();
        User user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        
        // Act
        history.setId(id);
        history.setOperation(operation);
        history.setFirstOperand(firstOperand);
        history.setSecondOperand(secondOperand);
        history.setOperator(operator);
        history.setResult(result);
        history.setTimestamp(timestamp);
        history.setUser(user);
        
        // Assert
        assertEquals(id, history.getId());
        assertEquals(operation, history.getOperation());
        assertEquals(firstOperand, history.getFirstOperand());
        assertEquals(secondOperand, history.getSecondOperand());
        assertEquals(operator, history.getOperator());
        assertEquals(result, history.getResult());
        assertEquals(timestamp, history.getTimestamp());
        assertEquals(user, history.getUser());
    }
    
    @Test
    void testDefaultValues() {
        // Arrange
        CalculationHistory history = new CalculationHistory();
        
        // Assert
        assertNull(history.getId());
        assertNull(history.getOperation());
        assertNull(history.getFirstOperand());
        assertNull(history.getSecondOperand());
        assertNull(history.getOperator());
        assertNull(history.getResult());
        assertNull(history.getTimestamp());
        assertNull(history.getUser());
    }
    
    @Test
    void testFormattedResult() {
        // Arrange
        CalculationHistory history = new CalculationHistory();
        Double result = 42.123;
        
        // Act
        history.setResult(result);
        
        // Assert
        assertNotNull(history.getFormattedResult());
        
        // Verificar formatação correta do resultado
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("pt", "BR"));
        symbols.setDecimalSeparator(',');
        DecimalFormat df = new DecimalFormat("#,##0.000", symbols);
        String expectedFormattedResult = df.format(result);
        
        assertEquals(expectedFormattedResult, history.getFormattedResult());
    }
    
    @Test
    void testGetFormattedResultWhenNull() {
        // Arrange
        CalculationHistory history = new CalculationHistory();
        
        // Act & Assert
        assertNull(history.getFormattedResult());
    }
    
    @Test
    void testSetFormattedResult() {
        // Arrange
        CalculationHistory history = new CalculationHistory();
        String formattedResult = "42,000";
        
        // Act
        history.setFormattedResult(formattedResult);
        
        // Assert
        assertEquals(formattedResult, history.getFormattedResult());
    }
    
    @Test
    void testGetFormattedResultWhenNotInitialized() {
        // Arrange
        CalculationHistory history = new CalculationHistory();
        Double result = 42.123;
        
        // Definir o resultado sem chamar o setter (para simular um caso onde formattedResult não foi inicializado)
        // Isso é um teste para o método getFormattedResult quando formattedResult é null mas result não é
        try {
            java.lang.reflect.Field resultField = CalculationHistory.class.getDeclaredField("result");
            resultField.setAccessible(true);
            resultField.set(history, result);
        } catch (Exception e) {
            // Ignorar exceções, o teste ainda pode verificar o comportamento padrão
        }
        
        // Act
        String formattedResult = history.getFormattedResult();
        
        // Assert
        assertNotNull(formattedResult);
        
        // Verificar formatação correta do resultado
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("pt", "BR"));
        symbols.setDecimalSeparator(',');
        DecimalFormat df = new DecimalFormat("#,##0.000", symbols);
        String expectedFormattedResult = df.format(result);
        
        assertEquals(expectedFormattedResult, formattedResult);
    }
}