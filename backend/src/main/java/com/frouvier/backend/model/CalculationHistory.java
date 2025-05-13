package com.frouvier.backend.model;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDateTime;
import java.util.Locale;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "calculation_history")
public class CalculationHistory {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String operation;
    private Double firstOperand;
    private Double secondOperand;
    private String operator;
    private Double result;
    private LocalDateTime timestamp;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getOperation() {
        return operation;
    }
    
    public void setOperation(String operation) {
        this.operation = operation;
    }
    
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
    
    public Double getResult() {
        return result;
    }
    
    /**
     * Retorna o resultado formatado com vírgula como separador decimal e 3 casas decimais
     * @return Resultado formatado
     */
    @Transient
    public String getFormattedResult() {
        if (result == null) {
            return null;
        }
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("pt", "BR"));
        symbols.setDecimalSeparator(',');
        DecimalFormat df = new DecimalFormat("#,##0.000", symbols);
        return df.format(result);
    }
    
    public void setResult(Double result) {
        this.result = result;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
}