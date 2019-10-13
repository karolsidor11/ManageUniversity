package pl.sidor.ManageUniversity.schedule.service;

import pl.sidor.ManageUniversity.request.ScheduleUpdate;
import pl.sidor.ManageUniversity.schedule.enums.Days;
import pl.sidor.ManageUniversity.schedule.model.Schedule;

import java.util.List;

public interface ScheduleService {

    Schedule create(Schedule schedule) throws Throwable;

    Schedule getScheduleById(Long id) throws Throwable;

    Schedule findByDay(Days day) throws Throwable;

    boolean deleteByID(Long id) throws Throwable;

    void deleteByDay(Days day) throws Throwable;

    Schedule updateSchedule(Schedule schedule) throws Throwable;

    List<Schedule> findByStudentGroupAndWeekNumber(Double studentGroup, Integer weekNumber);

    Schedule modifySchedule(ScheduleUpdate scheduleUpdate) throws Throwable;
}
