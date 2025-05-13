package com.frouvier.backend.controller.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.frouvier.backend.controller.UserController;
import com.frouvier.backend.model.User;
import com.frouvier.backend.service.UserService;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;
    
    @InjectMocks
    private UserController userController;
    
    private User testUser;
    private User createdUser;
    
    @BeforeEach
    void setUp() {
        // Configurar o usuário de teste para registro
        testUser = new User();
        testUser.setUsername("newuser");
        testUser.setPassword("password123");
        
        // Configurar o usuário criado (retornado pelo serviço)
        createdUser = new User();
        createdUser.setId(1L);
        createdUser.setUsername("newuser");
        createdUser.setPassword("encodedPassword123");
    }
    
    @Test
    void testRegisterUser() {
        // Arrange
        when(userService.createUser(any(User.class))).thenReturn(createdUser);
        
        // Act
        ResponseEntity<User> response = userController.registerUser(testUser);
        
        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        assertEquals("newuser", response.getBody().getUsername());
        assertNull(response.getBody().getPassword()); // A senha deve ser nula no response
        
        // Verificar se o serviço foi chamado
        verify(userService, times(1)).createUser(testUser);
    }
    
    @Test
    void testRegisterUserWithException() {
        // Arrange
        RuntimeException exception = new RuntimeException("Erro ao criar usuário");
        doThrow(exception).when(userService).createUser(any(User.class));
        
        // Act
        ResponseEntity<User> response = userController.registerUser(testUser);
        
        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        
        // Verificar se o serviço foi chamado
        verify(userService, times(1)).createUser(testUser);
    }
}