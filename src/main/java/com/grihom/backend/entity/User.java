package com.grihom.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "is_admin")
    @Builder.Default
    private boolean isAdmin = false;

    @Enumerated(EnumType.STRING)
    @Column
    @Builder.Default
    private UserRole role = UserRole.USER;

    @Column(name = "is_active")
    @Builder.Default
    private boolean isActive = true;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        normalizeRoleFlags();
    }

    @PreUpdate
    protected void onUpdate() {
        normalizeRoleFlags();
    }

    public UserRole getRole() {
        if (role == null) {
            return isAdmin ? UserRole.ADMIN : UserRole.USER;
        }
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role == null ? UserRole.USER : role;
        this.isAdmin = this.role == UserRole.ADMIN;
    }

    private void normalizeRoleFlags() {
        role = getRole();
        isAdmin = role == UserRole.ADMIN;
    }
}
