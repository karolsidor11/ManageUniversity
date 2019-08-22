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
    public ResponseEntity<Schedule> getScheduleByID(@PathVariable Long id) {

        Schedule scheduleById = scheduleService.getScheduleById(id);

        return new ResponseEntity<>(scheduleById, HttpStatus.OK);
    }

    @GetMapping(value = "/getByDay/{day}" , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Schedule> getByDay(@PathVariable Days day){

        Schedule byDay = scheduleService.findByDay(day);

        return  new ResponseEntity<>(byDay, HttpStatus.OK);

    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Schedule> create(@RequestBody Schedule schedule) {

        Schedule schedule1 = scheduleService.create(schedule);

        return new ResponseEntity<>(schedule1, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "delete/{id}")
    public ResponseEntity<Boolean> deleteSchedule(@PathVariable Long id) throws UniversityException {

        boolean result = scheduleService.deleteByID(id);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping(value = "deleteBy/{day}")
    public ResponseEntity<Boolean> deleteByDay(@PathVariable Days day) throws UniversityException {

         scheduleService.deleteByDay(day);

        return new ResponseEntity<>( HttpStatus.OK);
    }

    @PostMapping(value = "/update")
    public ResponseEntity<Schedule> updateSchedule(@RequestBody Schedule schedule) {

        Schedule updateSchedule = scheduleService.updateSchedule(schedule);

        return new ResponseEntity<>(updateSchedule, HttpStatus.CREATED);
    }
}
