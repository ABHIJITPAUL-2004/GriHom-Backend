package com.grihom.backend.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminStatsDto {
    private long totalUsers;
    private long totalReports;
    private long totalImprovements;
}
