package com.sistore.productservice.service;

import com.sistore.productservice.dto.AuthResponse;
import com.sistore.productservice.dto.LoginRequest;
import com.sistore.productservice.dto.RegisterRequest;
import com.sistore.productservice.entity.AuthUser;
import com.sistore.productservice.repository.AuthUserRepository;
import com.sistore.productservice.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthUserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtil jwtService;

    public AuthService(AuthUserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public String register(RegisterRequest request) {
        AuthUser user = new AuthUser();
        user.setUsername(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
        return "User registered successfully";
    }

    public AuthResponse login(LoginRequest request) {
        AuthUser user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new RuntimeException(
                                "Invalid credentials"
                        ));
        boolean passwordMatches =
                passwordEncoder.matches(
                        request.getPassword(),
                        user.getPassword()
                );
        if (!passwordMatches) {
            throw new RuntimeException(
                    "Invalid credentials"
            );
        }
        String token =
                jwtService.generateToken(
                        user.getEmail());
        return new AuthResponse(token);
    }
}
