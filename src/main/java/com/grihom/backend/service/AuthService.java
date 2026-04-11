package com.grihom.backend.service;

import com.grihom.backend.dto.AuthDto;
import com.grihom.backend.entity.User;
import com.grihom.backend.entity.UserRole;
import com.grihom.backend.repository.UserRepository;
import com.grihom.backend.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthDto.AuthResponse login(AuthDto.LoginRequest request) {
        User user = userRepository.findByEmailIgnoreCase(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Account not found. Please register first."));

        if (!user.isActive()) {
            throw new RuntimeException("Your account is inactive. Please contact an administrator.");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password.");
        }

        String token = jwtUtil.generateToken(user.getId(), user.getEmail(), user.getRole().name());
        return new AuthDto.AuthResponse(token, user.getId(), user.getName(),
                user.getEmail(), user.getRole().name(), user.isAdmin(), user.isActive());
    }

    public AuthDto.AuthResponse register(AuthDto.RegisterRequest request) {
        if (userRepository.existsByEmailIgnoreCase(request.getEmail())) {
            throw new RuntimeException("Email already exists. Please login.");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(UserRole.USER)
                .isAdmin(false)
                .isActive(true)
                .build();

        user = userRepository.save(user);
        String token = jwtUtil.generateToken(user.getId(), user.getEmail(), user.getRole().name());
        return new AuthDto.AuthResponse(token, user.getId(), user.getName(),
                user.getEmail(), user.getRole().name(), user.isAdmin(), user.isActive());
    }
}
