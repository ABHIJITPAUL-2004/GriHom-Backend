package com.grihom.backend.controller;

import com.grihom.backend.dto.ReportDto;
import com.grihom.backend.entity.User;
import com.grihom.backend.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping
    public ResponseEntity<List<ReportDto.ReportResponse>> getReports(
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(reportService.getUserReports(user.getId()));
    }

    @PostMapping
    public ResponseEntity<?> createReport(@AuthenticationPrincipal User user,
                                          @RequestBody ReportDto.CreateReportRequest request) {
        try {
            ReportDto.ReportResponse report = reportService.createReport(user, request);
            return ResponseEntity.ok(report);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReport(@AuthenticationPrincipal User user,
                                          @PathVariable Long id) {
        try {
            reportService.deleteReport(id, user.getId());
            return ResponseEntity.ok(Map.of("message", "Report deleted."));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }
}
