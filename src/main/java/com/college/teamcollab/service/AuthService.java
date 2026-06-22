package com.college.teamcollab.service;

import com.college.teamcollab.dto.auth.AuthResponse;
import com.college.teamcollab.dto.auth.LoginRequest;
import com.college.teamcollab.dto.auth.RegisterRequest;
import com.college.teamcollab.entity.User;
import com.college.teamcollab.repo.UserRepository;
import com.college.teamcollab.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public String register(RegisterRequest registerRequest) {
        if(userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        if(userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }
        String encodedPassword = passwordEncoder.encode(registerRequest.getPassword());

        User user = User.builder()
                .username(registerRequest.getUsername())
                .email(registerRequest.getEmail())
                .password(encodedPassword)
                .build();
        userRepository.save(user);
        return "User registered successfully";
    }

    public AuthResponse login(LoginRequest loginRequest) {

        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() ->
                        new IllegalArgumentException("User not found"));

        if (!passwordEncoder.matches(
                loginRequest.getPassword(),
                user.getPassword())) {
            throw new IllegalArgumentException("Wrong password");
        }

        String token = jwtUtil.generateToken(user.getEmail());


        return AuthResponse.builder()
                .token(token)
                .message("Login successful")
                .build();
    }
}
