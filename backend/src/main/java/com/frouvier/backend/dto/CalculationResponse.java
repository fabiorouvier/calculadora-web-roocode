package com.frouvier.backend.dto;

public class CalculationResponse {
    
    private Double result;
    private String operation;
    
    public CalculationResponse() {
    }
    
    public CalculationResponse(Double result, String operation) {
        this.result = result;
        this.operation = operation;
    }
    
    // Getters and Setters
    public Double getResult() {
        return result;
    }
    
    public void setResult(Double result) {
        this.result = result;
    }
    
    public String getOperation() {
        return operation;
    }
    
    public void setOperation(String operation) {
        this.operation = operation;
    }
}