package pl.sidor.ManageUniversity.schedule.service;

import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import pl.sidor.ManageUniversity.exception.ResponseException;
import pl.sidor.ManageUniversity.header.Header;
import pl.sidor.ManageUniversity.request.ScheduleUpdate;
import pl.sidor.ManageUniversity.schedule.enums.Days;
import pl.sidor.ManageUniversity.schedule.model.Schedule;
import pl.sidor.ManageUniversity.schedule.model.Subject;
import pl.sidor.ManageUniversity.schedule.repository.ScheduleRepo;
import pl.sidor.ManageUniversity.schedule.response.ScheduleResponse;
import pl.sidor.ManageUniversity.schedule.validator.ScheduleValidator;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.util.Collections.singletonList;
import static java.util.Optional.of;
import static java.util.Optional.ofNullable;
import static org.hibernate.validator.internal.util.CollectionHelper.newArrayList;

@Transactional
@AllArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepo scheduleRepo;
    private final ScheduleValidator scheduleValidator;

    @Override
    public ScheduleResponse create(final Schedule schedule)  {
        boolean isScheduleExists = scheduleValidator.test(schedule.getDayOfWeek());
        if(isScheduleExists){
            return ResponseException.istniejeRozklad();
        }
        Schedule savedSchedule = scheduleRepo.save(schedule);
        return ScheduleResponse.prepareScheduleResponse(savedSchedule);
    }

    @Override
    public ScheduleResponse getScheduleById(final Long id)  {
        Optional<Schedule> scheduleByID = scheduleRepo.findById(id);
        if(scheduleByID.isEmpty()){
            return ResponseException.brakRozkladu();
        }
        return ScheduleResponse.prepareScheduleResponse(scheduleByID.get());
    }

    @Override
    public ScheduleResponse findByDay(final Days day)  {
        Optional<Schedule> byDayOfWeek = scheduleRepo.findByDayOfWeek(day);
        if(byDayOfWeek.isEmpty()){
            return ResponseException.brakRozkladu();
        }
        return ScheduleResponse.prepareScheduleResponse(byDayOfWeek.get());
    }

    @Override
    public ScheduleResponse deleteByID(final Long id){
        ScheduleResponse scheduleById = getScheduleById(id);
        if(Objects.nonNull(scheduleById.getError())){
            return scheduleById;
        }
         scheduleRepo.deleteById(id);
        return ScheduleResponse.builder()
                .header(Header.getHeader())
                .build();
    }

    @Override
    public ScheduleResponse deleteByDay(final Days day)  {
        Optional<Schedule> byDayOfWeek = scheduleRepo.findByDayOfWeek(day);
        if(byDayOfWeek.isEmpty()){
            return ResponseException.brakRozkladu();
        }
        scheduleRepo.deleteByDayOfWeek(day);
        return ScheduleResponse.builder()
                .header(Header.getHeader())
                .build();
    }

    @Override
    public ScheduleResponse updateSchedule(Schedule schedule) {
        Optional<Schedule> byDayOfWeek = scheduleRepo.findByDayOfWeek(schedule.getDayOfWeek());
        if(byDayOfWeek.isEmpty()){
            return ResponseException.brakRozkladu();
        }
        Schedule updateSchedule = getUpdateSchedule(schedule, byDayOfWeek.get());
        return ScheduleResponse.prepareScheduleResponse(updateSchedule);
    }

    @Override
    public List<Schedule> findByStudentGroupAndWeekNumber(final Double studentGroup, final Integer weekNumber) {
        return scheduleRepo.findByStudentGroupAndWeekNumber(studentGroup, weekNumber);
    }

    @Override
    public ScheduleResponse modifySchedule(final ScheduleUpdate scheduleUpdate) {
        Optional<Schedule> optionalSchedule = validateSchedule(scheduleUpdate);
        if(optionalSchedule.isEmpty()){
            return ResponseException.brakRozkladu();
        }
        Schedule schedule = optionalSchedule.get();
        Optional<Subject> subject = modifySubject(schedule, scheduleUpdate);

        if(subject.isEmpty()){
            return ResponseException.brakRozkladu();
        }

        schedule.getSubjects().remove(subject.get());
        schedule.setSubjects(newArrayList(singletonList(scheduleUpdate.getSubjects())));
        scheduleRepo.save(schedule);

        return ScheduleResponse.prepareScheduleResponse(schedule);
    }

    private Optional<Schedule> validateSchedule(ScheduleUpdate scheduleUpdate){
        Days days = ofNullable(scheduleUpdate.getDayOfWeek()).orElseGet(() -> null);
        Integer weekNumber = of(scheduleUpdate.getWeekNumber()).orElseGet(() -> null);
        return scheduleRepo.findByDayOfWeekAndWeekNumber(days, weekNumber);
    }

    private Optional <Subject> modifySubject(Schedule schedule, ScheduleUpdate scheduleUpdate){
        if(schedule.getSubjects().isEmpty()){
            return Optional.empty();
        }
        return schedule.getSubjects().stream()
                .filter(subject -> subject.getName().equals(scheduleUpdate.getSubjects().getName()))
                .findFirst();
    }

    private Schedule getUpdateSchedule(Schedule schedule, Schedule builder) {
        builder.setDayOfWeek(schedule.getDayOfWeek());
        builder.setSubjects(schedule.getSubjects());
        builder.setWeekNumber(schedule.getWeekNumber());
        builder.setStudentGroup(schedule.getStudentGroup());
        return builder;
    }
}
