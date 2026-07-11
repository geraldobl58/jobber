package org.geraldoluiz.jobber.modules.candidate.useCases;

import org.geraldoluiz.jobber.modules.job.entities.JobEntity;
import org.geraldoluiz.jobber.modules.job.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListAllJobsFilterUseCase {

    @Autowired
    private JobRepository jobRepository;

    public List<JobEntity> execute(String filter)
    {
        return this.jobRepository.findByDescriptionContainingIgnoreCase(filter);
    }
}
