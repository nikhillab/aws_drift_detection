package com.nikhillab.drift.manager.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

@Service
public class TagParserService {

    private final ObjectMapper objectMapper;

    public TagParserService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * Parses AWS tags JSON stored in AWS_RESOURCE_DETAILS_PROPERTY.DATA
     *
     * @param tagsJson JSON array string from DB
     * @return normalized map of tags (key -> value)
     */
    public Map<String, String> parse(String tagsJson) {
        if (tagsJson == null || tagsJson.isBlank()) {
            return Collections.emptyMap();
        }

        try {
            List<Map<String, Object>> rawTags = objectMapper.readValue(tagsJson, new TypeReference<>() {
            });

            Map<String, String> normalizedTags = new HashMap<>();

            for (Map<String, Object> tag : rawTags) {
                Object keyObj = tag.get("Key");
                Object valueObj = tag.get("Value");

                if (keyObj == null || valueObj == null) {
                    continue;
                }

                String key = keyObj.toString().trim().toLowerCase();
                String value = valueObj.toString().trim().toLowerCase();

                if (!key.isEmpty()) {
                    normalizedTags.put(key, value);
                }
            }

            return normalizedTags;

        } catch (Exception e) {
            // IMPORTANT: Never fail drift/governance because of bad tag data
            return Collections.emptyMap();
        }
    }
}
