package com.grihom.backend.controller;

import com.grihom.backend.entity.User;
import com.grihom.backend.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping
    public ResponseEntity<?> getReviews() {
        return ResponseEntity.ok(reviewService.getAllReviews());
    }

    @PostMapping
    public ResponseEntity<?> createReview(@AuthenticationPrincipal User user,
                                          @RequestBody ReviewService.ReviewRequest request) {
        try {
            return ResponseEntity.ok(reviewService.createReview(user, request));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }
}
