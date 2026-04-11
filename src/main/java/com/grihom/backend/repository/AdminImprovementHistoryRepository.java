package com.grihom.backend.repository;

import com.grihom.backend.entity.AdminImprovementHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AdminImprovementHistoryRepository extends JpaRepository<AdminImprovementHistory, Long> {
    List<AdminImprovementHistory> findTop10ByOrderByTimestampDesc();
    List<AdminImprovementHistory> findAllByOrderByTimestampDesc();
}
