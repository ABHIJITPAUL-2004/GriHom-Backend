package com.grihom.backend.repository;

import com.grihom.backend.entity.AdminImprovement;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AdminImprovementRepository extends JpaRepository<AdminImprovement, Long> {
    List<AdminImprovement> findAllByOrderByCreatedAtDesc();
    List<AdminImprovement> findByRoomAndCostAndEffort(String room, String cost, String effort);
}
