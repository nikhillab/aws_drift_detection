package com.nikhillab.drift.manager.controller;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nikhillab.drift.manager.entity.AWSResourceDetails;
import com.nikhillab.drift.manager.service.ResourceManager;

@RestController
@RequestMapping("/api/resources")
public class ResourceManagerController {

    private final ResourceManager resourceManager;

    /**
     * Create a new ResourceManagerController.
     *
     * @param resourceManager the service used to manage and query AWS resource data
     */
    public ResourceManagerController(ResourceManager resourceManager) {
        this.resourceManager = resourceManager;
    }

    @GetMapping
    /**
     * Return a paginated list of AWS resource details.
     *
     * @param page zero-based page index (defaults to 0)
     * @param size the page size to return (defaults to 50)
     * @return a {@link Page} of {@link AWSResourceDetails} for the requested page and size
     */
    public Page<AWSResourceDetails> listResources(@RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "50") int size) {
        return resourceManager.getResourceDetails(page, size);
    }

    /**
     * Trigger creation of a snapshot for a given supported resource type.
     *
     * This endpoint initiates snapshot creation and returns an accepted response
     * indicating the request has been received. Snapshot creation is handled
     * asynchronously by the service layer.
     *
     * @param resourceTypeId the id of the supported resource type to snapshot
     * @param resourceType the resource type name (provider-specific identifier)
     * @return a {@link ResponseEntity} with HTTP 202 Accepted when the request is accepted
     */
    @PostMapping("/snapshots")
    public ResponseEntity<Void> createSnapshot(@RequestParam("resourceTypeId") Long resourceTypeId,
            @RequestParam("resourceType") String resourceType) {
        resourceManager.createSnapShot(resourceTypeId, resourceType);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/snapshots/{id}")
    /**
     * Retrieve the resource details for a previously created snapshot.
     *
     * @param id the snapshot identifier
     * @return a list of {@link AWSResourceDetails} contained in the snapshot
     */
    public List<AWSResourceDetails> getSnapshot(@PathVariable("id") Long id) {
        return resourceManager.getSnapShot(id);
    }

    /**
     * Compare two snapshots and return grouped comparison results.
     *
     * The returned {@code Map} groups resources by comparison category (for example
     * "added", "removed", "unchanged", or "modified"). Each map entry contains
     * the list of {@link AWSResourceDetails} that fall into that category.
     *
     * @param firstSnapId id of the first snapshot to compare
     * @param secondSnapId id of the second snapshot to compare
     * @return a map from comparison category name to the corresponding list of {@link AWSResourceDetails}
     */
    @GetMapping("/snapshots/compare")
    public Map<String, List<AWSResourceDetails>> compareSnapshots(
            @RequestParam("first") Long firstSnapId,
            @RequestParam("second") Long secondSnapId) {
        // Delegate the comparison logic to the ResourceManager service
        return resourceManager.compareSnapShot(firstSnapId, secondSnapId);
    }

}
