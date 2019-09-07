package pl.sidor.ManageUniversity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.sidor.ManageUniversity.exception.UniversityException;
import pl.sidor.ManageUniversity.schedule.enums.Days;
import pl.sidor.ManageUniversity.schedule.model.Schedule;
import pl.sidor.ManageUniversity.schedule.service.ScheduleService;
import pl.sidor.ManageUniversity.schedule.service.ScheduleServiceImpl;

@RestController
@RequestMapping(value = "/schedule")
public class ScheduleController {

    private ScheduleServiceImpl scheduleService;

    @Autowired
    public ScheduleController(ScheduleServiceImpl scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Schedule> getScheduleByID(@PathVariable Long id) throws Throwable {

        return new ResponseEntity<>(scheduleService.getScheduleById(id), HttpStatus.OK);
    }

    @GetMapping(value = "/getByDay/{day}" , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Schedule> getByDay(@PathVariable Days day) throws Throwable {

        return  new ResponseEntity<>(scheduleService.findByDay(day), HttpStatus.OK);
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Schedule> create(@RequestBody Schedule schedule) throws Throwable {

        return new ResponseEntity<>(scheduleService.create(schedule), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "delete/{id}")
    public ResponseEntity<Boolean> deleteSchedule(@PathVariable Long id) throws Throwable {

        return new ResponseEntity<>(scheduleService.deleteByID(id), HttpStatus.OK);
    }

    @DeleteMapping(value = "deleteBy/{day}")
    public ResponseEntity deleteByDay(@PathVariable Days day) throws Throwable {

         scheduleService.deleteByDay(day);

        return new ResponseEntity<>( HttpStatus.OK);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<Schedule> updateSchedule(@RequestBody Schedule schedule) throws Throwable {

        return new ResponseEntity<>(scheduleService.updateSchedule(schedule), HttpStatus.CREATED);
    }
}
