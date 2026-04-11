package com.grihom.backend.controller;

import com.grihom.backend.dto.AdminStatsDto;
import com.grihom.backend.dto.UserDto;
import com.grihom.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;

    @GetMapping("/stats")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AdminStatsDto> getStats() {
        return ResponseEntity.ok(userService.getAdminStats());
    }

    @GetMapping("/users")
    @PreAuthorize("hasAnyRole('ADMIN','DECOR')")
    public ResponseEntity<List<UserDto.UserResponse>> getUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PutMapping("/users/{id}/role")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateRole(@PathVariable Long id,
                                        @RequestBody UserDto.UpdateRoleRequest req) {
        try {
            return ResponseEntity.ok(userService.updateRole(id, req.getRole()));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @PutMapping("/users/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateStatus(@PathVariable Long id,
                                          @RequestBody UserDto.UpdateStatusRequest req) {
        try {
            return ResponseEntity.ok(userService.updateStatus(id, req.isActive()));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @DeleteMapping("/users/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            List<UserDto.UserResponse> updatedUsers = userService.deleteUser(id);
            return ResponseEntity.ok(updatedUsers);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }
}
