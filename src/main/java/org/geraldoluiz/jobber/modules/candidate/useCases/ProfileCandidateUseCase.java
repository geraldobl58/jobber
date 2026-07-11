package org.geraldoluiz.jobber.modules.candidate.useCases;

import org.geraldoluiz.jobber.modules.candidate.dto.ProfileCandidateResponseDto;
import org.geraldoluiz.jobber.modules.candidate.repositories.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProfileCandidateUseCase {

    @Autowired
    private CandidateRepository candidateRepository;

    public ProfileCandidateResponseDto execute(UUID idCandidate)
    {
        var profile = this.candidateRepository
                .findById(idCandidate)
                .orElseThrow(() -> new UsernameNotFoundException("Candidate not found"));

        var candidate = ProfileCandidateResponseDto.builder()
                .description(profile.getDescription())
                .username(profile.getUsername())
                .email(profile.getEmail())
                .name(profile.getName())
                .id(profile.getId())
                .build();

        return candidate;
    }
}
