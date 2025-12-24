package com.nikhillab.drift.manager.dto;

import java.util.Set;

public final class TagPolicy {

    private TagPolicy() {}

    public static final Set<String> REQUIRED_TAGS = Set.of(
            "project_id",
            "architecture"
    );
}