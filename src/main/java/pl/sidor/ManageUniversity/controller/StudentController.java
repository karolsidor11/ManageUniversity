package pl.sidor.ManageUniversity.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.sidor.ManageUniversity.dto.ScheduleDTO;
import pl.sidor.ManageUniversity.request.FindScheduleRequest;
import pl.sidor.ManageUniversity.schedule.model.Schedule;
import pl.sidor.ManageUniversity.student.model.Student;
import pl.sidor.ManageUniversity.student.service.StudentService;
import pl.sidor.ManageUniversity.student.service.StudentServiceImpl;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "student")
public class StudentController {

    private StudentService studentServiceImpl;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Student> findStudentByID(@PathVariable long id) throws Throwable {

        return new ResponseEntity<>(studentServiceImpl.findById(id), HttpStatus.OK);
    }

    @PostMapping(value = "/save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Student> createStudent(@RequestBody Student student) throws Throwable {

        return new ResponseEntity<>(studentServiceImpl.create(student), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Student> deleteStudent(@PathVariable long id) throws Throwable {

        studentServiceImpl.delete(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateStudent(@RequestBody Student student) throws Throwable {

        studentServiceImpl.update(student);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/findSchedule")
    public ResponseEntity<List> findScheduleForStudent(@RequestBody FindScheduleRequest request) throws Throwable {

        List<Schedule> scheduleForStudent = studentServiceImpl.findScheduleForStudent(request);

        return new ResponseEntity<>(scheduleForStudent, HttpStatus.OK);
    }

    @GetMapping("/scheduleStudent")
    public ResponseEntity<List<? extends ScheduleDTO>> findSchedule(@RequestBody FindScheduleRequest request) throws Throwable {
        List<ScheduleDTO> schedule = studentServiceImpl.findSchedule(request);
        return new ResponseEntity<>(schedule, HttpStatus.OK);

    }
}
