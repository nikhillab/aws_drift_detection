package com.nikhillab.drift.manager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nikhillab.drift.manager.entity.AWSArchitectureDiagram;

public interface ArchitectureDiagramRepo extends JpaRepository<AWSArchitectureDiagram, Long> {

    List<AWSArchitectureDiagram> findByProjectIdAndArchitecture(
            String projectId,
            String architecture);
}