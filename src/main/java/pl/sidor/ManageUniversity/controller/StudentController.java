package pl.sidor.ManageUniversity.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import pl.sidor.ManageUniversity.dto.ScheduleDTO;
import pl.sidor.ManageUniversity.model.Adres;
import pl.sidor.ManageUniversity.request.FindScheduleRequest;
import pl.sidor.ManageUniversity.schedule.model.Schedule;
import pl.sidor.ManageUniversity.student.model.Student;
import pl.sidor.ManageUniversity.student.model.Student_;
import pl.sidor.ManageUniversity.student.service.StudentService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "students")
public class StudentController {

    private StudentService studentServiceImpl;

    @PersistenceContext
    private EntityManager entityManager;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Student> findStudentByID(@PathVariable long id) throws Throwable {

        return new ResponseEntity<>(studentServiceImpl.findById(id), HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Student> createStudent(@RequestBody Student student) throws Throwable {

        return new ResponseEntity<>(studentServiceImpl.create(student), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Student> deleteStudent(@PathVariable long id) throws Throwable {

        studentServiceImpl.delete(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Student> updateStudent(@RequestBody Student student) throws Throwable {

        studentServiceImpl.update(student);

        return new ResponseEntity<>(HttpStatus.OK);
    }

//    todo do Schedule
    @GetMapping(value = "/findSchedule")
    public ResponseEntity<List> findScheduleForStudent(@RequestBody FindScheduleRequest request) throws Throwable {

        List<Schedule> scheduleForStudent = studentServiceImpl.findScheduleForStudent(request);

        return new ResponseEntity(scheduleForStudent, HttpStatus.OK);
    }

//    todo  do schedule
    @GetMapping("/scheduleStudent")
    public ResponseEntity<List<? extends ScheduleDTO>> findSchedule(@RequestBody FindScheduleRequest request) throws Throwable {
        List<ScheduleDTO> schedule = studentServiceImpl.findSchedule(request);
        return new ResponseEntity<>(schedule, HttpStatus.OK);

    }
}
