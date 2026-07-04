package org.geraldoluiz.jobber.modules.company.controllers;

import jakarta.validation.Valid;
import org.geraldoluiz.jobber.modules.company.entities.CompanyEntity;
import org.geraldoluiz.jobber.modules.company.useCases.CreateCompanyUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/company")
class CompanyController {

    @Autowired
    private CreateCompanyUseCase createCompanyUseCase;

    @PostMapping()
    public ResponseEntity<Object> create(@Valid @RequestBody CompanyEntity companyEntity)
    {
        try {
            var response = this.createCompanyUseCase.execute(companyEntity);

            return ResponseEntity.ok().body(response);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

}
