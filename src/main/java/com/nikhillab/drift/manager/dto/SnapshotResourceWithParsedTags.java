package com.nikhillab.drift.manager.dto;

import java.util.Map;

public class SnapshotResourceWithParsedTags {

    private final SnapshotResourceWithTagsDTO resource;
    private final Map<String, String> tags;

    public SnapshotResourceWithParsedTags(
            SnapshotResourceWithTagsDTO resource,
            Map<String, String> tags
    ) {
        this.resource = resource;
        this.tags = tags;
    }

    public SnapshotResourceWithTagsDTO getResource() {
        return resource;
    }

    public Map<String, String> getTags() {
        return tags;
    }

    @Override
    public String toString() {
        return "SnapshotResourceWithParsedTags [resource=" + resource + ", tags=" + tags + "]";
    }
    
}
