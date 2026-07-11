package org.geraldoluiz.jobber.modules.job.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.geraldoluiz.jobber.modules.job.dto.CreateJobDto;
import org.geraldoluiz.jobber.modules.job.entities.JobEntity;
import org.geraldoluiz.jobber.modules.job.useCases.CreateJobUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@RestController
@RequestMapping("/company/job")
class JobController {

    @Autowired
    private CreateJobUseCase createJobUseCase;

    @PostMapping()
    @PreAuthorize("hasRole('COMPANY')")
    public JobEntity create(@Valid @RequestBody CreateJobDto createJobDto, HttpServletRequest request)
    {
        var companyId = request.getAttribute("company_id");

       var jobEntity = JobEntity.builder()
               .benefits(createJobDto.getBenefits())
               .companyId(UUID.fromString(companyId.toString()))
               .description(createJobDto.getDescription())
               .level(createJobDto.getLevel())
               .build();

       return this.createJobUseCase.execute(jobEntity);
    }

}
