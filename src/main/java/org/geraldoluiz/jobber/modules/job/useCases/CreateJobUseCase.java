package org.geraldoluiz.jobber.modules.job.useCases;

import org.geraldoluiz.jobber.modules.job.entities.JobEntity;
import org.geraldoluiz.jobber.modules.job.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateJobUseCase {

    @Autowired
    private JobRepository jobRepository;

    public JobEntity execute(JobEntity jobEntity)
    {
        return this.jobRepository.save(jobEntity);
    }
}
