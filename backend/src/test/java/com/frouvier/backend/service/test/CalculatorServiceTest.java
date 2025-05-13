package com.frouvier.backend.service.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.frouvier.backend.dto.CalculationRequest;
import com.frouvier.backend.dto.CalculationResponse;
import com.frouvier.backend.exception.CalculationException;
import com.frouvier.backend.model.CalculationHistory;
import com.frouvier.backend.model.User;
import com.frouvier.backend.repository.CalculationHistoryRepository;
import com.frouvier.backend.repository.UserRepository;
import com.frouvier.backend.service.CalculatorService;

@ExtendWith(MockitoExtension.class)
class CalculatorServiceTest {

    @Mock
    private CalculationHistoryRepository calculationHistoryRepository;
    
    @Mock
    private UserRepository userRepository;
    
    @Mock
    private SecurityContext securityContext;
    
    @Mock
    private Authentication authentication;
    
    @InjectMocks
    private CalculatorService calculatorService;
    
    private User testUser;
    
    @BeforeEach
    void setUp() {
        // Configurar o SecurityContextHolder para os testes
        SecurityContextHolder.setContext(securityContext);
        
        // Configurar o usuário de teste
        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("testuser");
    }
    
    private void setupCommonMocks() {
        // Configurar mocks comuns para testes que salvam histórico
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("testuser");
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(testUser));
    }
    
    @Test
    void testCalculateAddition() {
        // Arrange
        setupCommonMocks();
        
        CalculationRequest request = new CalculationRequest();
        request.setFirstOperand(10.0);
        request.setSecondOperand(5.0);
        request.setOperator("+");
        
        when(calculationHistoryRepository.save(any(CalculationHistory.class))).thenAnswer(invocation -> {
            CalculationHistory savedHistory = invocation.getArgument(0);
            savedHistory.setId(1L);
            return savedHistory;
        });
        
        // Act
        CalculationResponse response = calculatorService.calculate(request);
        
        // Assert
        assertNotNull(response);
        assertEquals(15.0, response.getResult());
        assertEquals("10.0 + 5.0", response.getOperation());
        
        // Verificar se o histórico foi salvo
        verify(calculationHistoryRepository, times(1)).save(any(CalculationHistory.class));
    }
    
    @Test
    void testCalculateSubtraction() {
        // Arrange
        setupCommonMocks();
        
        CalculationRequest request = new CalculationRequest();
        request.setFirstOperand(10.0);
        request.setSecondOperand(5.0);
        request.setOperator("-");
        
        // Act
        CalculationResponse response = calculatorService.calculate(request);
        
        // Assert
        assertNotNull(response);
        assertEquals(5.0, response.getResult());
        assertEquals("10.0 - 5.0", response.getOperation());
    }
    
    @Test
    void testCalculateMultiplication() {
        // Arrange
        setupCommonMocks();
        
        CalculationRequest request = new CalculationRequest();
        request.setFirstOperand(10.0);
        request.setSecondOperand(5.0);
        request.setOperator("*");
        
        // Act
        CalculationResponse response = calculatorService.calculate(request);
        
        // Assert
        assertNotNull(response);
        assertEquals(50.0, response.getResult());
        assertEquals("10.0 * 5.0", response.getOperation());
    }
    
    @Test
    void testCalculateDivision() {
        // Arrange
        setupCommonMocks();
        
        CalculationRequest request = new CalculationRequest();
        request.setFirstOperand(10.0);
        request.setSecondOperand(5.0);
        request.setOperator("/");
        
        // Act
        CalculationResponse response = calculatorService.calculate(request);
        
        // Assert
        assertNotNull(response);
        assertEquals(2.0, response.getResult());
        assertEquals("10.0 / 5.0", response.getOperation());
    }
    
    @Test
    void testCalculateDivisionByZero() {
        // Arrange
        // Não precisamos configurar os mocks para este teste, pois a exceção é lançada antes de usá-los
        CalculationRequest request = new CalculationRequest();
        request.setFirstOperand(10.0);
        request.setSecondOperand(0.0);
        request.setOperator("/");
        
        // Act & Assert
        CalculationException exception = assertThrows(CalculationException.class, () -> {
            calculatorService.calculate(request);
        });
        
        assertEquals("Divisão por zero não é permitida", exception.getMessage());
    }
    
    @Test
    void testCalculateWithInvalidOperator() {
        // Arrange
        // Não precisamos configurar os mocks para este teste, pois a exceção é lançada antes de usá-los
        CalculationRequest request = new CalculationRequest();
        request.setFirstOperand(10.0);
        request.setSecondOperand(5.0);
        request.setOperator("%");
        
        // Act & Assert
        CalculationException exception = assertThrows(CalculationException.class, () -> {
            calculatorService.calculate(request);
        });
        
        assertEquals("Operador inválido: %", exception.getMessage());
    }
    
    @Test
    void testCalculateWhenUserNotFound() {
        // Arrange
        // Configurar o SecurityContextHolder para o teste
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("testuser");
        
        // Configurar para retornar um usuário não encontrado
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.empty());
        
        CalculationRequest request = new CalculationRequest();
        request.setFirstOperand(10.0);
        request.setSecondOperand(5.0);
        request.setOperator("+");
        
        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            calculatorService.calculate(request);
        });
        
        assertEquals("Usuário não encontrado: testuser", exception.getMessage());
    }
}