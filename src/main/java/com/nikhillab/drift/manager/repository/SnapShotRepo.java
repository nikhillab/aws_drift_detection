package com.nikhillab.drift.manager.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.nikhillab.drift.manager.entity.SnapShot;

public interface SnapShotRepo extends CrudRepository<SnapShot,Long>{

   Optional<SnapShot> findTopByResourceTypeOrderByCreatedAtDesc(String resourceType);

    
}
