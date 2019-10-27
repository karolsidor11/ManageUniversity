package pl.sidor.ManageUniversity.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.sidor.ManageUniversity.exception.UniversityException;
import pl.sidor.ManageUniversity.lecturer.model.Lecturer;
import pl.sidor.ManageUniversity.lecturer.service.LecturerService;
import pl.sidor.ManageUniversity.lecturer.service.LecturerServiceImpl;
import pl.sidor.ManageUniversity.dto.LecturerDTO;
import pl.sidor.ManageUniversity.request.FindScheduleRequest;
import pl.sidor.ManageUniversity.schedule.model.Schedule;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "lecturer")
public class LecturerController {

    private LecturerService lecturerServiceImpl;

    @GetMapping(value = "/{id}")
    public ResponseEntity<Lecturer> findByID(@PathVariable Long id) throws Throwable {

        return new ResponseEntity<>(lecturerServiceImpl.findById(id), HttpStatus.OK);
    }

    @PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Lecturer> create(@RequestBody Lecturer lecturer) throws Throwable {

        return new ResponseEntity<>(lecturerServiceImpl.create(lecturer), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Lecturer> deleteLecturer(@PathVariable Long id) throws Throwable {

        lecturerServiceImpl.delete(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Lecturer> updateLecturer(@RequestBody Lecturer lecturer) throws Throwable {

        lecturerServiceImpl.update(lecturer);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/findSchedule")
    public ResponseEntity<List<Schedule>> findScheduleForLecturer(@RequestBody FindScheduleRequest request) throws Throwable {

        List<Schedule> scheduleForLecturer = lecturerServiceImpl.findScheduleForLecturer(request);
        return new ResponseEntity<>(scheduleForLecturer, HttpStatus.OK);

    }

    @GetMapping(value = "/lecturerDTO/{id}")
    public ResponseEntity<LecturerDTO> findLecturerDTO(@PathVariable Long id) throws UniversityException {
        LecturerDTO lecturerDTO = lecturerServiceImpl.findLecturerDTO(id);
        return new ResponseEntity<>(lecturerDTO, HttpStatus.OK);

    }
}
