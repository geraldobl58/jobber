package org.geraldoluiz.jobber.modules.job.controllers;

import jakarta.validation.Valid;
import org.geraldoluiz.jobber.modules.job.entities.JobEntity;
import org.geraldoluiz.jobber.modules.job.useCases.CreateJobUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/job")
class JobController {

    @Autowired
    private CreateJobUseCase createJobUseCase;

    @PostMapping()
    public JobEntity create(@Valid @RequestBody JobEntity jobEntity)
    {
        return this.createJobUseCase.execute(jobEntity);
    }

}
