package com.nikhillab.drift.manager.entity;

import java.time.LocalDateTime;

import com.nikhillab.drift.manager.dto.ArchitectureDiagramStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "aws_architecture_diagram")
public class AWSArchitectureDiagram {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "aws_architecture_diagram_id")
    private Long awsArchitectureDiagramId;

    @Column(name = "project_id", nullable = false)
    private String projectId;

    @Column(name = "architecture", nullable = false)
    private String architecture;

    @Column(name = "snapshot_id", nullable = false)
    private Long snapshotId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "image_s3_url")
    private String imageS3Url;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ArchitectureDiagramStatus status;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // getters & setters omitted for brevity
}
