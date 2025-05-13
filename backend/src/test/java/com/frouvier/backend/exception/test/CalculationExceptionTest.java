package com.frouvier.backend.exception.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.frouvier.backend.exception.CalculationException;

class CalculationExceptionTest {

    @Test
    void testConstructorWithMessage() {
        // Arrange
        String errorMessage = "Erro de cálculo";
        
        // Act
        CalculationException exception = new CalculationException(errorMessage);
        
        // Assert
        assertEquals(errorMessage, exception.getMessage());
    }
    
    @Test
    void testConstructorWithMessageAndCause() {
        // Arrange
        String errorMessage = "Erro de cálculo";
        Throwable cause = new ArithmeticException("Divisão por zero");
        
        // Act
        CalculationException exception = new CalculationException(errorMessage, cause);
        
        // Assert
        assertEquals(errorMessage, exception.getMessage());
        assertNotNull(exception.getCause());
        assertEquals(cause, exception.getCause());
    }
}