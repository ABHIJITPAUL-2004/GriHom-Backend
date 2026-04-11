package com.grihom.backend.service;

import com.grihom.backend.entity.Review;
import com.grihom.backend.entity.User;
import com.grihom.backend.repository.ReviewRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    @Data
    public static class ReviewRequest {
        private String name;
        private String comment;
        private Integer rating;
    }

    @Data
    public static class ReviewResponse {
        private Long id;
        private String name;
        private String comment;
        private Integer rating;
        private LocalDateTime createdAt;
    }

    public List<ReviewResponse> getAllReviews() {
        return reviewRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public ReviewResponse createReview(User user, ReviewRequest req) {
        Review review = Review.builder()
                .user(user)
                .name(req.getName() != null ? req.getName() : (user != null ? user.getName() : "Anonymous"))
                .comment(req.getComment())
                .rating(req.getRating())
                .build();
        return toResponse(reviewRepository.save(review));
    }

    private ReviewResponse toResponse(Review r) {
        ReviewResponse res = new ReviewResponse();
        res.setId(r.getId());
        res.setName(r.getName());
        res.setComment(r.getComment());
        res.setRating(r.getRating());
        res.setCreatedAt(r.getCreatedAt());
        return res;
    }
}
