package com.grihom.backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

public class AuthDto {

    @Data
    public static class LoginRequest {
        @NotBlank @Email
        private String email;
        @NotBlank
        private String password;
    }

    @Data
    public static class RegisterRequest {
        @NotBlank
        private String name;
        @NotBlank @Email
        private String email;
        @NotBlank
        private String password;
    }

    @Data
    @lombok.AllArgsConstructor
    public static class AuthResponse {
        private String token;
        private Long id;
        private String name;
        private String email;
        private String role;
        private boolean isAdmin;
        private boolean isActive;
    }
}
