package com.frouvier.backend.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.frouvier.backend.dto.CalculationRequest;
import com.frouvier.backend.dto.CalculationResponse;
import com.frouvier.backend.exception.CalculationException;
import com.frouvier.backend.model.CalculationHistory;
import com.frouvier.backend.model.User;
import com.frouvier.backend.repository.CalculationHistoryRepository;
import com.frouvier.backend.repository.UserRepository;

@Service
public class CalculatorService {
    
    @Autowired
    private CalculationHistoryRepository calculationHistoryRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    public CalculationResponse calculate(CalculationRequest request) {
        Double result = null;
        String operation = request.getFirstOperand() + " " + request.getOperator() + " " + request.getSecondOperand();
        
        // Realizar o cálculo com base no operador
        switch (request.getOperator()) {
            case "+":
                result = request.getFirstOperand() + request.getSecondOperand();
                break;
            case "-":
                result = request.getFirstOperand() - request.getSecondOperand();
                break;
            case "*":
                result = request.getFirstOperand() * request.getSecondOperand();
                break;
            case "/":
                if (request.getSecondOperand() == 0) {
                    throw new CalculationException("Divisão por zero não é permitida");
                }
                result = request.getFirstOperand() / request.getSecondOperand();
                break;
            default:
                throw new CalculationException("Operador inválido: " + request.getOperator());
        }
        
        // Salvar no histórico
        saveCalculationHistory(request, result, operation);
        
        return new CalculationResponse(result, operation);
    }
    
    private void saveCalculationHistory(CalculationRequest request, Double result, String operation) {
        // Obter o usuário autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado: " + username));
        
        // Criar e salvar o histórico
        CalculationHistory history = new CalculationHistory();
        history.setOperation(operation);
        history.setFirstOperand(request.getFirstOperand());
        history.setSecondOperand(request.getSecondOperand());
        history.setOperator(request.getOperator());
        history.setResult(result);
        history.setTimestamp(LocalDateTime.now());
        history.setUser(user);
        
        calculationHistoryRepository.save(history);
    }
}