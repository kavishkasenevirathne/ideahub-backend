package com.ideahub.ideahubbackend.controller;

import com.ideahub.ideahubbackend.model.User;
import com.ideahub.ideahubbackend.security.JwtUtil;
import com.ideahub.ideahubbackend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody Map<String, String> request) {
        User savedUser = userService.saveUser(request.get("username"), request.get("password"));
        return ResponseEntity.ok(savedUser);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Map<String, String> request) {
        return userService.authenticate(request.get("username"), request.get("password"))
                .map(user -> ResponseEntity.ok(
                        Map.of("token", jwtUtil.generateToken(user.getUsername()))
                ))
                .orElseGet(() -> ResponseEntity.status(401)
                        .body(Map.of("error", "Invalid credentials")));
    }
}
