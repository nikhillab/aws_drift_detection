package com.nikhillab.drift.manager.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "aws_supported_resource_type")
public class AWSSupportedResourceType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long awsSupportedResourceTypeId;

    private String service;

    private String resourceType;

    @Column(name = "is_enabled")
    private boolean isEnabled = false;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public AWSSupportedResourceType() {
    }

    public AWSSupportedResourceType(String service, String resourceType) {
        this.service = service;
        this.resourceType = resourceType;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public Long getAwsSupportedResourceTypeId() {
        return awsSupportedResourceTypeId;
    }

    public void setAwsSupportedResourceTypeId(Long awsSupportedResourceTypeId) {
        this.awsSupportedResourceTypeId = awsSupportedResourceTypeId;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

}
