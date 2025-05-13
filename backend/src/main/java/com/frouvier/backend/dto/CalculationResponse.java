package com.frouvier.backend.dto;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class CalculationResponse {
    
    private Double result;
    private String operation;
    private String formattedResult;
    
    public CalculationResponse() {
    }
    
    public CalculationResponse(Double result, String operation) {
        this.result = result;
        this.operation = operation;
        
        // Formatar o resultado com vírgula como separador decimal e 3 casas decimais
        if (result != null) {
            DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("pt", "BR"));
            symbols.setDecimalSeparator(',');
            DecimalFormat df = new DecimalFormat("#,##0.000", symbols);
            this.formattedResult = df.format(result);
        }
    }
    
    // Getters and Setters
    public Double getResult() {
        return result;
    }
    
    public void setResult(Double result) {
        this.result = result;
        
        // Atualizar o resultado formatado quando o resultado for alterado
        if (result != null) {
            DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("pt", "BR"));
            symbols.setDecimalSeparator(',');
            DecimalFormat df = new DecimalFormat("#,##0.000", symbols);
            this.formattedResult = df.format(result);
        } else {
            this.formattedResult = null;
        }
    }
    
    public String getFormattedResult() {
        return formattedResult;
    }
    
    public void setFormattedResult(String formattedResult) {
        this.formattedResult = formattedResult;
    }
    
    public String getOperation() {
        return operation;
    }
    
    public void setOperation(String operation) {
        this.operation = operation;
    }
}