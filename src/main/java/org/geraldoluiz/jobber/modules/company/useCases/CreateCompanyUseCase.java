package org.geraldoluiz.jobber.modules.company.useCases;

import org.geraldoluiz.jobber.exceptions.ExceptionUserFound;
import org.geraldoluiz.jobber.modules.company.entities.CompanyEntity;
import org.geraldoluiz.jobber.modules.company.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CreateCompanyUseCase {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public CompanyEntity execute(CompanyEntity companyEntity)
    {
        this.companyRepository
            .findByUsernameOrEmail(
                    companyEntity.getUsername(),
                    companyEntity.getEmail())
            .ifPresent(company -> {
                throw new ExceptionUserFound("Company already exists");
            });

        var password = passwordEncoder.encode(companyEntity.getPassword());
        companyEntity.setPassword(password);

        return companyRepository.save(companyEntity);
    }
}
