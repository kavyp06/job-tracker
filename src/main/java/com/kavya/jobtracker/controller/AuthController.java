package com.kavya.jobtracker.controller;

import com.kavya.jobtracker.model.User;
import com.kavya.jobtracker.security.JwtUtil;
import com.kavya.jobtracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        Optional<User> existing = userService.findByEmail(user.getEmail());
        if (existing.isPresent()) {
            return ResponseEntity.badRequest()
                    .body("Email already registered");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User saved = userService.createUser(user);

        Map<String, Object> response = new HashMap<>();
        response.put("id", saved.getId());
        response.put("email", saved.getEmail());
        response.put("message", "User registered successfully");

        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginRequest) {
        Optional<User> user = userService.findByEmail(loginRequest.getEmail());

        if (user.isEmpty() || !passwordEncoder.matches(
                loginRequest.getPassword(),
                user.get().getPassword())) {
            return ResponseEntity.badRequest()
                    .body("Invalid email or password");
        }

        String token = jwtUtil.generateToken(user.get().getEmail());

        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("userId", user.get().getId());
        response.put("email", user.get().getEmail());

        return ResponseEntity.ok(response);
    }
}
