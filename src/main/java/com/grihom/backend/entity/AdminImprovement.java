package com.grihom.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "admin_improvements")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminImprovement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String room;
    private String cost;
    private String effort;
    private String roi;
    private Integer impact;

    @Column(name = "image_url", columnDefinition = "MEDIUMTEXT")
    private String imageUrl;

    @Column(name = "budget_range")
    private String budgetRange;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_by_email")
    private String createdByEmail;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_by_email")
    private String updatedByEmail;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
