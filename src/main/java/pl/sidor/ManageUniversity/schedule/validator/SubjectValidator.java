package pl.sidor.ManageUniversity.schedule.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.sidor.ManageUniversity.schedule.model.Subject;
import pl.sidor.ManageUniversity.schedule.repository.SubjectRepo;

import java.util.function.Predicate;

import static java.util.Optional.ofNullable;

@Component
public class SubjectValidator implements Predicate<Subject> {

    private SubjectRepo subjectRepo;

    @Autowired
    public SubjectValidator(SubjectRepo subjectRepo) {
        this.subjectRepo = subjectRepo;
    }

    @Override
    public boolean test(Subject subject) {

        return !ofNullable(subjectRepo.findByStartTimeAndEndTime(subject.getStartTime(), subject.getEndTime())).isPresent();

    }
}
