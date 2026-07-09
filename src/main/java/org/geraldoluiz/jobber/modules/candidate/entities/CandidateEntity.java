package org.geraldoluiz.jobber.modules.candidate.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity(name = "candidates")
@Schema(description = "Entidade de Candidato")
public class CandidateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Schema(description = "ID único do candidato", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
    private UUID id;

    @Schema(description = "Nome completo do candidato", example = "João da Silva")
    private String name;

    @NotBlank(message = "O campo é obrigatório")
    @Pattern(regexp = "^[^\\s]+$", message = "Não são permitidos espaços")
    @Schema(description = "Username único sem espaços", example = "joaosilva")
    private String username;

    @NotBlank(message = "O campo é obrigatório")
    @Length(min = 6, max = 200, message = "Password must be between 6 and 20 characters")
    @Schema(description = "Senha entre 6 e 20 caracteres", example = "senha123")
    private String password;

    @NotBlank(message = "O campo é obrigatório")
    @Email(message = "Email should be valid")
    @Schema(description = "E-mail válido do candidato", example = "joao@email.com")
    private String email;

    @Schema(description = "Breve descrição profissional", example = "Desenvolvedor Java com 3 anos de experiência")
    private String description;

    @Schema(description = "URL do currículo do candidato", example = "https://meusite.com/curriculo.pdf")
    private String curriculum;

    @CreationTimestamp
    @Schema(description = "Data de criação do registro", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime createdAt;
}
