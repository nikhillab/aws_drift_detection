package com.nikhillab.drift.manager.service;

import org.springframework.stereotype.Service;

import com.nikhillab.drift.manager.entity.SnapShot;
import com.nikhillab.drift.manager.repository.SnapShotRepo;

@Service
public class SnapshotResolverService {

    private final SnapShotRepo snapshotRepository;

    public SnapshotResolverService(SnapShotRepo snapshotRepository) {
        this.snapshotRepository = snapshotRepository;
    }

    /**
     * Returns the latest snapshot ID for a given resource type.
     */
    public Long getLatestSnapshotId(String resourceType) {
        return snapshotRepository
                .findTopByResourceTypeOrderByCreatedAtDesc(resourceType)
                .map(SnapShot::getSnapShotID)
                .orElseThrow(() -> new IllegalStateException(
                        "No snapshot found for resourceType=" + resourceType));
    }
}