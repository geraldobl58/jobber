package org.geraldoluiz.jobber.modules.company.entities;

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

@Entity(name = "company")
@Data
public class CompanyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "O campo é obrigatório")
    @Pattern(regexp = "^[^\\s]+$", message = "Não são permitidos espaços")
    private String username;

    @NotBlank(message = "O campo é obrigatório")
    @Length(min = 6, max = 20, message = "Password must be between 6 and 20 characters")
    private String password;

    @NotBlank(message = "O campo é obrigatório")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "O campo é obrigatório")
    private String name;

    private String description;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
