package org.geraldoluiz.jobber.modules.job.repositories;

import org.geraldoluiz.jobber.modules.job.entities.JobEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JobRepository extends JpaRepository<JobEntity, UUID> {
}
