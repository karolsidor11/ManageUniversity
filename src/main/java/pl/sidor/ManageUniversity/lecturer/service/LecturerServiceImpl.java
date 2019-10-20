package pl.sidor.ManageUniversity.lecturer.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.sidor.ManageUniversity.dto.LecturerDTO;
import pl.sidor.ManageUniversity.exception.ExceptionFactory;
import pl.sidor.ManageUniversity.exception.UniversityException;
import pl.sidor.ManageUniversity.lecturer.model.Lecturer;
import pl.sidor.ManageUniversity.lecturer.repository.LecturerRepo;
import pl.sidor.ManageUniversity.lecturer.validation.CheckLecturer;
import pl.sidor.ManageUniversity.mapper.LecturerMapper;
import pl.sidor.ManageUniversity.request.FindScheduleRequest;
import pl.sidor.ManageUniversity.schedule.model.Schedule;
import pl.sidor.ManageUniversity.schedule.model.Subject;
import pl.sidor.ManageUniversity.schedule.repository.ScheduleRepo;
import pl.sidor.ManageUniversity.schedule.repository.SubjectRepo;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Optional.of;

@AllArgsConstructor
@Transactional
public class LecturerServiceImpl implements LecturerService {

    private LecturerRepo lecturerRepo;
    private CheckLecturer checkLecturer;
    private SubjectRepo subjectRepo;
    private ScheduleRepo scheduleRepo;

    @Override
    public Lecturer findById(long id) throws Throwable {

        return lecturerRepo.findById(id).orElseThrow(ExceptionFactory.incorrectLecturerID(String.valueOf(id)));
    }

    @Override
    public LecturerDTO findLecturerDTO(long id) throws UniversityException {
        Optional<Lecturer> byId = lecturerRepo.findById(id);

        if (byId.isPresent()) {
            return LecturerMapper.mapTo(byId.get());
        }
        throw ExceptionFactory.incorrectLecturerID(String.valueOf(id));
    }

    @Override
    public Lecturer create(Lecturer lecturer) throws Throwable {

        checkObject(lecturer);

        return of(lecturer).filter(checkLecturer).map(lecturer1 -> lecturerRepo.save(lecturer))
                .orElseThrow(ExceptionFactory.lecturerInDatabase(lecturer.getEmail()));
    }

    private void checkObject(Lecturer lecturer) throws UniversityException {
        if(lecturer.getName()==null || lecturer.getLastName()==null ||lecturer.getEmail()==null){
            throw  ExceptionFactory.objectIsEmpty("Wymagane jest imię, nazwisko, email. !!!");
        }
    }

    @Override
    public void update(Lecturer lecturer) throws Throwable {

        Lecturer lecturer2 = of(findById(lecturer.getId()))
                .map(lecturer1 -> createLecturer(lecturer1, lecturer))
                .orElseThrow(ExceptionFactory.incorrectLecturerID(String.valueOf(lecturer.getId())));

        lecturerRepo.save(lecturer2);
    }

    @Override
    public void delete(long id) throws Throwable {

        of(findById(id)).ifPresent(lecturer -> lecturerRepo.deleteById(id));
    }

    @Override
    public List<Schedule> findScheduleForLecturer(FindScheduleRequest request) throws Throwable {

        Optional<Lecturer> byNameAndLastName = lecturerRepo.findByNameAndLastName(request.getName(), request.getLastName());
        Optional<Subject> subject = subjectRepo.findByLecturer(byNameAndLastName.get());
        List<Schedule> bySubjects = scheduleRepo.findBySubjects(subject.get());

        Optional<List<Schedule>> collect = of(bySubjects.stream().filter(schedule -> Objects.nonNull(schedule.getWeekNumber()))
                .filter(schedule -> schedule.getWeekNumber().equals(request.getWeekNumber())).collect(Collectors.toList()));

        if (collect.get().isEmpty()) {
            throw ExceptionFactory.nieoczekianyBladSystemu(request.getName(), request.getLastName(), request.getWeekNumber());
        }

        return collect.get();
    }

    private Lecturer createLecturer(Lecturer actual, Lecturer updateLecturer) {

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
