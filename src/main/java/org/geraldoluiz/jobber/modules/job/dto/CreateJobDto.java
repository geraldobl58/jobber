package org.geraldoluiz.jobber.modules.job.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CreateJobDto {

    @Schema(description = "Descrição da vaga", example = "Desenvolvedor Java", requiredMode = Schema.RequiredMode.REQUIRED)
    private String description;

    @Schema(description = "Benefícios da vaga", example = "Vale transporte, Vale refeição", requiredMode = Schema.RequiredMode.REQUIRED)
    private String benefits;
    @Schema(description = "Nível da vaga", example = "Júnior", requiredMode = Schema.RequiredMode.REQUIRED)
    private String level;
}
