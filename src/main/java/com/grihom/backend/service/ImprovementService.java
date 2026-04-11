package com.grihom.backend.service;

import com.grihom.backend.dto.ImprovementDto;
import com.grihom.backend.entity.AdminImprovement;
import com.grihom.backend.entity.AdminImprovementHistory;
import com.grihom.backend.entity.User;
import com.grihom.backend.entity.UserRole;
import com.grihom.backend.repository.AdminImprovementHistoryRepository;
import com.grihom.backend.repository.AdminImprovementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ImprovementService {

    private final AdminImprovementRepository improvementRepository;
    private final AdminImprovementHistoryRepository historyRepository;

    public List<ImprovementDto.ImprovementResponse> getAllImprovements(
            String room, String cost, String effort) {

        List<AdminImprovement> all = improvementRepository.findAllByOrderByCreatedAtDesc();

        return all.stream()
                .filter(i -> room == null || room.isBlank() || room.equals(i.getRoom()))
                .filter(i -> cost == null || cost.isBlank() || cost.equals(i.getCost()))
                .filter(i -> effort == null || effort.isBlank() || effort.equals(i.getEffort()))
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public ImprovementDto.ImprovementResponse createImprovement(ImprovementDto.CreateImprovementRequest req) {
        throw new UnsupportedOperationException("Use createImprovement with user context.");
    }

    public ImprovementDto.ImprovementResponse createImprovement(User actor, ImprovementDto.CreateImprovementRequest req) {
        String budgetRange = req.getBudgetRange() != null ? req.getBudgetRange() : deriveBudgetRange(req.getCost());

        AdminImprovement improvement = AdminImprovement.builder()
                .title(req.getTitle())
                .description(req.getDescription())
                .room(req.getRoom())
                .cost(req.getCost())
                .effort(req.getEffort())
                .roi(req.getRoi())
                .impact(req.getImpact())
                .imageUrl(req.getImageUrl())
                .budgetRange(budgetRange)
                .createdBy(actor != null ? actor.getName() : req.getCreatedBy())
                .createdByEmail(actor != null ? actor.getEmail() : req.getCreatedByEmail())
                .build();

        return toResponse(improvementRepository.save(improvement));
    }

    public ImprovementDto.ImprovementResponse updateImprovement(Long id, ImprovementDto.UpdateImprovementRequest req) {
        throw new UnsupportedOperationException("Use updateImprovement with user context.");
    }

    public ImprovementDto.ImprovementResponse updateImprovement(User actor, Long id, ImprovementDto.UpdateImprovementRequest req) {
        AdminImprovement improvement = improvementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Improvement not found."));

        ensureCanModify(actor, improvement);

        if (req.getTitle() != null) improvement.setTitle(req.getTitle());
        if (req.getDescription() != null) improvement.setDescription(req.getDescription());
        if (req.getRoom() != null) improvement.setRoom(req.getRoom());
        if (req.getCost() != null) improvement.setCost(req.getCost());
        if (req.getEffort() != null) improvement.setEffort(req.getEffort());
        if (req.getRoi() != null) improvement.setRoi(req.getRoi());
        if (req.getImpact() != null) improvement.setImpact(req.getImpact());
        if (req.getImageUrl() != null) improvement.setImageUrl(req.getImageUrl());
        if (req.getBudgetRange() != null) improvement.setBudgetRange(req.getBudgetRange());
        improvement.setUpdatedBy(actor != null ? actor.getName() : req.getUpdatedBy());
        improvement.setUpdatedByEmail(actor != null ? actor.getEmail() : req.getUpdatedByEmail());

        return toResponse(improvementRepository.save(improvement));
    }

    public void deleteImprovement(User actor, Long id) {
        AdminImprovement improvement = improvementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Improvement not found."));
        if (actor == null || actor.getRole() != UserRole.ADMIN) {
            throw new RuntimeException("Only admins can delete improvements.");
        }
        improvementRepository.delete(improvement);
    }

    public ImprovementDto.HistoryResponse saveHistory(ImprovementDto.HistoryRequest req) {
        AdminImprovementHistory entry = AdminImprovementHistory.builder()
                .action(req.getAction())
                .details(req.getDetails())
                .adminName(req.getAdminName())
                .adminEmail(req.getAdminEmail())
                .build();

        entry = historyRepository.save(entry);
        return toHistoryResponse(entry);
    }

    public List<ImprovementDto.HistoryResponse> getHistory() {
        return historyRepository.findAllByOrderByTimestampDesc()
                .stream()
                .map(this::toHistoryResponse)
                .collect(Collectors.toList());
    }

    public List<ImprovementDto.HistoryResponse> getRecentHistory() {
        return historyRepository.findTop10ByOrderByTimestampDesc()
                .stream()
                .map(this::toHistoryResponse)
                .collect(Collectors.toList());
    }

    private String deriveBudgetRange(String cost) {
        if (cost == null) return "Rs. 10,000 - Rs. 50,000";
        return switch (cost) {
            case "Medium" -> "Rs. 50,000 - Rs. 2,00,000";
            case "High" -> "Rs. 2,00,000+";
            default -> "Rs. 10,000 - Rs. 50,000";
        };
    }

    private void ensureCanModify(User actor, AdminImprovement improvement) {
        if (actor == null) {
            throw new RuntimeException("Authentication required.");
        }

        if (actor.getRole() == UserRole.ADMIN) {
            return;
        }

        if (actor.getRole() == UserRole.DECOR && actor.getEmail() != null
                && actor.getEmail().equalsIgnoreCase(improvement.getCreatedByEmail())) {
            return;
        }

        throw new RuntimeException("You can update only your own improvements.");
    }

    private ImprovementDto.ImprovementResponse toResponse(AdminImprovement i) {
        return new ImprovementDto.ImprovementResponse(
                i.getId(),
                i.getTitle(),
                i.getDescription(),
                i.getRoom(),
                i.getCost(),
                i.getEffort(),
                i.getRoi(),
                i.getImpact(),
                i.getImageUrl(),
                i.getBudgetRange(),
                i.getCreatedBy(),
                i.getCreatedByEmail(),
                i.getUpdatedBy(),
                i.getUpdatedByEmail(),
                i.getCreatedAt(),
                i.getUpdatedAt(),
                "admin"
        );
    }

    private ImprovementDto.HistoryResponse toHistoryResponse(AdminImprovementHistory h) {
        return new ImprovementDto.HistoryResponse(
                h.getId(),
                h.getAction(),
                h.getDetails(),
                h.getAdminName(),
                h.getAdminEmail(),
                h.getTimestamp()
        );
    }
}

