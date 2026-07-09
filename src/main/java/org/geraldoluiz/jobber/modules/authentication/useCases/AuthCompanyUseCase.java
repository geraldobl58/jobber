package org.geraldoluiz.jobber.modules.authentication.useCases;

import com.auth0.jwt.JWT;


import com.auth0.jwt.algorithms.Algorithm;
import org.geraldoluiz.jobber.modules.company.dto.AuthCompanyDto;
import org.geraldoluiz.jobber.modules.company.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthCompanyUseCase {

    @Value("${security.token.secret}")
    private String secretKey;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String execute(AuthCompanyDto authCompanyDto) {
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

        return JWT.create().withIssuer("jobber").withSubject(company.getId().toString()).sign(algorithm);
    }
}
