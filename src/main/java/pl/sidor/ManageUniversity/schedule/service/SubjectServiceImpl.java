package pl.sidor.ManageUniversity.schedule.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.sidor.ManageUniversity.exception.ExceptionFactory;
import pl.sidor.ManageUniversity.exception.UniversityException;
import pl.sidor.ManageUniversity.lecturer.model.Lecturer;
import pl.sidor.ManageUniversity.lecturer.repository.LecturerRepo;
import pl.sidor.ManageUniversity.schedule.model.Subject;
import pl.sidor.ManageUniversity.schedule.repository.SubjectRepo;
import pl.sidor.ManageUniversity.schedule.validator.SubjectValidator;
import java.util.Optional;

import static java.util.Optional.of;

@Service
@Transactional
public class SubjectServiceImpl implements SubjectService {

    private SubjectRepo subjectRepo;
    private SubjectValidator subjectValidator;
    private LecturerRepo lecturerRepo;

    @Autowired
    public SubjectServiceImpl(SubjectRepo subjectRepo, SubjectValidator subjectValidator,LecturerRepo lecturerRepo) {
        this.subjectRepo = subjectRepo;
        this.subjectValidator = subjectValidator;
        this.lecturerRepo=lecturerRepo;
    }

    @Override
    public Subject getById(Long id) throws Throwable {
        return subjectRepo.findById(id).orElseThrow(ExceptionFactory.incorrectSubjectID(String.valueOf(id)));
    }

    @Override
    public Subject save(Subject subject) throws Throwable {

       return of(subject).filter(subjectValidator)
                .map(subject1 -> subjectRepo.save(subject))
                .orElseThrow(ExceptionFactory.incorrectTime(subject.getEndTime().toString()));
    }

    @Override
    public void delete(Long id) throws Throwable {

       of(getById(id)).ifPresent(subject -> subjectRepo.deleteById(id));

    }

    @Override
    public Optional<Subject> findByLecturer(Long id, String name, String lastName) {

        Long id1 = lecturerRepo.findByNameAndLastName(name, lastName).get().getId();

        return subjectRepo.findByLecturer(new Lecturer());

    }

    @Override
    public Subject findByLecturer(Lecturer lecturer) {
        return subjectRepo.findByLecturer(lecturer).get();
    }
}
