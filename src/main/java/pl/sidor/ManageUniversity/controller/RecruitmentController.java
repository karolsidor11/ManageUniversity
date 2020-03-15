package pl.sidor.ManageUniversity.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.sidor.ManageUniversity.recruitment.model.Candidate;
import pl.sidor.ManageUniversity.recruitment.response.RecruitmentResponse;
import pl.sidor.ManageUniversity.recruitment.service.candidate.CandidateService;

@RestController
@RequestMapping(value = "recruitments")
@AllArgsConstructor
public class RecruitmentController {

    private final CandidateService candidateService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RecruitmentResponse> createCandidate(@RequestBody Candidate candidate) {
        RecruitmentResponse recruitmentResponse = candidateService.create(candidate);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(recruitmentResponse);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RecruitmentResponse> findCandidateByID(@PathVariable final Long id) {
        return new ResponseEntity<>(candidateService.findByID(id), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<RecruitmentResponse> deleteCandidate(@PathVariable final Long id) {
        return new ResponseEntity<>(candidateService.delete(id), HttpStatus.OK);
    }
}
