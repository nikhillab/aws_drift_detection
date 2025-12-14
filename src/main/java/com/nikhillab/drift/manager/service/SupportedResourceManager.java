package com.nikhillab.drift.manager.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.nikhillab.drift.manager.entity.AWSSupportedResourceType;
import com.nikhillab.drift.manager.repository.AWSSupportedResourceTypeRepo;

import jakarta.transaction.Transactional;

@Service
public class SupportedResourceManager {

    private final AWSSupportedResourceTypeRepo awsSupportedResourceTypeRepo;

    public SupportedResourceManager(AWSSupportedResourceTypeRepo awsSupportedResourceTypeRepo) {
        this.awsSupportedResourceTypeRepo = awsSupportedResourceTypeRepo;
    }

    public boolean isSupportedResourceTypeEnabled(Long supportedResourceTypeId) {
        var supportedResourceType = awsSupportedResourceTypeRepo.findById(supportedResourceTypeId);

        if (supportedResourceType.isPresent()) {
            return supportedResourceType.get().isEnabled();
        } else {
            return false;
        }
    }

    public boolean enableSupportedResourceType(Long supportedResourceTypeId) {

        // Implementation to enable the resource
        return toggleSupportedResourceType(supportedResourceTypeId, true);

    }

    public boolean disableSupportedResourceType(Long supportedResourceTypeId) {
        // Implementation to disable the resource
        return toggleSupportedResourceType(supportedResourceTypeId, false);

    }

    @Transactional
    private boolean toggleSupportedResourceType(Long supportedResourceTypeId, boolean isEnabled) {
        var supportedResourceType = awsSupportedResourceTypeRepo.findById(supportedResourceTypeId);
        if (supportedResourceType.isPresent()) {
            var supportedResource = supportedResourceType.get();
            supportedResource.setEnabled(isEnabled);
            supportedResource.setUpdatedAt(LocalDateTime.now());
            awsSupportedResourceTypeRepo.save(supportedResource);
            return true;
        } else {
            return false;
        }
    }

    public Iterable<AWSSupportedResourceType> getSupportedResourceTypes() {
        // Implementation to get supported resource types
        return awsSupportedResourceTypeRepo.findAll();
    }

}
