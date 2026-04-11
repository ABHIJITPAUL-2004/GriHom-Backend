package com.grihom.backend.service;

import com.grihom.backend.entity.PlannedImprovement;
import com.grihom.backend.entity.User;
import com.grihom.backend.repository.PlannedImprovementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlannedImprovementService {

    private final PlannedImprovementRepository plannedRepo;

    public List<String> getPlannedIds(Long userId) {
        return plannedRepo.findByUserId(userId)
                .stream()
                .map(PlannedImprovement::getImprovementId)
                .collect(Collectors.toList());
    }

    public List<String> addPlanned(User user, String improvementId) {
        boolean exists = plannedRepo.findByUserIdAndImprovementId(user.getId(), improvementId).isPresent();
        if (!exists) {
            PlannedImprovement planned = PlannedImprovement.builder()
                    .user(user)
                    .improvementId(improvementId)
                    .build();
            plannedRepo.save(planned);
        }
        return getPlannedIds(user.getId());
    }

    @Transactional
    public List<String> removePlanned(Long userId, String improvementId) {
        plannedRepo.deleteByUserIdAndImprovementId(userId, improvementId);
        return getPlannedIds(userId);
    }

    @Transactional
    public List<String> syncPlanned(User user, List<String> improvementIds) {
        List<String> normalizedIds = improvementIds == null
                ? Collections.emptyList()
                : improvementIds.stream()
                .filter(id -> id != null && !id.isBlank())
                .collect(Collectors.collectingAndThen(Collectors.toCollection(LinkedHashSet::new), List::copyOf));

        plannedRepo.deleteAllByUserId(user.getId());
        List<PlannedImprovement> entries = normalizedIds.stream()
                .map(id -> PlannedImprovement.builder().user(user).improvementId(id).build())
                .collect(Collectors.toList());
        plannedRepo.saveAll(entries);
        return normalizedIds;
    }
}
