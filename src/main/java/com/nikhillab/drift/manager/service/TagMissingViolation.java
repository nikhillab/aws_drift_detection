package com.nikhillab.drift.manager.service;

import java.util.Set;

public class TagMissingViolation {

    private final Long resourceId;
    private final String arn;
    private final Set<String> missingTags;

    public TagMissingViolation(Long resourceId, String arn, Set<String> missingTags) {
        this.resourceId = resourceId;
        this.arn = arn;
        this.missingTags = missingTags;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public String getArn() {
        return arn;
    }

    public Set<String> getMissingTags() {
        return missingTags;
    }
}
