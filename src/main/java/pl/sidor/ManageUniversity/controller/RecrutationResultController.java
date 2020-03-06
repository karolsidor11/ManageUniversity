package pl.sidor.ManageUniversity.controller;


import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.sidor.ManageUniversity.dto.CandidateDto;
import pl.sidor.ManageUniversity.recruitment.response.RecrutationResultResponse;
import pl.sidor.ManageUniversity.recruitment.service.recrutationResult.RecrutationResultService;

@RestController
@AllArgsConstructor
@RequestMapping(value = "recrutationResults")
public class RecrutationResultController {

    private final RecrutationResultService recrutationResultService;

    @GetMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RecrutationResultResponse> checkRecrutationResult(@RequestBody CandidateDto candidateDto) {
        RecrutationResultResponse recrutationResultResponse = recrutationResultService.checkRecrutationResult(candidateDto.getName(), candidateDto.getLastName());
        return new ResponseEntity<>(recrutationResultResponse, HttpStatus.OK);
    }
}
