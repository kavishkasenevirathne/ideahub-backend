package com.ideahub.ideahubbackend.controller;

import com.ideahub.ideahubbackend.model.User;
import com.ideahub.ideahubbackend.security.JwtUtil;
import com.ideahub.ideahubbackend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final JwtUtil jwtUtil;
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    public AuthController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody Map<String, String> request) {
        try {
            User savedUser = userService.saveUser(request.get("username"), request.get("password"));
            return ResponseEntity.ok(savedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> request) {
        logger.info("Login endpoint hit for username: {}", request.get("username"));
        return userService.authenticate(request.get("username"), request.get("password"))
                .map(user -> ResponseEntity.ok(
                        Map.of("token", jwtUtil.generateToken(user.getUsername()))
                ))
                .orElseGet(() -> ResponseEntity.status(401)
                        .body(Map.of("error", "Invalid credentials")));
    }
}
