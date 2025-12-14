package com.nikhillab.drift.manager.repository;

import org.springframework.data.repository.CrudRepository;

import com.nikhillab.drift.manager.entity.SnapShot;

public interface SnapShotRepo extends CrudRepository<SnapShot,Long>{
    
}
