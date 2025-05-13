package com.frouvier.backend.service.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
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

import com.frouvier.backend.model.CalculationHistory;
import com.frouvier.backend.model.User;
import com.frouvier.backend.repository.CalculationHistoryRepository;
import com.frouvier.backend.repository.UserRepository;
import com.frouvier.backend.service.HistoryService;

@ExtendWith(MockitoExtension.class)
class HistoryServiceTest {

    @Mock
    private CalculationHistoryRepository calculationHistoryRepository;
    
    @Mock
    private UserRepository userRepository;
    
    @Mock
    private SecurityContext securityContext;
    
    @Mock
    private Authentication authentication;
    
    @InjectMocks
    private HistoryService historyService;
    
    private User testUser;
    private List<CalculationHistory> testHistoryList;
    
    @BeforeEach
    void setUp() {
        // Configurar o SecurityContextHolder para os testes
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("testuser");
        
        // Configurar o usuário de teste
        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("testuser");
        
        // Configurar o histórico de teste
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
    void testGetUserHistory() {
        // Arrange
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(testUser));
        when(calculationHistoryRepository.findByUserOrderByTimestampDesc(testUser)).thenReturn(testHistoryList);
        
        // Act
        List<CalculationHistory> result = historyService.getUserHistory();
        
        // Assert
        assertEquals(2, result.size());
        assertEquals(testHistoryList, result);
    }
    
    @Test
    void testGetUserHistoryWhenUserNotFound() {
        // Arrange
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.empty());
        
        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            historyService.getUserHistory();
        });
        
        assertEquals("Usuário não encontrado: testuser", exception.getMessage());
    }
}