package com.grihom.backend.repository;

import com.grihom.backend.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findByUserIdOrderByCreatedAtDesc(Long userId);
    long countByUserId(Long userId);
}
