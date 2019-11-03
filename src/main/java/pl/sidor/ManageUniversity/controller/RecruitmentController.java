package pl.sidor.ManageUniversity.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.sidor.ManageUniversity.exception.UniversityException;
import pl.sidor.ManageUniversity.recruitment.model.Candidate;
import pl.sidor.ManageUniversity.recruitment.service.CandidateService;

@RestController
@RequestMapping(value = "recruitment")
@AllArgsConstructor
public class RecruitmentController {

    private CandidateService candidateService;

    @PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Candidate> createCandidate(@RequestBody Candidate candidate) throws UniversityException {

        return new ResponseEntity<>(candidateService.create(candidate), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Candidate> findCandidateByID(@PathVariable Long id) throws Throwable {

        return new ResponseEntity<>(candidateService.findByID(id), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity deleteCandidate(@PathVariable Long id) throws Throwable {

        candidateService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
