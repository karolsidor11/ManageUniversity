package pl.sidor.ManageUniversity.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("subject")
@AllArgsConstructor
public class SubjectController {

    private SubjectService subjectService;
    private LecturerRepo lecturerRepo;

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Subject> createSubject(@RequestBody Subject subject) throws Throwable {

        return new ResponseEntity<>(subjectService.save(subject), HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Subject> getById(@PathVariable Long id) throws Throwable {

        Subject byId = subjectService.getById(id);
        return new ResponseEntity<>(byId, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Subject> deleteSubject(@PathVariable Long id) throws Throwable {

        subjectService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/lecturer/{id}")
    public ResponseEntity<Subject> findByLecturer(@PathVariable Long id){

        Optional<Lecturer> byId = lecturerRepo.findById(id);

        Subject byLecturer = subjectService.findByLecturer(byId.get());

        return  new ResponseEntity<>(byLecturer, HttpStatus.OK);
    }
}
