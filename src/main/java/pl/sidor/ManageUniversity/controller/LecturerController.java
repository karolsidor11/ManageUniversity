package pl.sidor.ManageUniversity.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.sidor.ManageUniversity.dto.LecturerDTO;
import pl.sidor.ManageUniversity.exception.UniversityException;
import pl.sidor.ManageUniversity.lecturer.model.Lecturer;
import pl.sidor.ManageUniversity.lecturer.service.LecturerService;

@RestController
@AllArgsConstructor
@RequestMapping(value = "lecturers")
public class LecturerController {

    private final LecturerService lecturerServiceImpl;

    @GetMapping(value = "/{id}")
    public ResponseEntity<Lecturer> findByID(@PathVariable final Long id) throws Throwable {
        return new ResponseEntity<>(lecturerServiceImpl.findById(id), HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Lecturer> create(@RequestBody Lecturer lecturer) throws Throwable {
        return new ResponseEntity<>(lecturerServiceImpl.create(lecturer), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Lecturer> deleteLecturer(@PathVariable final Long id) throws Throwable {
        lecturerServiceImpl.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Lecturer> updateLecturer(@RequestBody Lecturer lecturer) throws Throwable {
        lecturerServiceImpl.update(lecturer);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/lecturerDTO/{id}")
    public ResponseEntity<LecturerDTO> findLecturerDTO(@PathVariable final Long id) throws Throwable {
        LecturerDTO lecturerDTO = lecturerServiceImpl.findLecturerDTO(id);
        return new ResponseEntity<>(lecturerDTO, HttpStatus.OK);
    }
}
