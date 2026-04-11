package com.grihom.backend.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

public class ReportDto {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreateReportRequest {
        private String title;
        private Integer valorScore;
        private PropertyDataDto propertyData;
        private String recommendations; // JSON string
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PropertyDataDto {
        private String type;
        private String location;
        private String propertySize;
        private Integer bedrooms;
        private Integer yearBuilt;
        private String propertyCondition;
        private String budget;
        private String goal;
        private String estimatedValue;
        private String fullName;
        private String email;
        private String phone;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ReportResponse {
        private Long id;
        private String title;
        private Integer valorScore;
        private PropertyDataDto propertyData;
        private String recommendations;
        private LocalDateTime createdAt;
        // For frontend compatibility
        private Long timestamp;
    }
}
