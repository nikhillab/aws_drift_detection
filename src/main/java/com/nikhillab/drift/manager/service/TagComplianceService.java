package com.nikhillab.drift.manager.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.nikhillab.drift.manager.dto.SnapshotResourceWithParsedTags;
import com.nikhillab.drift.manager.dto.TagPolicy;

@Service
public class TagComplianceService {

    private final SnapshotResourceService snapshotResourceService;

    public TagComplianceService(SnapshotResourceService snapshotResourceService) {
        this.snapshotResourceService = snapshotResourceService;
    }

    public List<TagMissingViolation> findTagMissingViolations(Long snapshotId) {

        List<SnapshotResourceWithParsedTags> resources =
                snapshotResourceService.getResources(snapshotId);

        return resources.stream()
                .map(resource -> {
                    Set<String> missing = new HashSet<>(TagPolicy.REQUIRED_TAGS);
                    missing.removeAll(resource.getTags().keySet());

                    if (missing.isEmpty()) {
                        return null;
                    }

                    return new TagMissingViolation(
                            resource.getResource().getResourceId(),
                            resource.getResource().getArn(),
                            missing
                    );
                })
                .filter(v -> v != null)
                .collect(Collectors.toList());
    }
}
