package com.grihom.backend.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

public class UserDto {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserResponse {
        private Long id;
        private String name;
        private String email;
        private String role;
        private boolean isAdmin;
        private boolean isActive;
    }

    @Data
    public static class UpdateRoleRequest {
        private String role;
    }

    @Data
    public static class UpdateStatusRequest {
        private boolean isActive;
    }
}
