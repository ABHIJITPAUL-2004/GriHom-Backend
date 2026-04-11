package com.grihom.backend.controller;

import com.grihom.backend.entity.User;
import com.grihom.backend.service.PlannedImprovementService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/planned")
@RequiredArgsConstructor
public class PlannedImprovementController {

    private final PlannedImprovementService plannedService;

    @Data
    static class AddRequest {
        private String improvementId;
    }

    @Data
    static class SyncRequest {
        private List<String> improvementIds;
    }

    // Get all planned improvement IDs for the logged-in user
    @GetMapping
    public ResponseEntity<List<String>> getPlanned(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(plannedService.getPlannedIds(user.getId()));
    }

    // Add a single planned improvement
    @PostMapping
    public ResponseEntity<?> addPlanned(@AuthenticationPrincipal User user,
                                        @RequestBody AddRequest request) {
        try {
            return ResponseEntity.ok(plannedService.addPlanned(user, request.getImprovementId()));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    // Remove a single planned improvement
    @DeleteMapping("/{improvementId}")
    public ResponseEntity<?> removePlanned(@AuthenticationPrincipal User user,
                                           @PathVariable String improvementId) {
        try {
            return ResponseEntity.ok(plannedService.removePlanned(user.getId(), improvementId));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    // Sync (replace all) planned improvements - useful for bulk save
    @PutMapping("/sync")
    public ResponseEntity<?> syncPlanned(@AuthenticationPrincipal User user,
                                         @RequestBody SyncRequest request) {
        try {
            return ResponseEntity.ok(plannedService.syncPlanned(user, request.getImprovementIds()));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }
}
