package com.frouvier.backend.model.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

import com.frouvier.backend.model.User;

class UserTest {

    @Test
    void testGettersAndSetters() {
        // Arrange
        User user = new User();
        Long id = 1L;
        String username = "testuser";
        String password = "password123";
        
        // Act
        user.setId(id);
        user.setUsername(username);
        user.setPassword(password);
        
        // Assert
        assertEquals(id, user.getId());
        assertEquals(username, user.getUsername());
        assertEquals(password, user.getPassword());
    }
    
    @Test
    void testDefaultValues() {
        // Arrange
        User user = new User();
        
        // Assert
        assertNull(user.getId());
        assertNull(user.getUsername());
        assertNull(user.getPassword());
    }
}