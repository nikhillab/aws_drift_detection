package com.nikhillab.drift.manager.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.nikhillab.drift.manager.dto.SnapshotResourceWithParsedTags;
import com.nikhillab.drift.manager.repository.AWSResourceDetailsRepo;

@Service
public class SnapshotResourceService {

    private final AWSResourceDetailsRepo repository;
    private final TagParserService tagParserService;

    public SnapshotResourceService(
            AWSResourceDetailsRepo repository,
            TagParserService tagParserService) {
        this.repository = repository;
        this.tagParserService = tagParserService;
    }

    public List<SnapshotResourceWithParsedTags> getResources(Long snapshotId) {

        return repository.findResourcesWithTagsBySnapshot(snapshotId)
                .stream()
                .map(dto -> new SnapshotResourceWithParsedTags(
                        dto,
                        tagParserService.parse(dto.getTagsJson())))
                .collect(Collectors.toList());
    }
}
