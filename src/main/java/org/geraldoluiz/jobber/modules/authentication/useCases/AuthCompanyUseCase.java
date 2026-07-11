package org.geraldoluiz.jobber.modules.authentication.useCases;

import com.auth0.jwt.JWT;


import com.auth0.jwt.algorithms.Algorithm;
import org.geraldoluiz.jobber.modules.company.dto.AuthCompanyDto;
import org.geraldoluiz.jobber.modules.company.dto.AuthCompanyResponseDto;
import org.geraldoluiz.jobber.modules.company.repositories.CompanyRepository;
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
public class AuthCompanyUseCase {

    @Value("${security.token.secret}")
    private String secretKey;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthCompanyResponseDto execute(AuthCompanyDto authCompanyDto) {
        var company = this.companyRepository.findByUsernameOrEmail(
                authCompanyDto.getUsername(),
                authCompanyDto.getUsername()
        ).orElseThrow(() -> {
            return new UsernameNotFoundException("Company not found with username: " + authCompanyDto.getUsername());
        });

        var passwordMatches = this.passwordEncoder.matches(authCompanyDto.getPassword(), company.getPassword());

        if (!passwordMatches) {
            throw new BadCredentialsException("Invalid username or password");
        }

        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        var expiresIn = Instant.now().plus(Duration.ofHours(2));

        var token =  JWT.create().withIssuer("jobber")
                .withExpiresAt(expiresIn)
                .withSubject(company.getId().toString())
                .withClaim("roles", Arrays.asList("COMPANY"))
                .sign(algorithm);

        var  authCompanyResponse = AuthCompanyResponseDto.builder()
                .accessToken(token)
                .expiresAt(expiresIn.toEpochMilli())
                .build();

        return authCompanyResponse;
    }
}
