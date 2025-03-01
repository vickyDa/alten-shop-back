package com.alten.shop.service;


import com.alten.shop.entity.User;
import com.alten.shop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(User user) {
        // Vérifiez si l'email ou le nom d'utilisateur existe déjà
        if (userRepository.findByEmail(user.getEmail()).isPresent() ||
                userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("Email ou nom d'utilisateur déjà utilisé.");
        }
        // Encodez le mot de passe avant de le sauvegarder
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
