package pl.sidor.ManageUniversity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.sidor.ManageUniversity.exception.UniversityException;
import pl.sidor.ManageUniversity.lecturer.model.Lecturer;
import pl.sidor.ManageUniversity.lecturer.service.LecturerServiceImpl;
import pl.sidor.ManageUniversity.schedule.model.Schedule;

import java.util.List;

@RestController
public class LecturerController {

    private LecturerServiceImpl lecturerServiceImpl;

    @Autowired
    public LecturerController(LecturerServiceImpl lecturerServiceImpl) {
        this.lecturerServiceImpl = lecturerServiceImpl;
    }

    @GetMapping(value = "/findLecturer/{id}")
    public ResponseEntity<Lecturer> findByID(@PathVariable Long id) throws Throwable {

        return new ResponseEntity<>(lecturerServiceImpl.findById(id), HttpStatus.OK);
    }

    @PostMapping(value = "/saveLecturer", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Lecturer> create(@RequestBody Lecturer lecturer) throws Throwable {

        return new ResponseEntity<>(lecturerServiceImpl.create(lecturer), HttpStatus.OK);
    }

    @DeleteMapping(value = "/deleteLecturer/{id}")
    public ResponseEntity<Lecturer> deleteLecturer(@PathVariable Long id) throws Throwable {

        lecturerServiceImpl.delete(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "updateLecturer", consumes = MediaType.APPLICATION_JSON_VALUE, produces =  MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Lecturer> updateLecturer(@RequestBody Lecturer lecturer) throws Throwable {

        lecturerServiceImpl.update(lecturer);

        return  new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "findSchedule/lecturer")
    public ResponseEntity<List<Schedule> >findScheduleForLecturer(@RequestParam String name, @RequestParam String lastName, Integer weekNumber) throws Throwable {

        List<Schedule> scheduleForLecturer = lecturerServiceImpl.findScheduleForLecturer(name, lastName, weekNumber);
        return  new ResponseEntity<>( scheduleForLecturer, HttpStatus.OK);

    }
}
