package org.geraldoluiz.jobber.modules.candidate.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.geraldoluiz.jobber.modules.candidate.dto.ProfileCandidateResponseDto;
import org.geraldoluiz.jobber.modules.candidate.entities.CandidateEntity;
import org.geraldoluiz.jobber.modules.candidate.useCases.CreateCandidateUseCase;
import org.geraldoluiz.jobber.modules.candidate.useCases.ListAllJobsFilterUseCase;
import org.geraldoluiz.jobber.modules.candidate.useCases.ProfileCandidateUseCase;
import org.geraldoluiz.jobber.modules.job.entities.JobEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/candidate")
@Tag(name = "Candidato", description = "Operações relacionadas ao cadastro de candidatos")
public class CandidateController {

    @Autowired
    private CreateCandidateUseCase createCandidateUseCase;

    @Autowired
    private ProfileCandidateUseCase profileCandidateUseCase;

    @Autowired
    private ListAllJobsFilterUseCase listAllJobsFilterUseCase;

    @PostMapping()
    @Operation(summary = "Criar candidato", description = "Cria um novo candidato na plataforma")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Candidato criado com sucesso",
            content = @Content(schema = @Schema(implementation = CandidateEntity.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos ou candidato já existente",
            content = @Content(schema = @Schema(type = "string", example = "Username já existe")))
    })
    public ResponseEntity<Object> create(@Valid @RequestBody CandidateEntity candidateEntity)
    {
        try {
            var response = this.createCandidateUseCase.execute(candidateEntity);
            return ResponseEntity.ok().body(response);
        }
        catch (Exception ex)
        {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping()
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(summary = "Listar perfil do candidato", description = "Retorna o perfil do candidato autenticado")
    @ApiResponses(
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = ProfileCandidateResponseDto.class))
            })
    )
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> get(HttpServletRequest request) {
        var idCandidate = request.getAttribute("candidate_id");

        try {
            var profile = this.profileCandidateUseCase.execute(UUID.fromString(idCandidate.toString()));
            return ResponseEntity.ok().body(profile);
        }
        catch (Exception ex)
        {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/jobs")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(summary = "Listar vagas", description = "Retorna todas as vagas filtradas por descrição")
    @ApiResponses(
            @ApiResponse(responseCode = "200", content = {
                    @Content(array = @ArraySchema(schema = @Schema(implementation = JobEntity.class)))
            })
    )
    @SecurityRequirement(name = "jwt_auth")
    public List<JobEntity> findJobsByFilter(@RequestParam String filter) {
        return this.listAllJobsFilterUseCase.execute(filter);
    }
}
