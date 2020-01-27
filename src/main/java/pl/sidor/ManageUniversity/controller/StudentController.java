package pl.sidor.ManageUniversity.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.sidor.ManageUniversity.student.model.Student;
import pl.sidor.ManageUniversity.student.response.StudentResponse;
import pl.sidor.ManageUniversity.student.service.StudentService;


@RestController
@AllArgsConstructor
@RequestMapping(value = "students")
public class StudentController {

    private final StudentService studentServiceImpl;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StudentResponse> findStudentByID(@PathVariable final Long id) {
        StudentResponse studentResponse = studentServiceImpl.findById(id);
        return new ResponseEntity<>(studentResponse, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StudentResponse> createStudent(@RequestBody Student student)  {
        return new ResponseEntity<>(studentServiceImpl.create(student), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<StudentResponse> deleteStudent(@PathVariable final Long id)  {
        return new ResponseEntity<>(studentServiceImpl.delete(id), HttpStatus.OK);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StudentResponse> updateStudent(@RequestBody Student student)  {
        StudentResponse updateStudent = studentServiceImpl.update(student);
        return new ResponseEntity<>(updateStudent, HttpStatus.OK);
    }
}
