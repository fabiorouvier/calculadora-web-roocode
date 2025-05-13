package com.frouvier.backend.dto;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

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
    
    /**
     * Retorna o resultado formatado com vírgula como separador decimal e 3 casas decimais
     * @return Resultado formatado
     */
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
    
    public String getOperation() {
        return operation;
    }
    
    public void setOperation(String operation) {
        this.operation = operation;
    }
}