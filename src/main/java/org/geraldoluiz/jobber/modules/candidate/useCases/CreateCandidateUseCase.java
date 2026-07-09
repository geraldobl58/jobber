package org.geraldoluiz.jobber.modules.candidate.useCases;

import org.geraldoluiz.jobber.exceptions.ExceptionUserFound;
import org.geraldoluiz.jobber.modules.candidate.entities.CandidateEntity;
import org.geraldoluiz.jobber.modules.candidate.repositories.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CreateCandidateUseCase {
    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public CandidateEntity execute(CandidateEntity candidateEntity)
    {
        this.candidateRepository
            .findByUsernameOrEmail(
                    candidateEntity.getUsername(),
                    candidateEntity.getEmail())
            .ifPresent((user) -> {
                throw new ExceptionUserFound("Username or email already exists");
            });

        var password = passwordEncoder.encode(candidateEntity.getPassword());

        candidateEntity.setPassword(password);

        return this.candidateRepository.save(candidateEntity);
    }
}
