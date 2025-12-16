package com.nikhillab.drift.manager.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.nikhillab.drift.manager.entity.AWSSupportedResourceType;
import com.nikhillab.drift.manager.service.SupportedResourceManager;


@RestController
@RequestMapping("/api/supported-resources")
public class SupportedResourceController {

    private final SupportedResourceManager supportedResourceManager;

    public SupportedResourceController(SupportedResourceManager supportedResourceManager) {
        this.supportedResourceManager = supportedResourceManager;
    }

    @GetMapping
    public Iterable<AWSSupportedResourceType> listSupportedResourceTypes() {
        return supportedResourceManager.getSupportedResourceTypes();
    }

    @GetMapping("/{id}/enabled")
    public ResponseEntity<Boolean> isEnabled(@PathVariable("id") Long id) {
        boolean enabled = supportedResourceManager.isSupportedResourceTypeEnabled(id);
        return ResponseEntity.ok(enabled);
    }

    @PostMapping("/{id}/enable")
    public ResponseEntity<Void> enable(@PathVariable("id") Long id) {
        boolean ok = supportedResourceManager.enableSupportedResourceType(id);
        if (ok) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/disable")
    public ResponseEntity<Void> disable(@PathVariable("id") Long id) {
        boolean ok = supportedResourceManager.disableSupportedResourceType(id);
        if (ok) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
