package com.nikhillab.drift.manager.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "aws_resource_details_property")
public class AWSResourceDetailsProperty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long awsResourceDetailsPropertyId;

    private String name;

    private Instant lastReportedAt;

    @Column(columnDefinition = "TEXT")
    private String data;

    @ManyToOne
    @JoinColumn(name = "aws_resource_details_id")
    private AWSResourceDetails awsResourceDetails;

    public AWSResourceDetailsProperty() {
    }

    public AWSResourceDetailsProperty(String name, Instant lastReportedAt, String data) {
        this.name = name;
        this.lastReportedAt = lastReportedAt;
        this.data = data;
    }

    public Long getAwsResourceDetailsPropertyId() {
        return awsResourceDetailsPropertyId;
    }

    public void setAwsResourceDetailsPropertyId(Long awsResourceDetailsPropertyId) {
        this.awsResourceDetailsPropertyId = awsResourceDetailsPropertyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getLastReportedAt() {
        return lastReportedAt;
    }

    public void setLastReportedAt(Instant lastReportedAt) {
        this.lastReportedAt = lastReportedAt;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public AWSResourceDetails getAwsResourceDetails() {
        return awsResourceDetails;
    }

    public void setAwsResourceDetails(AWSResourceDetails awsResourceDetails) {
        this.awsResourceDetails = awsResourceDetails;
    }

    @Override
    public String toString() {
        return "AWSResourceDetailsProperty [name=" + name + ", lastReportedAt=" + lastReportedAt + ", data=" + data
                + "]";
    }

}
