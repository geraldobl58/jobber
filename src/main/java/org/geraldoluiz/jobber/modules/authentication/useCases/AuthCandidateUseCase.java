package org.geraldoluiz.jobber.modules.authentication.useCases;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.geraldoluiz.jobber.modules.candidate.dto.AuthCandidateRequestDto;
import org.geraldoluiz.jobber.modules.candidate.dto.AuthCandidateResponseDto;
import org.geraldoluiz.jobber.modules.candidate.repositories.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

@Service
public class AuthCandidateUseCase {

    @Value("${security.token.secret.candidate}")
    private String secretKey;

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthCandidateResponseDto execute(AuthCandidateRequestDto authCandidateDto)
    {
        var candidate = this.candidateRepository
                .findByUsername(authCandidateDto.username())
                .orElseThrow(() -> new UsernameNotFoundException("Candidate not found"));

        var passwordMatches = this.passwordEncoder.matches(authCandidateDto.password(), candidate.getPassword());

        if (!passwordMatches) {
            throw new BadCredentialsException("Invalid username or password");
        }

        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        var expiration = Instant.now().plus(Duration.ofMinutes(10));

        var token  = JWT.create()
                .withIssuer("jobber")
                .withSubject(candidate.getId().toString())
                .withClaim("roles", Arrays.asList("CANDIDATE"))
                .withExpiresAt(expiration)
                .sign(algorithm);

        var authCandidateResponse = AuthCandidateResponseDto
                .builder()
                .access_token(token)
                .expires_at(expiration.toEpochMilli())
                .build();

        return authCandidateResponse;
    }
}
