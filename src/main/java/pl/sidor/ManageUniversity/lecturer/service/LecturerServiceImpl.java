package pl.sidor.ManageUniversity.lecturer.service;

import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import pl.sidor.ManageUniversity.exception.ExceptionFactory;
import pl.sidor.ManageUniversity.exception.ResponseException;
import pl.sidor.ManageUniversity.header.Header;
import pl.sidor.ManageUniversity.lecturer.converter.LecturerConverter;
import pl.sidor.ManageUniversity.lecturer.model.Lecturer;
import pl.sidor.ManageUniversity.lecturer.repository.LecturerRepo;
import pl.sidor.ManageUniversity.lecturer.response.LecturerResponse;
import pl.sidor.ManageUniversity.request.FindScheduleRequest;
import pl.sidor.ManageUniversity.schedule.model.Schedule;
import pl.sidor.ManageUniversity.schedule.model.Subject;
import pl.sidor.ManageUniversity.schedule.repository.ScheduleRepo;
import pl.sidor.ManageUniversity.schedule.repository.SubjectRepo;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Optional.of;

@Transactional
@AllArgsConstructor
public class LecturerServiceImpl implements LecturerService {

    private final LecturerRepo lecturerRepo;
    private final SubjectRepo subjectRepo;
    private final ScheduleRepo scheduleRepo;

    @Override
    public LecturerResponse findById(final Long id) {
        Optional<Lecturer> lecturerById = lecturerRepo.findById(id);
        if(lecturerById.isEmpty()){
            return ResponseException.brakWykladowcy(id);
        }
        return  LecturerResponse.prepareLecturerResponse(lecturerById.get());
    }

    @Override
    public LecturerResponse findLecturerDTO( final Long id) {
        LecturerResponse lecturerResponse = findById(id);
        if(Objects.isNull(lecturerResponse.getLecturer())){
            return lecturerResponse;
        }
        Lecturer actualLecturer = LecturerConverter.convertDtoToLecturer(lecturerResponse.getLecturer());
        return LecturerResponse.prepareLecturerResponse(actualLecturer);
    }

    @Override
    public LecturerResponse create( final Lecturer lecturer)  {
        if(isNull(lecturer.getName())|| isNull(lecturer.getEmail())){
            return ResponseException.pustyObiekt();
        }
        Lecturer save = lecturerRepo.save(lecturer);
        return LecturerResponse.prepareLecturerResponse(save);
    }

    @Override
    public LecturerResponse update( final Lecturer lecturer) {
        LecturerResponse lecturerResponse = findById(lecturer.getId());
        if(Objects.isNull(lecturerResponse.getLecturer())){
            return lecturerResponse;
        }
        Lecturer updateLecturer = lecturerRepo.save(createLecturer(lecturerResponse.getLecturer(), lecturer));
        return LecturerResponse.prepareLecturerResponse(updateLecturer);
    }

    @Override
    public LecturerResponse delete( final Long id)  {
        LecturerResponse lecturerResponse = findById(id);
        if(Objects.nonNull(lecturerResponse.getLecturer())){
            lecturerRepo.deleteById(id);
            return LecturerResponse.builder().header(Header.getHeader()).build();
        }
        return lecturerResponse;
    }

    @Override
    public List<Schedule> findScheduleForLecturer( final FindScheduleRequest request) throws Throwable {

        Optional<Lecturer> byNameAndLastName = lecturerRepo.findByNameAndLastName(request.getName(), request.getLastName());
        Optional<Subject> subject = subjectRepo.findByLecturer(byNameAndLastName.orElseThrow(ExceptionFactory.objectIsEmpty("!!!")));
        List<Schedule> bySubjects = scheduleRepo.findBySubjects(subject.orElseThrow(ExceptionFactory.objectIsEmpty("!!!")));

        Optional<List<Schedule>> collect = of(bySubjects.stream()
                .filter(schedule -> schedule.getWeekNumber()==(request.getWeekNumber()))
                .collect(Collectors.toList()));

        return collect.orElseThrow(ExceptionFactory.nieoczekianyBladSystemu
                (request.getName(), request.getLastName(), request.getWeekNumber()));
    }

    private Lecturer createLecturer(Lecturer actual,  Lecturer updateLecturer) {
        actual.setId(updateLecturer.getId());
        actual.setName(updateLecturer.getName());
        actual.setLastName(updateLecturer.getLastName());
        actual.setEmail(updateLecturer.getEmail());
        actual.setPhoneNumber(updateLecturer.getPhoneNumber());
        actual.setAdres(updateLecturer.getAdres());
        actual.setGrade(updateLecturer.getGrade());
        actual.setSubject(updateLecturer.getSubject());

        return updateLecturer;
    }
}
