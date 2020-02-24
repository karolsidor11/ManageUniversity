package pl.sidor.ManageUniversity.schedule.service;

import pl.sidor.ManageUniversity.request.ScheduleUpdate;
import pl.sidor.ManageUniversity.schedule.enums.Days;
import pl.sidor.ManageUniversity.schedule.model.Schedule;

import java.util.List;

public interface ScheduleService {

    Schedule create(final Schedule schedule) throws Throwable;

    Schedule getScheduleById(final Long id) throws Throwable;

    Schedule findByDay(final Days day) throws Throwable;

    boolean deleteByID(final Long id) throws Throwable;

    void deleteByDay(final Days day) throws Throwable;

    Schedule updateSchedule(final Schedule schedule) throws Throwable;

    List<Schedule> findByStudentGroupAndWeekNumber(final Double studentGroup, final Integer weekNumber);

    Schedule modifySchedule(final ScheduleUpdate scheduleUpdate) throws Throwable;
}
