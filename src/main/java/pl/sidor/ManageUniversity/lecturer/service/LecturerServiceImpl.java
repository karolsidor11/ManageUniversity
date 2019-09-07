package pl.sidor.ManageUniversity.lecturer.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sidor.ManageUniversity.exception.ExceptionFactory;
import pl.sidor.ManageUniversity.exception.UniversityException;
import pl.sidor.ManageUniversity.lecturer.model.Lecturer;
import pl.sidor.ManageUniversity.lecturer.repository.LecturerRepo;
import pl.sidor.ManageUniversity.lecturer.validation.CheckLecturer;

import java.util.Optional;

import static java.util.Optional.of;

@Service
@Slf4j
public class LecturerServiceImpl implements LecturerService {

    private LecturerRepo lecturerRepo;
    private CheckLecturer checkLecturer;

    @Autowired
    public LecturerServiceImpl(LecturerRepo lecturerRepo, CheckLecturer checkLecturer) {
        this.lecturerRepo = lecturerRepo;
        this.checkLecturer = checkLecturer;
    }

    @Override
    public Lecturer findById(long id) throws Throwable {

        return lecturerRepo.findById(id).orElseThrow(ExceptionFactory.incorrectLecturerID(String.valueOf(id)));
    }

    @Override
    public Lecturer create(Lecturer lecturer) throws Throwable {

        return of(lecturer)
                .filter(checkLecturer)
                .map(lecturer1 -> lecturerRepo.save(lecturer))
                .orElseThrow(ExceptionFactory.lecturerInDatabase(lecturer.getEmail()));
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
