package com.frouvier.backend.controller.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.frouvier.backend.controller.CalculatorController;
import com.frouvier.backend.dto.CalculationRequest;
import com.frouvier.backend.dto.CalculationResponse;
import com.frouvier.backend.exception.CalculationException;
import com.frouvier.backend.model.CalculationHistory;
import com.frouvier.backend.model.User;
import com.frouvier.backend.service.CalculatorService;
import com.frouvier.backend.service.HistoryService;

@ExtendWith(MockitoExtension.class)
class CalculatorControllerTest {

    @Mock
    private CalculatorService calculatorService;
    
    @Mock
    private HistoryService historyService;
    
    @InjectMocks
    private CalculatorController calculatorController;
    
    private CalculationRequest testRequest;
    private CalculationResponse testResponse;
    private List<CalculationHistory> testHistoryList;
    
    @BeforeEach
    void setUp() {
        // Configurar o request de teste
        testRequest = new CalculationRequest();
        testRequest.setFirstOperand(10.0);
        testRequest.setSecondOperand(5.0);
        testRequest.setOperator("+");
        
        // Configurar o response de teste
        testResponse = new CalculationResponse(15.0, "10.0 + 5.0");
        
        // Configurar o histórico de teste
        User testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("testuser");
        
        CalculationHistory history1 = new CalculationHistory();
        history1.setId(1L);
        history1.setOperation("10.0 + 5.0");
        history1.setFirstOperand(10.0);
        history1.setSecondOperand(5.0);
        history1.setOperator("+");
        history1.setResult(15.0);
        history1.setTimestamp(LocalDateTime.now());
        history1.setUser(testUser);
        
        CalculationHistory history2 = new CalculationHistory();
        history2.setId(2L);
        history2.setOperation("20.0 - 10.0");
        history2.setFirstOperand(20.0);
        history2.setSecondOperand(10.0);
        history2.setOperator("-");
        history2.setResult(10.0);
        history2.setTimestamp(LocalDateTime.now().minusMinutes(5));
        history2.setUser(testUser);
        
        testHistoryList = Arrays.asList(history1, history2);
    }
    
    @Test
    void testCalculate() {
        // Arrange
        when(calculatorService.calculate(any(CalculationRequest.class))).thenReturn(testResponse);
        
        // Act
        ResponseEntity<CalculationResponse> response = calculatorController.calculate(testRequest);
        
        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testResponse, response.getBody());
        
        // Verificar se o serviço foi chamado
        verify(calculatorService, times(1)).calculate(testRequest);
    }
    
    @Test
    void testCalculateWithException() {
        // Arrange
        CalculationException exception = new CalculationException("Erro de cálculo");
        doThrow(exception).when(calculatorService).calculate(any(CalculationRequest.class));
        
        // Act & Assert
        try {
            calculatorController.calculate(testRequest);
        } catch (CalculationException e) {
            assertEquals(exception, e);
        }
        
        // Verificar se o serviço foi chamado
        verify(calculatorService, times(1)).calculate(testRequest);
    }
    
    @Test
    void testGetHistory() {
        // Arrange
        when(historyService.getUserHistory()).thenReturn(testHistoryList);
        
        // Act
        ResponseEntity<List<CalculationHistory>> response = calculatorController.getHistory();
        
        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testHistoryList, response.getBody());
        assertEquals(2, response.getBody().size());
        
        // Verificar se o serviço foi chamado
        verify(historyService, times(1)).getUserHistory();
    }
}