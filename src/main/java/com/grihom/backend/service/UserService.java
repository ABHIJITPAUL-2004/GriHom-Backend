package com.grihom.backend.service;

import com.grihom.backend.dto.AdminStatsDto;
import com.grihom.backend.dto.UserDto;
import com.grihom.backend.entity.User;
import com.grihom.backend.entity.UserRole;
import com.grihom.backend.repository.AdminImprovementRepository;
import com.grihom.backend.repository.ReportRepository;
import com.grihom.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ReportRepository reportRepository;
    private final AdminImprovementRepository adminImprovementRepository;

    public List<UserDto.UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public UserDto.UserResponse updateRole(Long userId, String roleValue) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found."));

        if (roleValue == null || roleValue.isBlank()) {
            throw new RuntimeException("Role is required.");
        }

        UserRole nextRole;
        try {
            nextRole = UserRole.valueOf(roleValue.trim().toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new RuntimeException("Invalid role selected.");
        }

        user.setRole(nextRole);
        return toResponse(userRepository.save(user));
    }

    public UserDto.UserResponse updateStatus(Long userId, boolean isActive) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found."));
        user.setActive(isActive);
        return toResponse(userRepository.save(user));
    }

    public List<UserDto.UserResponse> deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found."));
        user.setActive(false);
        user.setRole(UserRole.USER);
        userRepository.save(user);
        return getAllUsers();
    }

    public AdminStatsDto getAdminStats() {
        long totalUsers = userRepository.count();
        long totalReports = reportRepository.count();
        long totalImprovements = adminImprovementRepository.count();
        return new AdminStatsDto(totalUsers, totalReports, totalImprovements);
    }

    public UserDto.UserResponse toResponse(User user) {
        return new UserDto.UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole().name(),
                user.isAdmin(),
                user.isActive()
        );
    }
}
