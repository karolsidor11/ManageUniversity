package pl.sidor.ManageUniversity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.sidor.ManageUniversity.schedule.model.Schedule;
import pl.sidor.ManageUniversity.student.model.Student;
import pl.sidor.ManageUniversity.student.service.StudentServiceImpl;

import java.util.List;

@RestController
public class StudentController {

    private StudentServiceImpl studentServiceImpl;

    @Autowired
    public StudentController(StudentServiceImpl studentServiceImpl) {
        this.studentServiceImpl = studentServiceImpl;
    }


    @GetMapping(value = "/findStudent/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Student> findStudentByID(@PathVariable long id) throws Throwable {

        return new ResponseEntity<>(studentServiceImpl.findById(id), HttpStatus.OK);
    }

    @PostMapping(value = "/saveStudent", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Student> createStudent(@RequestBody Student student) throws Throwable {

        return new ResponseEntity<>(studentServiceImpl.create(student), HttpStatus.OK);
    }

    @DeleteMapping(value = "/deleteStudent/{id}")
    public ResponseEntity<Student> deleteStudent(@PathVariable long id) throws Throwable {

        studentServiceImpl.delete(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/updateStudent", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity updateStudent(@RequestBody Student student) throws Throwable {

        studentServiceImpl.update(student);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "findSchedule/student")
    public ResponseEntity<List> findScheduleForStudent(@RequestParam("name") String name,
      @RequestParam("lastName") String lastName, @RequestParam("weekNumber") Integer weekNumber) throws Throwable {

        List<Schedule> scheduleForStudent = studentServiceImpl.findScheduleForStudent(name, lastName, weekNumber);

        return new ResponseEntity<>( scheduleForStudent, HttpStatus.OK);
    }
}
