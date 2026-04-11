package com.grihom.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "admin_improvement_history")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminImprovementHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String action;

    @Column(columnDefinition = "TEXT")
    private String details;

    @Column(name = "admin_name")
    private String adminName;

    @Column(name = "admin_email")
    private String adminEmail;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    @PrePersist
    protected void onCreate() {
        timestamp = LocalDateTime.now();
    }
}
