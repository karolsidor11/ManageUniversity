package pl.sidor.ManageUniversity.schedule.service;

import pl.sidor.ManageUniversity.exception.UniversityException;
import pl.sidor.ManageUniversity.schedule.enums.Days;
import pl.sidor.ManageUniversity.schedule.model.Schedule;

public interface ScheduleService {

    Schedule create(Schedule schedule);

    Schedule getScheduleById(Long id);

    Schedule findByDay(Days day);

    boolean deleteByID(Long id) throws UniversityException;

    void deleteByDay(Days day) throws UniversityException;

    Schedule updateSchedule(Schedule schedule);
}
