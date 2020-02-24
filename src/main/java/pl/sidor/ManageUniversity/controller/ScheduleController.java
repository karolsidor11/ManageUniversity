package pl.sidor.ManageUniversity.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.sidor.ManageUniversity.lecturer.service.LecturerService;
import pl.sidor.ManageUniversity.request.FindScheduleRequest;
import pl.sidor.ManageUniversity.request.ScheduleUpdate;
import pl.sidor.ManageUniversity.schedule.enums.Days;
import pl.sidor.ManageUniversity.schedule.model.Schedule;
import pl.sidor.ManageUniversity.schedule.service.ScheduleService;
import pl.sidor.ManageUniversity.student.service.StudentService;

import java.util.List;
import java.util.Optional;

import static java.util.Optional.of;

@RestController
@AllArgsConstructor
@RequestMapping(value = "schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final LecturerService lecturerServiceImpl;
    private final StudentService studentServiceImpl;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Schedule> getScheduleByID(@PathVariable final Long id) throws Throwable {
        return new ResponseEntity<>(scheduleService.getScheduleById(id), HttpStatus.OK);
    }

    @GetMapping(value = "/day/{day}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Schedule> getByDay(@PathVariable final Days day) throws Throwable {
        return new ResponseEntity<>(scheduleService.findByDay(day), HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Schedule> create(@RequestBody Schedule schedule) throws Throwable {
        return new ResponseEntity<>(scheduleService.create(schedule), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Boolean> deleteSchedule(@PathVariable final Long id) throws Throwable {
        return new ResponseEntity<>(scheduleService.deleteByID(id), HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{day}")
    public ResponseEntity<Schedule> deleteByDay(@PathVariable final Days day) throws Throwable {
        scheduleService.deleteByDay(day);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Schedule> updateSchedule(@RequestBody Schedule schedule) throws Throwable {
        return new ResponseEntity<>(scheduleService.updateSchedule(schedule), HttpStatus.CREATED);
    }

    @PatchMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Schedule> modifySchedule(@RequestBody ScheduleUpdate scheduleUpdate) throws Throwable {
        Optional<Schedule> schedule = of(scheduleService.modifySchedule(scheduleUpdate));
        return new ResponseEntity<>(schedule.get(), HttpStatus.ACCEPTED);
    }

    @GetMapping(value = "/lecturer", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Schedule>> findScheduleForLecturer(@RequestBody FindScheduleRequest request) throws Throwable {
        List<Schedule> scheduleForLecturer = lecturerServiceImpl.findScheduleForLecturer(request);
        return new ResponseEntity<>(scheduleForLecturer, HttpStatus.OK);
    }

    @GetMapping(value = "/student", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Schedule>> findScheduleForStudent(@RequestBody FindScheduleRequest request) throws Throwable {
        List<Schedule> scheduleForStudent = studentServiceImpl.findScheduleForStudent(request);
        return new ResponseEntity<>(scheduleForStudent, HttpStatus.OK);
    }
}
