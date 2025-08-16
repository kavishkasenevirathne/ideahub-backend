package com.ideahub.ideahubbackend.service;

import com.ideahub.ideahubbackend.model.User;
import com.ideahub.ideahubbackend.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    // Inject PasswordEncoder bean from SecurityConfig
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Signup method
    public User saveUser(String username, String rawPassword) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        String encodedPassword = passwordEncoder.encode(rawPassword);
        return userRepository.save(new User(username, encodedPassword));
    }

    // Login method
    public Optional<User> authenticate(String username, String rawPassword) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isEmpty()) {
            logger.warn("User not found: {}", username);
            return Optional.empty();
        }
        User user = userOpt.get();
        boolean matches = passwordEncoder.matches(rawPassword, user.getPassword());
        logger.info("Authenticating user '{}': password matches = {}", username, matches);
        return matches ? Optional.of(user) : Optional.empty();
    }
}
