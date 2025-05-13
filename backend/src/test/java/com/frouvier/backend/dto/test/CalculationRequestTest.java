package com.frouvier.backend.dto.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import com.frouvier.backend.dto.CalculationRequest;

class CalculationRequestTest {

    @Test
    void testGettersAndSetters() {
        // Arrange
        CalculationRequest request = new CalculationRequest();
        
        // Act
        request.setFirstOperand(10.5);
        request.setSecondOperand(5.5);
        request.setOperator("+");
        
        // Assert
        assertEquals(10.5, request.getFirstOperand());
        assertEquals(5.5, request.getSecondOperand());
        assertEquals("+", request.getOperator());
    }
    
    @Test
    void testDefaultValues() {
        // Arrange
        CalculationRequest request = new CalculationRequest();
        
        // Assert
        assertNull(request.getFirstOperand());
        assertNull(request.getSecondOperand());
        assertNull(request.getOperator());
    }
}