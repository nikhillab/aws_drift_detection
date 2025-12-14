package com.nikhillab.drift.manager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nikhillab.drift.manager.entity.AWSResourceDetails;

public interface AWSResourceDetailsRepo extends JpaRepository<AWSResourceDetails, Long> {
	List<AWSResourceDetails> findBySnapShotID(Long snapShotID);
    
}
