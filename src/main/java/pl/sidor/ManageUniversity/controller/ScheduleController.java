package pl.sidor.ManageUniversity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.sidor.ManageUniversity.schedule.model.Schedule;
import pl.sidor.ManageUniversity.schedule.service.ScheduleService;

@RestController
@RequestMapping(value = "/schedule")
public class ScheduleController {

    private ScheduleService scheduleService;

    @Autowired
    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Schedule> getScheduleByID(@PathVariable long id) {

        Schedule scheduleById = scheduleService.getScheduleById(id);

        return new ResponseEntity<>(scheduleById, HttpStatus.OK);
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Schedule> create(@RequestBody Schedule schedule) {

        Schedule schedule1 = scheduleService.create(schedule);

        return new ResponseEntity<>(schedule1, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "delete/{id}")
    public ResponseEntity<Boolean> deleteSchedule(@PathVariable Long id) {

        boolean result = scheduleService.deleteByID(id);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value = "update")
    public ResponseEntity<Schedule> updateSchedule(@RequestBody Schedule schedule) {

        Schedule updateSchedule = scheduleService.updateSchedule(schedule);

        return new ResponseEntity<>(updateSchedule, HttpStatus.CREATED);
    }
}
