package pl.sidor.ManageUniversity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.sidor.ManageUniversity.lecturer.model.Lecturer;
import pl.sidor.ManageUniversity.lecturer.service.LecturerServiceImpl;

@RestController
public class LecturerController {

    private LecturerServiceImpl lecturerServiceImpl;

    @Autowired
    public LecturerController(LecturerServiceImpl lecturerServiceImpl) {
        this.lecturerServiceImpl = lecturerServiceImpl;
    }

    @GetMapping(value = "/findLecturer/{id}")
    public ResponseEntity<Lecturer> findByID(@PathVariable long id) {

        Lecturer byId = lecturerServiceImpl.findById(id);

        return new ResponseEntity<>(byId, HttpStatus.OK);
    }

    @PostMapping(value = "/saveLecturer", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Lecturer> create(@RequestBody Lecturer lecturer) {

        Lecturer lecturer1 = lecturerServiceImpl.create(lecturer);

        return new ResponseEntity<>(lecturer1, HttpStatus.OK);
    }

    @DeleteMapping(value = "/deleteLecturer/{id}")
    public ResponseEntity<Lecturer> deleteLecturer(@PathVariable long id) {

        lecturerServiceImpl.delete(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "updateLecturer", consumes = MediaType.APPLICATION_JSON_VALUE, produces =  MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Lecturer> updateLecturer(@RequestBody Lecturer lecturer){

        lecturerServiceImpl.update(lecturer);

        return  new ResponseEntity<>(HttpStatus.OK);
    }
}
