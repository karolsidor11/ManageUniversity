package pl.sidor.ManageUniversity.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.sidor.ManageUniversity.request.FindScheduleRequest;
import pl.sidor.ManageUniversity.request.ScheduleUpdate;
import pl.sidor.ManageUniversity.schedule.enums.Days;
import pl.sidor.ManageUniversity.schedule.model.Schedule;
import pl.sidor.ManageUniversity.schedule.response.ScheduleResponse;
import pl.sidor.ManageUniversity.schedule.service.ScheduleService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ScheduleResponse> getScheduleByID(@PathVariable final Long id) {
        return new ResponseEntity<>(scheduleService.getScheduleById(id), HttpStatus.OK);
    }

    @GetMapping(value = "/day/{day}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ScheduleResponse> getByDay(@PathVariable final Days day) {
        return new ResponseEntity<>(scheduleService.findByDay(day), HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ScheduleResponse> create(@RequestBody Schedule schedule) {
        return new ResponseEntity<>(scheduleService.create(schedule), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ScheduleResponse> deleteSchedule(@PathVariable final Long id) {
        return new ResponseEntity<>(scheduleService.deleteByID(id), HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{day}")
    public ResponseEntity<ScheduleResponse> deleteByDay(@PathVariable final Days day) {
        return new ResponseEntity<>(scheduleService.deleteByDay(day), HttpStatus.OK);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ScheduleResponse> updateSchedule(@RequestBody Schedule schedule) {
        return new ResponseEntity<>(scheduleService.updateSchedule(schedule), HttpStatus.CREATED);
    }

    @PatchMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ScheduleResponse> modifySchedule(@RequestBody ScheduleUpdate scheduleUpdate) {
        return new ResponseEntity<>(scheduleService.modifySchedule(scheduleUpdate), HttpStatus.ACCEPTED);
    }

    @GetMapping(value = "/lecturer", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Schedule>> findScheduleForLecturer(@RequestBody FindScheduleRequest request) throws Throwable {
        List<Schedule> scheduleForLecturer = scheduleService.findScheduleForLecturer(request);
        return new ResponseEntity<>(scheduleForLecturer, HttpStatus.OK);
    }

    @GetMapping(value = "/student", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Schedule>> findScheduleForStudent(@RequestBody FindScheduleRequest request) throws Throwable {
        List<Schedule> scheduleForStudent = scheduleService.findScheduleForStudent(request);
        return new ResponseEntity<>(scheduleForStudent, HttpStatus.OK);
    }
}
