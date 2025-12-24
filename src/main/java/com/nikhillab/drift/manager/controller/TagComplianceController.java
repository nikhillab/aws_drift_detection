package com.nikhillab.drift.manager.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nikhillab.drift.manager.dto.TagMissingViolation;
import com.nikhillab.drift.manager.service.TagComplianceService;

@RestController
@RequestMapping("/api/compliance")
public class TagComplianceController {

    private final TagComplianceService service;

    public TagComplianceController(TagComplianceService service) {
        this.service = service;
    }

    @GetMapping("/tag-missing")
    public ResponseEntity<List<TagMissingViolation>> getTagMissing(
            @RequestParam String resourceType,
            @RequestParam(required = false) Long snapshotId) {
                System.out.println("Received request for TAG_MISSING violations for resourceType: " + resourceType + " with snapshotId: " + snapshotId);
        if (snapshotId != null) {
            var result = service.findTagMissingViolations(resourceType, snapshotId);
            System.out.println("Number of TAG_MISSING violations found: " + result.size());
            return ResponseEntity.ok(result);
        }
        var res= service.findTagMissingViolations(resourceType);
        System.out.println("Number of TAG_MISSING violations found: " + res.size());
        return ResponseEntity.ok(res);
    }
}