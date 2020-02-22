package pl.sidor.ManageUniversity.schedule.service;

import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import pl.sidor.ManageUniversity.dto.ScheduleDTO;
import pl.sidor.ManageUniversity.exception.ExceptionFactory;
import pl.sidor.ManageUniversity.exception.ResponseException;
import pl.sidor.ManageUniversity.header.Header;
import pl.sidor.ManageUniversity.lecturer.model.Lecturer;
import pl.sidor.ManageUniversity.lecturer.repository.LecturerRepo;
import pl.sidor.ManageUniversity.mapper.ScheduleMapper;
import pl.sidor.ManageUniversity.request.FindScheduleRequest;
import pl.sidor.ManageUniversity.request.ScheduleUpdate;
import pl.sidor.ManageUniversity.schedule.enums.Days;
import pl.sidor.ManageUniversity.schedule.model.Schedule;
import pl.sidor.ManageUniversity.schedule.model.Subject;
import pl.sidor.ManageUniversity.schedule.repository.ScheduleRepo;
import pl.sidor.ManageUniversity.schedule.repository.SubjectRepo;
import pl.sidor.ManageUniversity.schedule.response.ScheduleResponse;
import pl.sidor.ManageUniversity.schedule.validator.ScheduleValidator;
import pl.sidor.ManageUniversity.student.model.Student;
import pl.sidor.ManageUniversity.student.service.StudentService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Collections.singletonList;
import static java.util.Optional.of;
import static java.util.Optional.ofNullable;
import static org.hibernate.validator.internal.util.CollectionHelper.newArrayList;

@Transactional
@AllArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepo scheduleRepo;
    private final LecturerRepo lecturerRepo;
    private final SubjectRepo subjectRepo;
    private final StudentService studentService;
    private final ScheduleValidator scheduleValidator;

    @Override
    public ScheduleResponse create(final Schedule schedule) {
        boolean isScheduleExists = scheduleValidator.test(schedule.getDayOfWeek());
        if (isScheduleExists) {
            return ScheduleResponse.prepareResponse(Optional.empty(), ResponseException.pustyObiekt());
        }
        Schedule savedSchedule = scheduleRepo.save(schedule);
        return ScheduleResponse.prepareResponse(Optional.of(savedSchedule), ResponseException.pustyObiekt());
    }

    @Override
    public ScheduleResponse getScheduleById(final Long id) {
        Optional<Schedule> scheduleByID = scheduleRepo.findById(id);
        if (scheduleByID.isEmpty()) {
            return ScheduleResponse.prepareResponse(Optional.empty(), ResponseException.brakRozkladu());
        }
        return ScheduleResponse.prepareResponse(scheduleByID, ResponseException.pustyObiekt());
    }

    @Override
    public ScheduleResponse findByDay(final Days day) {
        Optional<Schedule> byDayOfWeek = scheduleRepo.findByDayOfWeek(day);
        if (byDayOfWeek.isEmpty()) {
            return  ScheduleResponse.prepareResponse(Optional.empty(), ResponseException.brakRozkladu());
        }
        return ScheduleResponse.prepareResponse(byDayOfWeek, ResponseException.pustyObiekt());
    }

    @Override
    public ScheduleResponse deleteByID(final Long id) {
        ScheduleResponse scheduleById = getScheduleById(id);
        if (Objects.nonNull(scheduleById.getError())) {
            return ScheduleResponse.prepareResponse(Optional.empty(), ResponseException.pustyObiekt());
        }
        scheduleRepo.deleteById(id);
        return ScheduleResponse.builder().header(Header.getInstance()).build();
    }

    @Override
    public ScheduleResponse deleteByDay(final Days day) {
        Optional<Schedule> byDayOfWeek = scheduleRepo.findByDayOfWeek(day);
        if (byDayOfWeek.isEmpty()) {
            return ScheduleResponse.prepareResponse(Optional.empty(), ResponseException.pustyObiekt());
        }
        scheduleRepo.deleteByDayOfWeek(day);
        return ScheduleResponse.builder().header(Header.getInstance()).build();
    }

    @Override
    public ScheduleResponse updateSchedule(Schedule schedule) {
        Optional<Schedule> byDayOfWeek = scheduleRepo.findByDayOfWeek(schedule.getDayOfWeek());
        if (byDayOfWeek.isEmpty()) {
            return ScheduleResponse.prepareResponse(byDayOfWeek, ResponseException.brakRozkladu());
        }
        Schedule updateSchedule = getUpdateSchedule(schedule, byDayOfWeek.get());
        return ScheduleResponse.prepareResponse(Optional.of(updateSchedule), ResponseException.pustyObiekt());
    }

    @Override
    public List<Schedule> findByStudentGroupAndWeekNumber(final Double studentGroup, final Integer weekNumber) {
        return scheduleRepo.findByStudentGroupAndWeekNumber(studentGroup, weekNumber);
    }

    @Override
    public ScheduleResponse modifySchedule(final ScheduleUpdate scheduleUpdate) {
        Optional<Schedule> optionalSchedule = validateSchedule(scheduleUpdate);
        if (optionalSchedule.isEmpty()) {
            return  ScheduleResponse.prepareResponse(Optional.empty(), ResponseException.brakRozkladu());
        }
        Schedule schedule = optionalSchedule.get();
        Optional<Subject> subject = modifySubject(schedule, scheduleUpdate);

        if (subject.isEmpty()) {
            return  ScheduleResponse.prepareResponse(Optional.empty(), ResponseException.brakRozkladu());
        }

        schedule.getSubjects().remove(subject.get());
        schedule.setSubjects(newArrayList(singletonList(scheduleUpdate.getSubjects())));
        scheduleRepo.save(schedule);

        return ScheduleResponse.prepareResponse(Optional.of(schedule), ResponseException.pustyObiekt());
    }

    @Override
    public List<ScheduleDTO> findSchedule(FindScheduleRequest request) throws Throwable {
        Student byNameAndLastName = studentService.findByNameAndLastName(request.getName(), request.getLastName()).getStudent();
        List<Schedule> byStudentGroupAndWeekNumber = scheduleRepo.
                findByStudentGroupAndWeekNumber(byNameAndLastName.getStudentGroup(), request.getWeekNumber());
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();

        if (byStudentGroupAndWeekNumber.isEmpty()) {
            throw ExceptionFactory.objectIsEmpty("!!!");
        }
        byStudentGroupAndWeekNumber.forEach((schedule) -> {
            ScheduleDTO scheduleDTO = ScheduleMapper.mapTo(schedule);
            scheduleDTOS.add(scheduleDTO);
        });

        return scheduleDTOS;
    }

    @Override
    public List<Schedule> findScheduleForLecturer(FindScheduleRequest request) throws Throwable {
        Optional<Lecturer> byNameAndLastName = lecturerRepo.findByNameAndLastName(request.getName(), request.getLastName());
        Optional<Subject> subject = subjectRepo.findByLecturer(byNameAndLastName.orElseThrow(ExceptionFactory.objectIsEmpty("!!!")));
        List<Schedule> bySubjects = scheduleRepo.findBySubjects(subject.orElseThrow(ExceptionFactory.objectIsEmpty("!!!")));

        Optional<List<Schedule>> collect = of(bySubjects.stream().filter(schedule -> schedule.getWeekNumber() == (request.getWeekNumber())).collect(Collectors.toList()));

        return collect.orElseThrow(ExceptionFactory.nieoczekianyBladSystemu(request.getName(), request.getLastName(), request.getWeekNumber()));
    }

    @Override
    public List<Schedule> findScheduleForStudent(FindScheduleRequest request) throws Throwable {
        Student byNameAndLastName = studentService.findByNameAndLastName(request.getName(), request.getLastName()).getStudent();
        List<Schedule> schedules = scheduleRepo.findByStudentGroupAndWeekNumber(byNameAndLastName.getStudentGroup(), request.getWeekNumber());
        if (schedules.isEmpty()) {
            throw ExceptionFactory.nieoczekianyBladSystemu(request.getName(), request.getLastName(), request.getWeekNumber());
        }
        return schedules;
    }

    private Optional<Schedule> validateSchedule(ScheduleUpdate scheduleUpdate) {
        Days days = ofNullable(scheduleUpdate.getDayOfWeek()).orElseGet(() -> null);
        Integer weekNumber = of(scheduleUpdate.getWeekNumber()).orElseGet(() -> null);
        return scheduleRepo.findByDayOfWeekAndWeekNumber(days, weekNumber);
    }

    private Optional<Subject> modifySubject(Schedule schedule, ScheduleUpdate scheduleUpdate) {
        if (schedule.getSubjects().isEmpty()) {
            return Optional.empty();
        }
        return schedule.getSubjects().stream().filter(subject -> subject.getName().equals(scheduleUpdate.getSubjects().getName())).findFirst();
    }

    private Schedule getUpdateSchedule(Schedule schedule, Schedule builder) {
        builder.setDayOfWeek(schedule.getDayOfWeek());
        builder.setSubjects(schedule.getSubjects());
        builder.setWeekNumber(schedule.getWeekNumber());
        builder.setStudentGroup(schedule.getStudentGroup());
        return builder;
    }
}
