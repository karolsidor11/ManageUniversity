package pl.sidor.ManageUniversity.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.sidor.ManageUniversity.lecturer.model.Lecturer;
import pl.sidor.ManageUniversity.lecturer.response.LecturerResponse;
import pl.sidor.ManageUniversity.lecturer.service.LecturerService;

@RestController
@AllArgsConstructor
@RequestMapping(value = "lecturers")
public class LecturerController {

    private final LecturerService lecturerServiceImpl;

    @GetMapping(value = "/{id}")
    public ResponseEntity<LecturerResponse> findByID(@PathVariable final Long id)  {
        return new ResponseEntity<>(lecturerServiceImpl.findById(id), HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LecturerResponse> create(@RequestBody Lecturer lecturer)  {
        return new ResponseEntity<>(lecturerServiceImpl.create(lecturer), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<LecturerResponse> deleteLecturer(@PathVariable final Long id) {
        return new ResponseEntity<>(lecturerServiceImpl.delete(id), HttpStatus.OK);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LecturerResponse> updateLecturer(@RequestBody Lecturer lecturer) {
        return new ResponseEntity<>(lecturerServiceImpl.update(lecturer),HttpStatus.OK);
    }

    @GetMapping(value = "/lecturerDTO/{id}")
    public ResponseEntity<LecturerResponse> findLecturerDTO(@PathVariable final Long id)  {
        return new ResponseEntity<>( lecturerServiceImpl.findLecturerDTO(id), HttpStatus.OK);
    }
}
