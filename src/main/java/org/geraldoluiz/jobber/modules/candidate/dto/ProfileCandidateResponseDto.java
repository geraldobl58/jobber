package org.geraldoluiz.jobber.modules.candidate.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileCandidateResponseDto {

    private UUID id;

    @Schema(description = "Nome do candidato", example = "João da Silva")
    private String name;

    @Schema(description = "Email do candidato", example = "joao.silva@example.com")
    private String email;

    @Schema(description = "Nome de usuário do candidato", example = "joaosilva")
    private String username;

    @Schema(description = "Descrição do candidato", example = "Desenvolvedor Java com 5 anos de experiência")
    private String description;
}
