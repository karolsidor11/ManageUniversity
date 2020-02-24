package pl.sidor.ManageUniversity.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.sidor.ManageUniversity.schedule.model.Subject;
import pl.sidor.ManageUniversity.schedule.response.SubjectResponse;
import pl.sidor.ManageUniversity.schedule.service.SubjectService;

@RestController
@AllArgsConstructor
@RequestMapping("subject")
public class SubjectController {

    private final SubjectService subjectService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SubjectResponse> getById(@PathVariable final Long id) {
        return new ResponseEntity<>(subjectService.getById(id), HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SubjectResponse> createSubject(@RequestBody Subject subject) {
        return new ResponseEntity<>(subjectService.save(subject), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<SubjectResponse> deleteSubject(@PathVariable final Long id) {
        return new ResponseEntity<>(subjectService.delete(id), HttpStatus.OK);
    }

    @GetMapping(value = "/lecturer/{id}")
    public ResponseEntity<SubjectResponse> findByLecturer(@PathVariable final Long id) {
        return new ResponseEntity<>(subjectService.findByLecturer(id), HttpStatus.OK);
    }
}
