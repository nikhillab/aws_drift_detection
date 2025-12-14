package com.nikhillab.drift.manager.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "snapshot")
public class SnapShot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long snapShotID;

    private String resourceType;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public SnapShot() {
    }

    public SnapShot(String resourceType) {
        this.resourceType = resourceType;
        this.createdAt = LocalDateTime.now();
    }

    public Long getSnapShotID() {
        return snapShotID;
    }

    public void setSnapShotID(Long snapShotID) {
        this.snapShotID = snapShotID;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

}
