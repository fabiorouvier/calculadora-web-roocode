package com.frouvier.backend.config.test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.frouvier.backend.config.DataInitializer;
import com.frouvier.backend.model.User;
import com.frouvier.backend.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class DataInitializerTest {

    @Mock
    private UserRepository userRepository;
    
    @Mock
    private PasswordEncoder passwordEncoder;
    
    @InjectMocks
    private DataInitializer dataInitializer;
    
    @Test
    void testRunWhenUsersDoNotExist() throws Exception {
        // Arrange
        when(userRepository.findByUsername("user")).thenReturn(Optional.empty());
        when(userRepository.findByUsername("admin")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(passwordEncoder.encode("admin123")).thenReturn("encodedAdminPassword");
        
        // Act
        dataInitializer.run();
        
        // Assert
        verify(userRepository, times(1)).findByUsername("user");
        verify(userRepository, times(1)).findByUsername("admin");
        verify(passwordEncoder, times(1)).encode("password");
        verify(passwordEncoder, times(1)).encode("admin123");
        verify(userRepository, times(2)).save(any(User.class));
    }
    
    @Test
    void testRunWhenUserExistsButAdminDoesNot() throws Exception {
        // Arrange
        User existingUser = new User();
        existingUser.setUsername("user");
        
        when(userRepository.findByUsername("user")).thenReturn(Optional.of(existingUser));
        when(userRepository.findByUsername("admin")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("admin123")).thenReturn("encodedAdminPassword");
        
        // Act
        dataInitializer.run();
        
        // Assert
        verify(userRepository, times(1)).findByUsername("user");
        verify(userRepository, times(1)).findByUsername("admin");
        verify(passwordEncoder, never()).encode("password");
        verify(passwordEncoder, times(1)).encode("admin123");
        verify(userRepository, times(1)).save(any(User.class));
    }
    
    @Test
    void testRunWhenAdminExistsButUserDoesNot() throws Exception {
        // Arrange
        User existingAdmin = new User();
        existingAdmin.setUsername("admin");
        
        when(userRepository.findByUsername("user")).thenReturn(Optional.empty());
        when(userRepository.findByUsername("admin")).thenReturn(Optional.of(existingAdmin));
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        
        // Act
        dataInitializer.run();
        
        // Assert
        verify(userRepository, times(1)).findByUsername("user");
        verify(userRepository, times(1)).findByUsername("admin");
        verify(passwordEncoder, times(1)).encode("password");
        verify(passwordEncoder, never()).encode("admin123");
        verify(userRepository, times(1)).save(any(User.class));
    }
    
    @Test
    void testRunWhenBothUsersExist() throws Exception {
        // Arrange
        User existingUser = new User();
        existingUser.setUsername("user");
        
        User existingAdmin = new User();
        existingAdmin.setUsername("admin");
        
        when(userRepository.findByUsername("user")).thenReturn(Optional.of(existingUser));
        when(userRepository.findByUsername("admin")).thenReturn(Optional.of(existingAdmin));
        
        // Act
        dataInitializer.run();
        
        // Assert
        verify(userRepository, times(1)).findByUsername("user");
        verify(userRepository, times(1)).findByUsername("admin");
        verify(passwordEncoder, never()).encode("password");
        verify(passwordEncoder, never()).encode("admin123");
        verify(userRepository, never()).save(any(User.class));
    }
}