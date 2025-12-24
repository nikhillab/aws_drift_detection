package com.nikhillab.drift.manager.dto;

public class SnapshotResourceWithTagsDTO {

    private final Long resourceId;
    private final String arn;
    private final String service;
    private final String resourceType;
    private final String region;
    private final String tagsJson;

    public SnapshotResourceWithTagsDTO(
            Long resourceId,
            String arn,
            String service,
            String resourceType,
            String region,
            String tagsJson
    ) {
        this.resourceId = resourceId;
        this.arn = arn;
        this.service = service;
        this.resourceType = resourceType;
        this.region = region;
        this.tagsJson = tagsJson;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public String getArn() {
        return arn;
    }

    public String getService() {
        return service;
    }

    public String getResourceType() {
        return resourceType;
    }

    public String getRegion() {
        return region;
    }

    public String getTagsJson() {
        return tagsJson;
    }

    @Override
    public String toString() {
        return "SnapshotResourceWithTagsDTO [resourceId=" + resourceId + ", arn=" + arn + ", service=" + service
                + ", resourceType=" + resourceType + ", region=" + region + ", tagsJson=" + tagsJson + "]";
    }

    
}
