package com.frouvier.backend.exception.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.frouvier.backend.exception.CalculationException;
import com.frouvier.backend.exception.GlobalExceptionHandler;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();

    @Test
    void testHandleCalculationException() {
        // Arrange
        String errorMessage = "Erro de cálculo";
        CalculationException exception = new CalculationException(errorMessage);
        
        // Act
        ResponseEntity<Object> response = exceptionHandler.handleCalculationException(exception);
        
        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        
        @SuppressWarnings("unchecked")
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        assertEquals(errorMessage, responseBody.get("message"));
        assertEquals(HttpStatus.BAD_REQUEST.value(), responseBody.get("status"));
    }
    
    @Test
    void testHandleArithmeticException() {
        // Arrange
        String errorMessage = "Divisão por zero";
        ArithmeticException exception = new ArithmeticException(errorMessage);
        
        // Act
        ResponseEntity<Object> response = exceptionHandler.handleArithmeticException(exception);
        
        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        
        @SuppressWarnings("unchecked")
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        assertEquals("Erro aritmético: " + errorMessage, responseBody.get("message"));
        assertEquals(HttpStatus.BAD_REQUEST.value(), responseBody.get("status"));
    }
    
    @Test
    void testHandleIllegalArgumentException() {
        // Arrange
        String errorMessage = "Argumento inválido";
        IllegalArgumentException exception = new IllegalArgumentException(errorMessage);
        
        // Act
        ResponseEntity<Object> response = exceptionHandler.handleIllegalArgumentException(exception);
        
        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        
        @SuppressWarnings("unchecked")
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        assertEquals("Argumento inválido: " + errorMessage, responseBody.get("message"));
        assertEquals(HttpStatus.BAD_REQUEST.value(), responseBody.get("status"));
    }
    
    @Test
    void testHandleGenericException() {
        // Arrange
        Exception exception = new Exception("Erro genérico");
        
        // Act
        ResponseEntity<Object> response = exceptionHandler.handleGenericException(exception);
        
        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        
        @SuppressWarnings("unchecked")
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        assertEquals("Ocorreu um erro interno", responseBody.get("message"));
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), responseBody.get("status"));
    }
}