package com.nikhillab.drift.manager.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.nikhillab.drift.manager.dto.SnapshotResourceWithParsedTags;
import com.nikhillab.drift.manager.dto.TagMissingViolation;
import com.nikhillab.drift.manager.dto.TagPolicy;

@Service
public class TagComplianceService {

    private final SnapshotResolverService snapshotResolverService;
    private final SnapshotResourceService snapshotResourceService;

    public TagComplianceService(
            SnapshotResolverService snapshotResolverService,
            SnapshotResourceService snapshotResourceService
    ) {
        this.snapshotResolverService = snapshotResolverService;
        this.snapshotResourceService = snapshotResourceService;
    }

    /**
     * TAG_MISSING detection for a given resource type (latest snapshot).
     */
    public List<TagMissingViolation> findTagMissingViolations(
            String resourceType
    ) {
        Long snapshotId =
                snapshotResolverService.getLatestSnapshotId(resourceType);
                System.out.println("Latest snapshotId for resourceType " + resourceType + " is " + snapshotId);

        return findTagMissingViolations(resourceType, snapshotId);
    }

    /**
     * TAG_MISSING detection for a specific snapshot (audit/debug use).
     */
    public List<TagMissingViolation> findTagMissingViolations(
            String resourceType,
            Long snapshotId
    ) {

        List<SnapshotResourceWithParsedTags> resources =
                snapshotResourceService.getResources(snapshotId);
                System.out.println("Number of resources fetched for snapshotId " + snapshotId + ": " + resources.size());

        return resources.stream()
                .map(resource -> {
                    Set<String> missing =
                            new HashSet<>(TagPolicy.REQUIRED_TAGS);

                    missing.removeAll(resource.getTags().keySet());

                    if (missing.isEmpty()) {
                        return null;
                    }

                    return new TagMissingViolation(
                            resource.getResource().getResourceId(),
                            resource.getResource().getArn(),
                            resourceType,
                            missing
                    );
                })
                .peek(System.out::println)
                .filter(v -> v != null)
                .collect(Collectors.toList());
    }
}
