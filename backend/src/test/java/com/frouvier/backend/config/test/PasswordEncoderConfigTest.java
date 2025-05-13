package com.frouvier.backend.config.test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.frouvier.backend.config.PasswordEncoderConfig;

class PasswordEncoderConfigTest {

    @Test
    void testPasswordEncoder() {
        // Arrange
        PasswordEncoderConfig config = new PasswordEncoderConfig();
        
        // Act
        PasswordEncoder encoder = config.passwordEncoder();
        
        // Assert
        assertNotNull(encoder);
        assertTrue(encoder instanceof BCryptPasswordEncoder);
        
        // Verificar se o encoder funciona corretamente
        String rawPassword = "password123";
        String encodedPassword = encoder.encode(rawPassword);
        
        assertNotNull(encodedPassword);
        assertTrue(encoder.matches(rawPassword, encodedPassword));
    }
}