package com.grihom.backend.repository;

import com.grihom.backend.entity.PlannedImprovement;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface PlannedImprovementRepository extends JpaRepository<PlannedImprovement, Long> {
    List<PlannedImprovement> findByUserId(Long userId);
    Optional<PlannedImprovement> findByUserIdAndImprovementId(Long userId, String improvementId);
    void deleteByUserIdAndImprovementId(Long userId, String improvementId);
    void deleteAllByUserId(Long userId);
}
