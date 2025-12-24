package com.nikhillab.drift.manager.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nikhillab.drift.manager.service.TagComplianceService;
import com.nikhillab.drift.manager.service.TagMissingViolation;

@RestController
@RequestMapping("/api/compliance")
public class TagComplianceController {

    private final TagComplianceService service;

    public TagComplianceController(TagComplianceService service) {
        this.service = service;
    }

    @GetMapping("/tag-missing")
    public List<TagMissingViolation> getTagMissing(
            @RequestParam Long snapshotId) {
        return service.findTagMissingViolations(snapshotId);
    }
}