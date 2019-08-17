package pl.sidor.ManageUniversity.schedule.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.sidor.ManageUniversity.exception.ExceptionFactory;
import pl.sidor.ManageUniversity.exception.UniversityException;
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

    @Autowired
    public SubjectServiceImpl(SubjectRepo subjectRepo, SubjectValidator subjectValidator) {
        this.subjectRepo = subjectRepo;
        this.subjectValidator = subjectValidator;
    }

    @Override
    public Subject getById(Long id) throws UniversityException {

        Optional<Subject> byId = subjectRepo.findById(id);

        if (!byId.isPresent()) {
            throw ExceptionFactory.incorrectSubjectID(String.valueOf(id));
        }
        return byId.get();

    }

    @Override
    public Subject save(Subject subject) throws Throwable {

       return of(subject).filter(subjectValidator)
                .map(subject1 -> subjectRepo.save(subject))
                .orElseThrow(ExceptionFactory.incorrectTime(subject.getEndTime().toString()));
    }
}
