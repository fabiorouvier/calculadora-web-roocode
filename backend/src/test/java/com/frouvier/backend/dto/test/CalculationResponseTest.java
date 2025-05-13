package com.frouvier.backend.dto.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import org.junit.jupiter.api.Test;

import com.frouvier.backend.dto.CalculationResponse;

class CalculationResponseTest {

    @Test
    void testEmptyConstructor() {
        // Arrange & Act
        CalculationResponse response = new CalculationResponse();
        
        // Assert
        assertNull(response.getResult());
        assertNull(response.getOperation());
        assertNull(response.getFormattedResult());
    }
    
    @Test
    void testParameterizedConstructor() {
        // Arrange
        Double result = 16.5;
        String operation = "10.5 + 6";
        
        // Act
        CalculationResponse response = new CalculationResponse(result, operation);
        
        // Assert
        assertEquals(result, response.getResult());
        assertEquals(operation, response.getOperation());
        assertNotNull(response.getFormattedResult());
        
        // Verificar formatação correta do resultado
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("pt", "BR"));
        symbols.setDecimalSeparator(',');
        DecimalFormat df = new DecimalFormat("#,##0.000", symbols);
        String expectedFormattedResult = df.format(result);
        
        assertEquals(expectedFormattedResult, response.getFormattedResult());
    }
    
    @Test
    void testSetResult() {
        // Arrange
        CalculationResponse response = new CalculationResponse();
        Double result = 42.123;
        
        // Act
        response.setResult(result);
        
        // Assert
        assertEquals(result, response.getResult());
        
        // Verificar formatação correta do resultado
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("pt", "BR"));
        symbols.setDecimalSeparator(',');
        DecimalFormat df = new DecimalFormat("#,##0.000", symbols);
        String expectedFormattedResult = df.format(result);
        
        assertEquals(expectedFormattedResult, response.getFormattedResult());
    }
    
    @Test
    void testSetNullResult() {
        // Arrange
        CalculationResponse response = new CalculationResponse(42.0, "test");
        
        // Act
        response.setResult(null);
        
        // Assert
        assertNull(response.getResult());
        assertNull(response.getFormattedResult());
    }
    
    @Test
    void testSetFormattedResult() {
        // Arrange
        CalculationResponse response = new CalculationResponse();
        String formattedResult = "42,000";
        
        // Act
        response.setFormattedResult(formattedResult);
        
        // Assert
        assertEquals(formattedResult, response.getFormattedResult());
    }
    
    @Test
    void testSetOperation() {
        // Arrange
        CalculationResponse response = new CalculationResponse();
        String operation = "10 * 5";
        
        // Act
        response.setOperation(operation);
        
        // Assert
        assertEquals(operation, response.getOperation());
    }
}