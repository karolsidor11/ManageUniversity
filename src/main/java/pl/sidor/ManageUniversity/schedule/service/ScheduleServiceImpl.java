package pl.sidor.ManageUniversity.schedule.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.sidor.ManageUniversity.exception.ExceptionFactory;
import pl.sidor.ManageUniversity.lecturer.model.Lecturer;
import pl.sidor.ManageUniversity.mapper.LecturerDTO;
import pl.sidor.ManageUniversity.mapper.LecturerMapper;
import pl.sidor.ManageUniversity.schedule.enums.Days;
import pl.sidor.ManageUniversity.schedule.model.Schedule;
import pl.sidor.ManageUniversity.schedule.model.Subject;
import pl.sidor.ManageUniversity.schedule.repository.ScheduleRepo;
import pl.sidor.ManageUniversity.schedule.validator.ScheduleValidator;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import static java.util.Optional.of;

@Service
@Transactional
public class ScheduleServiceImpl implements ScheduleService {

    private ScheduleRepo scheduleRepo;
    private ScheduleValidator scheduleValidator;

    @Autowired
    public ScheduleServiceImpl(ScheduleRepo scheduleRepo, ScheduleValidator scheduleValidator) {
        this.scheduleRepo = scheduleRepo;
        this.scheduleValidator = scheduleValidator;
    }

    @Override
    public Schedule create(Schedule schedule) throws Throwable {

        return of(schedule).filter(schedule1 -> scheduleValidator
                .test(schedule.getDayOfWeek())).map(schedule1 -> scheduleRepo.save(schedule))
                .orElseThrow(ExceptionFactory.incorectScheduleDay(schedule.getDayOfWeek().getDay()));
    }

    @Override
    public Schedule getScheduleById(Long id) throws Throwable {
        return scheduleRepo.findById(id).orElseThrow(ExceptionFactory.incorrectScheduleID(String.valueOf(id)));

    }

    @Override
    public Schedule findByDay(Days day) throws Throwable {

        return scheduleRepo.findByDayOfWeek(day).orElseThrow(ExceptionFactory.incorrectPlanDay(day.getDay()));
    }

    @Override
    public boolean deleteByID(Long id) throws Throwable {

        of(getScheduleById(id)).ifPresent(schedule -> scheduleRepo.deleteById(id));

        return true;
    }

    @Override
    public void deleteByDay(Days day) throws Throwable {

        of(findByDay(day)).ifPresent(schedule -> scheduleRepo.deleteByDayOfWeek(day));
    }

    @Override
    public Schedule updateSchedule(Schedule schedule) throws Throwable {

        Schedule.ScheduleBuilder builder = Schedule.builder();

        of(findByDay(schedule.getDayOfWeek())).ifPresent(getUpdateSchedule(schedule, builder));
        Schedule build = builder.dayOfWeek(schedule.getDayOfWeek()).build();

        return scheduleRepo.save(build);
    }

    @Override
    public List<Schedule> findByStudentGroupAndWeekNumber(Double studentGroup, Integer weekNumber) {

        return scheduleRepo.findByStudentGroupAndWeekNumber(studentGroup, weekNumber);
    }

    private Consumer<Schedule> getUpdateSchedule(Schedule schedule, Schedule.ScheduleBuilder builder) {
        return schedule1 -> builder.id(schedule.getId()).subjects(schedule.getSubjects());
    }
}
