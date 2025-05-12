package com.frouvier.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.frouvier.backend.model.User;
import com.frouvier.backend.repository.UserRepository;

import java.util.ArrayList;

@Service
public class UserService implements UserDetailsService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));
        
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                new ArrayList<>()
        );
    }
    
    public User createUser(User user) {
        // Verificar se o usuário já existe
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("Usuário já existe: " + user.getUsername());
        }
        
        // Criptografar a senha
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        // Salvar o usuário
        return userRepository.save(user);
    }
}