package org.geraldoluiz.jobber.modules.authentication.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.geraldoluiz.jobber.modules.authentication.useCases.AuthCompanyUseCase;
import org.geraldoluiz.jobber.modules.company.dto.AuthCompanyDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticação", description = "Operações relacionadas à autenticação de usuários")
public class AuthCompanyController {

    @Autowired
    private AuthCompanyUseCase  authCompanyUseCase;

    @PostMapping("/company")
    @Operation(summary = "Realiza login", description = "Realiza login de uma empresa na plataforma")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login realizado com sucesso",
                    content = @Content(schema = @Schema(type = "string", example = "jwt-token"))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                    content = @Content(schema = @Schema(type = "string", example = "Campo obrigatório"))),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas",
                    content = @Content(schema = @Schema(type = "string", example = "Invalid username or password")))
    })
    public ResponseEntity<Object> create(@RequestBody AuthCompanyDto authCompanyDto) {
        try {
            var response = this.authCompanyUseCase.execute(authCompanyDto);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        }
    }
}
