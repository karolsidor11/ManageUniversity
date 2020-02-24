package pl.sidor.ManageUniversity.schedule.service;

import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import pl.sidor.ManageUniversity.exception.ExceptionFactory;
import pl.sidor.ManageUniversity.lecturer.model.Lecturer;
import pl.sidor.ManageUniversity.lecturer.repository.LecturerRepo;
import pl.sidor.ManageUniversity.schedule.model.Subject;
import pl.sidor.ManageUniversity.schedule.repository.SubjectRepo;
import pl.sidor.ManageUniversity.schedule.validator.SubjectValidator;

import java.util.Optional;

import static java.util.Optional.of;
import static java.util.Optional.ofNullable;

@Transactional
@AllArgsConstructor
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepo subjectRepo;
    private final SubjectValidator subjectValidator;
    private final LecturerRepo lecturerRepo;

    @Override
    public Subject getById(final Long id) throws Throwable {
        return subjectRepo.findById(id).orElseThrow(ExceptionFactory.incorrectSubjectID(String.valueOf(id)));
    }

    @Override
    public Subject save(final Subject subject) throws Throwable {
        if (!subjectValidator.test(subject)) {
            throw ExceptionFactory.incorrectTime(subject.getEndTime().toString());
        }

       return of(subjectRepo.save(subject)).orElseThrow(ExceptionFactory.objectIsEmpty("!!!"));
    }

    @Override
    public void delete(final Long id) throws Throwable {
        ofNullable(getById(id)).ifPresent(subject -> subjectRepo.deleteById(id));
    }

    @Override
    public Optional<Subject> findByLecturer(final Long id, final String name, final String lastName) throws Throwable {

        Optional<Lecturer> lecturer = ofNullable(lecturerRepo.findByNameAndLastName(name, lastName))
                .orElseThrow(ExceptionFactory.objectIsEmpty("!!!"));

        Long lecturerId = lecturer.get().getId();
        Optional<Lecturer> lecturerById = of(lecturerRepo.findById(lecturerId)).orElseThrow();

        return ofNullable(subjectRepo.findByLecturer(lecturerById.get())).orElseThrow();
    }

    @Override
    public Subject findByLecturer(final Lecturer lecturer) throws Throwable {
        return ofNullable(subjectRepo.findByLecturer(lecturer)).orElseThrow(ExceptionFactory.objectIsEmpty("!!!")).get();
    }
}
