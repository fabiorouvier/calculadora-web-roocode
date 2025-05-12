package com.frouvier.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.frouvier.backend.model.CalculationHistory;
import com.frouvier.backend.model.User;
import com.frouvier.backend.repository.CalculationHistoryRepository;
import com.frouvier.backend.repository.UserRepository;

@Service
public class HistoryService {
    
    @Autowired
    private CalculationHistoryRepository calculationHistoryRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    public List<CalculationHistory> getUserHistory() {
        // Obter o usuário autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado: " + username));
        
        // Buscar o histórico do usuário
        return calculationHistoryRepository.findByUserOrderByTimestampDesc(user);
    }
}