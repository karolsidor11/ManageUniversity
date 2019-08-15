package pl.sidor.ManageUniversity.schedule.service;

import pl.sidor.ManageUniversity.schedule.enums.Days;
import pl.sidor.ManageUniversity.schedule.model.Schedule;

public interface ScheduleService {

    Schedule create(Schedule schedule);

    Schedule getScheduleById(Long id);

    Schedule findByDay(Days day);

    boolean deleteByID(Long id);

    void deleteByDay(Days day);

    Schedule updateSchedule(Schedule schedule);
}
