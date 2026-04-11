package com.grihom.backend.config;

import com.grihom.backend.entity.User;
import com.grihom.backend.entity.UserRole;
import com.grihom.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${grihom.bootstrap-admin.email:}")
    private String adminEmail;

    @Value("${grihom.bootstrap-admin.password:}")
    private String adminPassword;

    @Override
    public void run(String... args) {
        if (adminEmail == null || adminEmail.isBlank() || adminPassword == null || adminPassword.isBlank()) {
            log.info("Admin bootstrap skipped because grihom.bootstrap-admin credentials are not configured.");
            return;
        }

        if (!userRepository.existsByEmailIgnoreCase(adminEmail)) {
            User admin = User.builder()
                    .name("Admin User")
                    .email(adminEmail)
                    .password(passwordEncoder.encode(adminPassword))
                    .role(UserRole.ADMIN)
                    .isAdmin(true   )
                    .isActive(true)
                    .build();
            userRepository.save(admin);
            log.info("Bootstrap admin user created: {}", adminEmail);
            return;
        }

        userRepository.findByEmailIgnoreCase(adminEmail).ifPresent(existingAdmin -> {
            existingAdmin.setRole(UserRole.ADMIN);
            existingAdmin.setActive(true);
            userRepository.save(existingAdmin);
        });
    }
}
