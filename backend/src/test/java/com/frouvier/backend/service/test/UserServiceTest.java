package com.frouvier.backend.service.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.frouvier.backend.model.User;
import com.frouvier.backend.repository.UserRepository;
import com.frouvier.backend.service.UserService;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    
    @Mock
    private PasswordEncoder passwordEncoder;
    
    @InjectMocks
    private UserService userService;
    
    private User testUser;
    
    @BeforeEach
    void setUp() {
        // Configurar o usuário de teste
        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("testuser");
        testUser.setPassword("encodedPassword");
    }
    
    @Test
    void testLoadUserByUsername() {
        // Arrange
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(testUser));
        
        // Act
        UserDetails userDetails = userService.loadUserByUsername("testuser");
        
        // Assert
        assertNotNull(userDetails);
        assertEquals("testuser", userDetails.getUsername());
        assertEquals("encodedPassword", userDetails.getPassword());
        assertEquals(0, userDetails.getAuthorities().size());
    }
    
    @Test
    void testLoadUserByUsernameWhenUserNotFound() {
        // Arrange
        when(userRepository.findByUsername("nonexistentuser")).thenReturn(Optional.empty());
        
        // Act & Assert
        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () -> {
            userService.loadUserByUsername("nonexistentuser");
        });
        
        assertEquals("Usuário não encontrado: nonexistentuser", exception.getMessage());
    }
    
    @Test
    void testCreateUser() {
        // Arrange
        User newUser = new User();
        newUser.setUsername("newuser");
        newUser.setPassword("password123");
        
        when(userRepository.findByUsername("newuser")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword123");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User savedUser = invocation.getArgument(0);
            savedUser.setId(2L);
            return savedUser;
        });
        
        // Act
        User createdUser = userService.createUser(newUser);
        
        // Assert
        assertNotNull(createdUser);
        assertEquals(2L, createdUser.getId());
        assertEquals("newuser", createdUser.getUsername());
        assertEquals("encodedPassword123", createdUser.getPassword());
        
        // Verificar se o password foi codificado e o usuário foi salvo
        verify(passwordEncoder, times(1)).encode("password123");
        verify(userRepository, times(1)).save(any(User.class));
    }
    
    @Test
    void testCreateUserWhenUsernameAlreadyExists() {
        // Arrange
        User newUser = new User();
        newUser.setUsername("existinguser");
        newUser.setPassword("password123");
        
        when(userRepository.findByUsername("existinguser")).thenReturn(Optional.of(testUser));
        
        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.createUser(newUser);
        });
        
        assertEquals("Usuário já existe: existinguser", exception.getMessage());
    }
}