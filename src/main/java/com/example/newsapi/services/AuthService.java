package com.example.newsapi.services;

import com.example.newsapi.dtos.AuthRequest;
import com.example.newsapi.dtos.AuthResponse;
import com.example.newsapi.dtos.RegisterRequest;
import com.example.newsapi.models.User;
import com.example.newsapi.jpa.UserRepository;
import org.apache.commons.validator.routines.EmailValidator;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private static final int MIN_PASSWORD_LENGTH = 8;

    public AuthService(UserRepository repository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public AuthResponse register(RegisterRequest request) {
        if (!EmailValidator.getInstance().isValid(request.getEmail())) {
            throw new RuntimeException("Invalid email format");
        }
        if (request.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new IllegalArgumentException("Password is too short. Minimum length required: " + MIN_PASSWORD_LENGTH);
        }

        if (isEmailTaken(request.getEmail())) {
            throw new RuntimeException("Email is already taken");
        }

        var user = new User(
                request.getName(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()));

        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return new AuthResponse(jwtToken);
    }

    public AuthResponse authenticate(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();

        var jwtToken = jwtService.generateToken(user);

        return new AuthResponse(jwtToken);
    }

    public boolean isEmailTaken(String email) {
        return repository.existsByEmail(email);
    }
}
