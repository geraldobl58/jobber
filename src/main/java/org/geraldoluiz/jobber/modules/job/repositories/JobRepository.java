package org.geraldoluiz.jobber.modules.job.repositories;

import org.geraldoluiz.jobber.modules.job.entities.JobEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface JobRepository extends JpaRepository<JobEntity, UUID> {

    List<JobEntity> findByDescriptionContainingIgnoreCase(String filter);

    List<JobEntity> findByDescriptionContaining(String description);
}
