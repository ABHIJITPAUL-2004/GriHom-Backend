package com.grihom.backend.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

public class ImprovementDto {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreateImprovementRequest {
        private String title;
        private String description;
        private String room;
        private String cost;
        private String effort;
        private String roi;
        private Integer impact;
        private String imageUrl;
        private String budgetRange;
        private String createdBy;
        private String createdByEmail;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UpdateImprovementRequest {
        private String title;
        private String description;
        private String room;
        private String cost;
        private String effort;
        private String roi;
        private Integer impact;
        private String imageUrl;
        private String budgetRange;
        private String updatedBy;
        private String updatedByEmail;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ImprovementResponse {
        private Long id;
        private String title;
        private String description;
        private String room;
        private String cost;
        private String effort;
        private String roi;
        private Integer impact;
        private String imageUrl;
        private String budgetRange;
        private String createdBy;
        private String createdByEmail;
        private String updatedBy;
        private String updatedByEmail;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private String source;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class HistoryResponse {
        private Long id;
        private String action;
        private String details;
        private String adminName;
        private String adminEmail;
        private LocalDateTime timestamp;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class HistoryRequest {
        private String action;
        private String details;
        private String adminName;
        private String adminEmail;
    }
}
