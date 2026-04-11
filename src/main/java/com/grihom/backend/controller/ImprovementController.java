package com.grihom.backend.controller;

import com.grihom.backend.dto.ImprovementDto;
import com.grihom.backend.entity.User;
import com.grihom.backend.service.ImprovementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/improvements")
@RequiredArgsConstructor
public class ImprovementController {

    private final ImprovementService improvementService;

    // Public - anyone can browse improvements
    @GetMapping
    public ResponseEntity<List<ImprovementDto.ImprovementResponse>> getImprovements(
            @RequestParam(required = false) String room,
            @RequestParam(required = false) String cost,
            @RequestParam(required = false) String effort) {
        return ResponseEntity.ok(improvementService.getAllImprovements(room, cost, effort));
    }

    // Admin only - create
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','DECOR')")
    public ResponseEntity<?> createImprovement(
            @AuthenticationPrincipal User user,
            @RequestBody ImprovementDto.CreateImprovementRequest request) {
        try {
            return ResponseEntity.ok(improvementService.createImprovement(user, request));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    // Admin only - update
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','DECOR')")
    public ResponseEntity<?> updateImprovement(@AuthenticationPrincipal User user,
            @PathVariable Long id,
            @RequestBody ImprovementDto.UpdateImprovementRequest request) {
        try {
            return ResponseEntity.ok(improvementService.updateImprovement(user, id, request));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    // Admin only - delete
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteImprovement(@AuthenticationPrincipal User user, @PathVariable Long id) {
        try {
            improvementService.deleteImprovement(user, id);
            return ResponseEntity.ok(Map.of("message", "Improvement deleted."));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    // Admin only - history log
    @GetMapping("/history")
    @PreAuthorize("hasAnyRole('ADMIN','DECOR')")
    public ResponseEntity<List<ImprovementDto.HistoryResponse>> getHistory(
            @RequestParam(defaultValue = "false") boolean recent) {
        if (recent) {
            return ResponseEntity.ok(improvementService.getRecentHistory());
        }
        return ResponseEntity.ok(improvementService.getHistory());
    }

    @PostMapping("/history")
    @PreAuthorize("hasAnyRole('ADMIN','DECOR')")
    public ResponseEntity<?> saveHistory(@RequestBody ImprovementDto.HistoryRequest request) {
        try {
            return ResponseEntity.ok(improvementService.saveHistory(request));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }
}
