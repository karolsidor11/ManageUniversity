package pl.sidor.ManageUniversity.schedule.service;

import pl.sidor.ManageUniversity.exception.UniversityException;
import pl.sidor.ManageUniversity.schedule.enums.Days;
import pl.sidor.ManageUniversity.schedule.model.Schedule;

public interface ScheduleService {

    Schedule create(Schedule schedule) throws Throwable;

    Schedule getScheduleById(Long id) throws Throwable;

    Schedule findByDay(Days day) throws Throwable;

    boolean deleteByID(Long id) throws Throwable;

    void deleteByDay(Days day) throws Throwable;

    Schedule updateSchedule(Schedule schedule) throws Throwable;
}
