package org.geraldoluiz.jobber.modules.candidate.controllers;

import jakarta.validation.Valid;
import org.geraldoluiz.jobber.modules.candidate.entities.CandidateEntity;

import org.geraldoluiz.jobber.modules.candidate.useCases.CreateCandidateUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/candidate")
public class CandidateController {

    @Autowired
    private CreateCandidateUseCase createCandidateUseCase;

    @PostMapping()
    public ResponseEntity<Object> create(@Valid @RequestBody CandidateEntity candidateEntity)
    {
        try {
            var response = this.createCandidateUseCase.execute(candidateEntity);

            return ResponseEntity.ok().body(response);
        }
        catch (Exception ex)
        {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping()
    public void getAllCandidates() {
        System.out.println("Get all candidates");
    }
}
