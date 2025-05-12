package com.frouvier.backend.dto;

public class CalculationRequest {
    
    private Double firstOperand;
    private Double secondOperand;
    private String operator; // "+", "-", "*", "/"
    
    // Getters and Setters
    public Double getFirstOperand() {
        return firstOperand;
    }
    
    public void setFirstOperand(Double firstOperand) {
        this.firstOperand = firstOperand;
    }
    
    public Double getSecondOperand() {
        return secondOperand;
    }
    
    public void setSecondOperand(Double secondOperand) {
        this.secondOperand = secondOperand;
    }
    
    public String getOperator() {
        return operator;
    }
    
    public void setOperator(String operator) {
        this.operator = operator;
    }
}