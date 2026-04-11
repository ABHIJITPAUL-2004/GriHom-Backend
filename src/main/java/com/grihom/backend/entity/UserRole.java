package com.grihom.backend.entity;

public enum UserRole {
    ADMIN,
    DECOR,
    USER;

    public static UserRole fromValue(String value) {
        if (value == null || value.isBlank()) {
            return USER;
        }

        try {
            return UserRole.valueOf(value.trim().toUpperCase());
        } catch (IllegalArgumentException ex) {
            return USER;
        }
    }
}
