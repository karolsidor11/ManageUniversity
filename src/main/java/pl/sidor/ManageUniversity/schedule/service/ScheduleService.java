package pl.sidor.ManageUniversity.schedule.service;

import pl.sidor.ManageUniversity.dto.ScheduleDTO;
import pl.sidor.ManageUniversity.request.FindScheduleRequest;
import pl.sidor.ManageUniversity.request.ScheduleUpdate;
import pl.sidor.ManageUniversity.schedule.enums.Days;
import pl.sidor.ManageUniversity.schedule.model.Schedule;
import pl.sidor.ManageUniversity.schedule.response.ScheduleResponse;

import java.util.List;

public interface ScheduleService {

    ScheduleResponse create(final Schedule schedule);

    ScheduleResponse getScheduleById(final Long id);

    ScheduleResponse findByDay(final Days day) ;

    ScheduleResponse deleteByID(final Long id);

    ScheduleResponse deleteByDay(final Days day);

    ScheduleResponse updateSchedule(final Schedule schedule);

    ScheduleResponse modifySchedule(final ScheduleUpdate scheduleUpdate);

    List<ScheduleDTO> findSchedule(final FindScheduleRequest request) throws Throwable;

    List<Schedule> findByStudentGroupAndWeekNumber(final Double studentGroup, final Integer weekNumber);

    List<Schedule> findScheduleForLecturer(final FindScheduleRequest request) throws Throwable;

    List<Schedule> findScheduleForStudent(final FindScheduleRequest request) throws Throwable;
}
