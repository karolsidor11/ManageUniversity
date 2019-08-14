package pl.sidor.ManageUniversity.schedule.service;

import pl.sidor.ManageUniversity.schedule.model.Schedule;

public interface ScheduleService {

    Schedule create(Schedule schedule);

    Schedule getScheduleById(Long id);

    boolean deleteByID(Long id);

    Schedule updateSchedule(Schedule schedule);
}
