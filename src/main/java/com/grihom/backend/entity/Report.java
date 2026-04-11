package com.grihom.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reports")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String title;

    @Column(name = "valor_score")
    private Integer valorScore;

    @Column(name = "property_type")
    private String propertyType;

    @Column(name = "property_location")
    private String propertyLocation;

    @Column(name = "property_size")
    private String propertySize;

    @Column(name = "bedrooms")
    private Integer bedrooms;

    @Column(name = "year_built")
    private Integer yearBuilt;

    @Column(name = "property_condition")
    private String propertyCondition;

    @Column(name = "budget")
    private String budget;

    @Column(name = "goal")
    private String goal;

    @Column(name = "estimated_value")
    private String estimatedValue;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "contact_email")
    private String contactEmail;

    @Column(name = "phone")
    private String phone;

    @Column(name = "recommendations", columnDefinition = "MEDIUMTEXT")
    private String recommendations;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
