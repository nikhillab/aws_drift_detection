package com.nikhillab.drift.manager.dto;

public enum ArchitectureDiagramStatus {

    CREATED,        // Metadata created
    IMAGE_UPLOADED, // Image stored in S3
    PROCESSING,     // LLM running
    COMPLETED,      // Extraction successful
    FAILED          // LLM or parsing failed
}