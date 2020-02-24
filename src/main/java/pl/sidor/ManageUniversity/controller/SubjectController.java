package pl.sidor.ManageUniversity.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.sidor.ManageUniversity.lecturer.model.Lecturer;
import pl.sidor.ManageUniversity.lecturer.repository.LecturerRepo;
import pl.sidor.ManageUniversity.schedule.model.Subject;
import pl.sidor.ManageUniversity.schedule.service.SubjectService;

import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("subject")
public class SubjectController {

    private final SubjectService subjectService;
    private final LecturerRepo lecturerRepo;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Subject> getById(@PathVariable final Long id) throws Throwable {
        return new ResponseEntity<>(subjectService.getById(id), HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Subject> createSubject(@RequestBody Subject subject) throws Throwable {
        return new ResponseEntity<>(subjectService.save(subject), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Subject> deleteSubject(@PathVariable final Long id) throws Throwable {
        subjectService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/lecturer/{id}")
    public ResponseEntity<Subject> findByLecturer(@PathVariable final Long id) throws Throwable {
        Optional<Lecturer> byId = lecturerRepo.findById(id);
        if (byId.isPresent()) {
            Subject byLecturer = subjectService.findByLecturer(byId.get());
            return new ResponseEntity<>(byLecturer, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
