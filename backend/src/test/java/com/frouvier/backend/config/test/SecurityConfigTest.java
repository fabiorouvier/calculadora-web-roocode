package com.frouvier.backend.config.test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

import com.frouvier.backend.config.SecurityConfig;

/**
 * Teste para a configuração de segurança.
 * Usamos SpringBootTest para evitar problemas com mocks complexos do Spring Security.
 */
@SpringBootTest
class SecurityConfigTest {

    @Autowired
    private SecurityConfig securityConfig;
    
    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;
    
    /**
     * Teste para verificar se a configuração de segurança está presente.
     * Este é um teste simples que verifica se a classe foi carregada corretamente.
     */
    @Test
    void testSecurityConfigExists() {
        // Assert
        assertNotNull(securityConfig);
    }
    
    /**
     * Teste para verificar se o AuthenticationManager pode ser obtido.
     * Este teste verifica se a configuração de autenticação está funcionando.
     */
    @Test
    void testAuthenticationManagerCanBeObtained() throws Exception {
        // Act
        AuthenticationManager authManager = authenticationConfiguration.getAuthenticationManager();
        
        // Assert
        assertNotNull(authManager);
    }
}