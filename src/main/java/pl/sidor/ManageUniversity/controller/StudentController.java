package pl.sidor.ManageUniversity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.sidor.ManageUniversity.student.model.Student;
import pl.sidor.ManageUniversity.student.service.StudentService;

import java.util.Optional;

@RestController
public class StudentController {

    private StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }


    @GetMapping(value = "/findStudent/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Student> findStudentByID(@PathVariable long id) {

        Optional<Student> byId = Optional.of(studentService.findById(id));

        HttpStatus httpStatus = byId.isPresent() ? HttpStatus.OK : HttpStatus.NOT_FOUND;

        return new ResponseEntity<>(byId.get(), httpStatus);

    }

    @PostMapping(value = "/saveStudent", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {

        Student student1 = studentService.create(student);

        return new ResponseEntity<>(student1, HttpStatus.OK);
    }

    @DeleteMapping(value = "/deleteStudent/{id}")
    public ResponseEntity<Student> deleteStudent(@PathVariable long id) {

        studentService.delete(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "updateStudent" ,consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Student> updateStudent(@RequestBody Student student) {

        studentService.update(student);

        return new ResponseEntity<>( HttpStatus.OK);
    }
}
