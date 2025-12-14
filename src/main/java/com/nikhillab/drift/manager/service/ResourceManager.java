package com.nikhillab.drift.manager.service;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.nikhillab.drift.manager.entity.AWSResourceDetails;
import com.nikhillab.drift.manager.entity.SnapShot;
import com.nikhillab.drift.manager.repository.AWSResourceDetailsRepo;
import com.nikhillab.drift.manager.repository.SnapShotRepo;
import com.nikhillab.drift.manager.service.aws.AWSResourceExplorer2Service;

@Service
public class ResourceManager {
    private final AWSResourceDetailsRepo awsResourceDetailsRepo;
    private final SnapShotRepo snapShotRepo;

    private final AWSResourceExplorer2Service awsResourceExplorer2Service;

    private SupportedResourceManager supportedResourceManager;

    public ResourceManager(AWSResourceDetailsRepo awsResourceDetailsRepo,
            AWSResourceExplorer2Service awsResourceExplorer2Service,
            SupportedResourceManager supportedResourceManager, SnapShotRepo snapShotRepo) {
        this.awsResourceDetailsRepo = awsResourceDetailsRepo;
        this.awsResourceExplorer2Service = awsResourceExplorer2Service;
        this.supportedResourceManager = supportedResourceManager;
        this.snapShotRepo = snapShotRepo;
    }

    public Page<AWSResourceDetails> getResourceDetails(int page, int size) {
        return awsResourceDetailsRepo.findAll(PageRequest.of(page, size));
    }

    public void createSnapShot(Long resourceTypeId, String resourceType) {

        // check if resource is enabled
        // create snapshort id
        // get the resource details save in DB
        // return responce

        boolean supportedResourceTypeEnabled = supportedResourceManager.isSupportedResourceTypeEnabled(resourceTypeId);
        if (supportedResourceTypeEnabled) {

            var snapshot = snapShotRepo.save(new SnapShot(resourceType));

            var resources = awsResourceExplorer2Service.getResources(resourceType);

            saveSnapShot(snapshot.getSnapShotID(), resources);

        } else {
            // not enabled for this type
        }

    }

    public List<AWSResourceDetails> getSnapShot(Long snapShotID){
        if (snapShotID == null) {
            return List.of();
        }
        return awsResourceDetailsRepo.findBySnapShotID(snapShotID);
    }

    /**
     * Compare two snapshots and return a map with keys: "added", "removed", "changed".
     * - "added": resources present in second snapshot but not in first
     * - "removed": resources present in first snapshot but not in second
     * - "changed": resources present in both snapshots but with differing details
     */
    public Map<String, List<AWSResourceDetails>> compareSnapShot(Long firstSnapShotID, Long secondSnapShotID){
        var first = getSnapShot(firstSnapShotID);
        var second = getSnapShot(secondSnapShotID);

        Map<String, AWSResourceDetails> byArnFirst = first.stream()
                .collect(Collectors.toMap(AWSResourceDetails::getArn, r -> r));
        Map<String, AWSResourceDetails> byArnSecond = second.stream()
                .collect(Collectors.toMap(AWSResourceDetails::getArn, r -> r));

        List<AWSResourceDetails> added = byArnSecond.keySet().stream()
                .filter(a -> !byArnFirst.containsKey(a))
                .map(byArnSecond::get)
                .collect(Collectors.toList());

        List<AWSResourceDetails> removed = byArnFirst.keySet().stream()
                .filter(a -> !byArnSecond.containsKey(a))
                .map(byArnFirst::get)
                .collect(Collectors.toList());

        List<AWSResourceDetails> changed = byArnFirst.keySet().stream()
                .filter(byArnSecond::containsKey)
                .filter(a -> !resourceEquals(byArnFirst.get(a), byArnSecond.get(a)))
                .map(byArnSecond::get)
                .collect(Collectors.toList());

        Map<String, List<AWSResourceDetails>> result = new HashMap<>();
        result.put("added", added);
        result.put("removed", removed);
        result.put("changed", changed);
        return result;
    }

    private boolean resourceEquals(AWSResourceDetails a, AWSResourceDetails b) {
        if (a == b) return true;
        if (a == null || b == null) return false;
        if (!safeEquals(a.getArn(), b.getArn())) return false;
        if (!safeEquals(a.getOwningAccountId(), b.getOwningAccountId())) return false;
        if (!safeEquals(a.getRegion(), b.getRegion())) return false;
        if (!safeEquals(a.getResourceType(), b.getResourceType())) return false;
        if (!safeEquals(a.getService(), b.getService())) return false;
        if (!safeEquals(a.getLastReportedAt(), b.getLastReportedAt())) return false;

        var pa = a.getProperties();
        var pb = b.getProperties();
        if (pa == null && pb == null) return true;
        if (pa == null || pb == null) return false;
        if (pa.size() != pb.size()) return false;
        return true;
    }

    private boolean safeEquals(Object x, Object y) {
        return x == null ? y == null : x.equals(y);
    }

    private void saveSnapShot(Long snapShotID, List<AWSResourceDetails> resources) {
        if (resources == null || resources.isEmpty()) {
            return;
        }

        // attach snapshot id to each resource
        resources.forEach(r -> r.setSnapShotID(snapShotID));

        // save in batches to avoid large single transactions
        final int batchSize = 500;
        for (int i = 0; i < resources.size(); i += batchSize) {
            int end = Math.min(resources.size(), i + batchSize);
            awsResourceDetailsRepo.saveAll(resources.subList(i, end));
        }
    }

}
