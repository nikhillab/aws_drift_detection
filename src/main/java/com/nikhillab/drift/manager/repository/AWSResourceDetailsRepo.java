package com.nikhillab.drift.manager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.nikhillab.drift.manager.dto.SnapshotResourceWithTagsDTO;
import com.nikhillab.drift.manager.entity.AWSResourceDetails;

public interface AWSResourceDetailsRepo extends JpaRepository<AWSResourceDetails, Long> {
	List<AWSResourceDetails> findBySnapShotID(Long snapShotID);

	@Query("""
			    SELECT new com.nikhillab.drift.manager.dto.SnapshotResourceWithTagsDTO(
			        ard.id,
			        ard.arn,
			        ard.service,
			        ard.resourceType,
			        ard.region,
			        prop.data
			    )
			    FROM AWSResourceDetails ard
			    LEFT JOIN AWSResourceDetailsProperty prop
			        ON ard.id = prop.awsResourceDetails.awsResourceDetailsId
			       AND prop.name = 'tags'
			    WHERE ard.snapShotID = :snapShotID
			""")
	List<SnapshotResourceWithTagsDTO> findResourcesWithTagsBySnapshot(
			@Param("snapShotID") Long snapShotID);

}
