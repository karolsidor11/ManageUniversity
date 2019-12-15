package pl.sidor.ManageUniversity.lecturer.service;

import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import pl.sidor.ManageUniversity.dto.LecturerDTO;
import pl.sidor.ManageUniversity.exception.ExceptionFactory;
import pl.sidor.ManageUniversity.lecturer.model.Lecturer;
import pl.sidor.ManageUniversity.lecturer.repository.LecturerRepo;
import pl.sidor.ManageUniversity.mapper.LecturerMapper;
import pl.sidor.ManageUniversity.request.FindScheduleRequest;
import pl.sidor.ManageUniversity.schedule.model.Schedule;
import pl.sidor.ManageUniversity.schedule.model.Subject;
import pl.sidor.ManageUniversity.schedule.repository.ScheduleRepo;
import pl.sidor.ManageUniversity.schedule.repository.SubjectRepo;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;
import static java.util.Optional.of;
import static java.util.Optional.ofNullable;

@Transactional
@AllArgsConstructor
public class LecturerServiceImpl implements LecturerService {

    private final LecturerRepo lecturerRepo;
    private final SubjectRepo subjectRepo;
    private final ScheduleRepo scheduleRepo;

    @Override
    public Lecturer findById(final Long id) throws Throwable {
        return lecturerRepo.findById(id).orElseThrow(ExceptionFactory.incorrectLecturerID(String.valueOf(id)));
    }

    @Override
    public LecturerDTO findLecturerDTO( final Long id) throws Throwable {
        Optional<Lecturer> byId = lecturerRepo.findById(id);
        return byId.map(lecturer -> LecturerMapper.mapTo(byId.get()))
               .orElseThrow(ExceptionFactory.incorrectLecturerID(String.valueOf(id)));
    }

    @Override
    public Lecturer create( final Lecturer lecturer) throws Throwable {
      return  ofNullable(lecturer).map(lecturer1 -> lecturerRepo.save(lecturer))
              .orElseThrow(ExceptionFactory.objectIsEmpty("!!!"));
    }

    @Override
    public void update( final Lecturer lecturer) throws Throwable {
        Lecturer updateLecturer = of(findById(lecturer.getId()))
                .map(lecturer1 -> createLecturer(lecturer1, lecturer))
                .orElseThrow(ExceptionFactory.incorrectLecturerID(String.valueOf(lecturer.getId())));

        lecturerRepo.save(updateLecturer);
    }

    @Override
    public void delete( final Long id) throws Throwable {
        of(findById(id)).ifPresent(lecturer -> lecturerRepo.deleteById(id));
    }

    @Override
    public List<Schedule> findScheduleForLecturer( final FindScheduleRequest request) throws Throwable {

        Optional<Lecturer> byNameAndLastName = lecturerRepo.findByNameAndLastName(request.getName(), request.getLastName());
        Optional<Subject> subject = subjectRepo.findByLecturer(byNameAndLastName.orElseThrow(ExceptionFactory.objectIsEmpty("!!!")));
        List<Schedule> bySubjects = scheduleRepo.findBySubjects(subject.orElseThrow(ExceptionFactory.objectIsEmpty("!!!")));

        Optional<List<Schedule>> collect = of(bySubjects.stream()
                .filter(schedule -> nonNull(schedule.getWeekNumber()))
                .filter(schedule -> schedule.getWeekNumber().equals(request.getWeekNumber()))
                .collect(Collectors.toList()));

        return collect.orElseThrow(ExceptionFactory.nieoczekianyBladSystemu
                (request.getName(), request.getLastName(), request.getWeekNumber()));
    }

    private Lecturer createLecturer(final Lecturer actual,  final Lecturer updateLecturer) {
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
