package com.grihom.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "planned_improvements",
       uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "improvement_id"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlannedImprovement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "improvement_id", nullable = false)
    private String improvementId;
}
