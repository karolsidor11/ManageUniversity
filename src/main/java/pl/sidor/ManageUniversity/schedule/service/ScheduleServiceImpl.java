package pl.sidor.ManageUniversity.schedule.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.sidor.ManageUniversity.exception.ExceptionFactory;
import pl.sidor.ManageUniversity.request.ScheduleUpdate;
import pl.sidor.ManageUniversity.schedule.enums.Days;
import pl.sidor.ManageUniversity.schedule.model.Schedule;
import pl.sidor.ManageUniversity.schedule.model.Subject;
import pl.sidor.ManageUniversity.schedule.repository.ScheduleRepo;
import pl.sidor.ManageUniversity.schedule.validator.ScheduleValidator;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import static java.util.Optional.of;

@AllArgsConstructor
@Transactional
public class ScheduleServiceImpl implements ScheduleService {

    private ScheduleRepo scheduleRepo;
    private ScheduleValidator scheduleValidator;

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

        return scheduleRepo.save(schedule);
    }

    @Override
    public List<Schedule> findByStudentGroupAndWeekNumber(Double studentGroup, Integer weekNumber) {

        return scheduleRepo.findByStudentGroupAndWeekNumber(studentGroup, weekNumber);
    }

    @Override
    public Schedule modifySchedule(ScheduleUpdate scheduleUpdate) throws Throwable {

        Schedule schedule = scheduleRepo.findByDayOfWeekAndWeekNumber(scheduleUpdate.getDayOfWeek(),
                scheduleUpdate.getWeekNumber()).orElseThrow(ExceptionFactory.objectIsEmpty("!!!"));

        List<Subject> subjects = schedule.getSubjects();

        Subject subject1 = schedule.getSubjects()
                .stream().filter(subject -> subject.getId().equals(scheduleUpdate.getSubjects().getId())).findFirst()
                .orElseThrow(ExceptionFactory.objectIsEmpty("Brak przedmiotu o podanym ID."));

        subjects.remove(subject1);
        schedule.setSubjects(Collections.singletonList(scheduleUpdate.getSubjects()));

         scheduleRepo.save(schedule);

        return  schedule;
    }

    private Consumer<Schedule> getUpdateSchedule(Schedule schedule, Schedule.ScheduleBuilder builder) {
        return schedule1 -> builder.id(schedule.getId()).subjects(schedule.getSubjects());
    }
}
