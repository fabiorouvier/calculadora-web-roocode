package com.frouvier.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.frouvier.backend.dto.CalculationRequest;
import com.frouvier.backend.dto.CalculationResponse;
import com.frouvier.backend.exception.CalculationException;
import com.frouvier.backend.model.CalculationHistory;
import com.frouvier.backend.service.CalculatorService;
import com.frouvier.backend.service.HistoryService;

@RestController
@RequestMapping("/api/calculator")
public class CalculatorController {
    
    @Autowired
    private CalculatorService calculatorService;
    
    @Autowired
    private HistoryService historyService;
    
    @PostMapping("/calculate")
    public ResponseEntity<CalculationResponse> calculate(@RequestBody CalculationRequest request) {
        try {
            CalculationResponse response = calculatorService.calculate(request);
            return ResponseEntity.ok(response);
        } catch (CalculationException e) {
            // O GlobalExceptionHandler irá tratar esta exceção
            throw e;
        }
    }
    
    @GetMapping("/history")
    public ResponseEntity<List<CalculationHistory>> getHistory() {
        List<CalculationHistory> history = historyService.getUserHistory();
        return ResponseEntity.ok(history);
    }
}