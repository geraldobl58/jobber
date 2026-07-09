package org.geraldoluiz.jobber.modules.job.dto;

import lombok.Data;

@Data
public class CreateJobDto {

    private String description;
    private String benefits;
    private String level;
}
