package com.nikhillab.drift.manager.entity;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "aws_resource_details")
public class AWSResourceDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long awsResourceDetailsId;

    @Column(name = "snapshot_id")
    private Long snapShotID;

    @Column(nullable = false)
    private String arn;

    private String owningAccountId;

    private String region;

    private String resourceType;

    private String service;

    private Instant lastReportedAt;

    @OneToMany(mappedBy = "awsResourceDetails", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AWSResourceDetailsProperty> properties = new ArrayList<>();

    public AWSResourceDetails() {
    }

    public AWSResourceDetails(String arn, String owningAccountId, String region, String resourceType, String service,
            Instant lastReportedAt, List<AWSResourceDetailsProperty> properties) {
        this.arn = arn;
        this.owningAccountId = owningAccountId;
        this.region = region;
        this.resourceType = resourceType;
        this.service = service;
        this.lastReportedAt = lastReportedAt;
        this.properties = properties == null ? new ArrayList<>() : properties;
    }

    public Long getAwsResourceDetailsId() {
        return awsResourceDetailsId;
    }

    public void setAwsResourceDetailsId(Long awsResourceDetailsId) {
        this.awsResourceDetailsId = awsResourceDetailsId;
    }

    public Long getSnapShotID() {
        return snapShotID;
    }

    public void setSnapShotID(Long snapShotID) {
        this.snapShotID = snapShotID;
    }

    public String getArn() {
        return arn;
    }

    public void setArn(String arn) {
        this.arn = arn;
    }

    public String getOwningAccountId() {
        return owningAccountId;
    }

    public void setOwningAccountId(String owningAccountId) {
        this.owningAccountId = owningAccountId;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public Instant getLastReportedAt() {
        return lastReportedAt;
    }

    public void setLastReportedAt(Instant lastReportedAt) {
        this.lastReportedAt = lastReportedAt;
    }

    public List<AWSResourceDetailsProperty> getProperties() {
        return properties;
    }

    public void setProperties(List<AWSResourceDetailsProperty> properties) {
        this.properties = properties;
    }

    public void addProperty(AWSResourceDetailsProperty prop) {
        prop.setAwsResourceDetails(this);
        properties.add(prop);
    }

    public void removeProperty(AWSResourceDetailsProperty prop) {
        properties.remove(prop);
        prop.setAwsResourceDetails(null);
    }

}
