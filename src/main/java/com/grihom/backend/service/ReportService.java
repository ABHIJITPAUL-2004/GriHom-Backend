package com.grihom.backend.service;

import com.grihom.backend.dto.ReportDto;
import com.grihom.backend.entity.Report;
import com.grihom.backend.entity.User;
import com.grihom.backend.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;

    public ReportDto.ReportResponse createReport(User user, ReportDto.CreateReportRequest request) {
        ReportDto.PropertyDataDto pd = request.getPropertyData();

        Report report = Report.builder()
                .user(user)
                .title(request.getTitle())
                .valorScore(request.getValorScore())
                .propertyType(pd != null ? pd.getType() : null)
                .propertyLocation(pd != null ? pd.getLocation() : null)
                .propertySize(pd != null ? pd.getPropertySize() : null)
                .bedrooms(pd != null ? pd.getBedrooms() : null)
                .yearBuilt(pd != null ? pd.getYearBuilt() : null)
                .propertyCondition(pd != null ? pd.getPropertyCondition() : null)
                .budget(pd != null ? pd.getBudget() : null)
                .goal(pd != null ? pd.getGoal() : null)
                .estimatedValue(pd != null ? pd.getEstimatedValue() : null)
                .fullName(pd != null ? pd.getFullName() : null)
                .contactEmail(pd != null ? pd.getEmail() : null)
                .phone(pd != null ? pd.getPhone() : null)
                .recommendations(request.getRecommendations())
                .build();

        report = reportRepository.save(report);
        return toResponse(report);
    }

    public List<ReportDto.ReportResponse> getUserReports(Long userId) {
        return reportRepository.findByUserIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public void deleteReport(Long reportId, Long userId) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new RuntimeException("Report not found."));
        if (!report.getUser().getId().equals(userId)) {
            throw new RuntimeException("Access denied.");
        }
        reportRepository.delete(report);
    }

    private ReportDto.ReportResponse toResponse(Report r) {
        ReportDto.PropertyDataDto pd = new ReportDto.PropertyDataDto(
                r.getPropertyType(),
                r.getPropertyLocation(),
                r.getPropertySize(),
                r.getBedrooms(),
                r.getYearBuilt(),
                r.getPropertyCondition(),
                r.getBudget(),
                r.getGoal(),
                r.getEstimatedValue(),
                r.getFullName(),
                r.getContactEmail(),
                r.getPhone()
        );

        long ts = r.getCreatedAt() != null
                ? r.getCreatedAt().atZone(java.time.ZoneId.systemDefault()).toInstant().toEpochMilli()
                : System.currentTimeMillis();

        return new ReportDto.ReportResponse(
                r.getId(),
                r.getTitle(),
                r.getValorScore(),
                pd,
                r.getRecommendations(),
                r.getCreatedAt(),
                ts
        );
    }
}
