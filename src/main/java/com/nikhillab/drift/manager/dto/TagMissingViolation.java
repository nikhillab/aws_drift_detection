package com.nikhillab.drift.manager.dto;

import java.util.Set;

public class TagMissingViolation {

    private final Long resourceId;
    private final String arn;
    private final String resourceType;
    private final Set<String> missingTags;

    public TagMissingViolation(
            Long resourceId,
            String arn,
            String resourceType,
            Set<String> missingTags) {
        this.resourceId = resourceId;
        this.arn = arn;
        this.resourceType = resourceType;
        this.missingTags = missingTags;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public String getArn() {
        return arn;
    }

    public String getResourceType() {
        return resourceType;
    }

    public Set<String> getMissingTags() {
        return missingTags;
    }

    @Override
    public String toString() {
        return "TagMissingViolation [resourceId=" + resourceId + ", arn=" + arn + ", resourceType=" + resourceType
                + ", missingTags=" + missingTags + "]";
    }

    // getters
}
