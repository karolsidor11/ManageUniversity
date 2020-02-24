package pl.sidor.ManageUniversity.schedule.validator;

import lombok.AllArgsConstructor;
import pl.sidor.ManageUniversity.schedule.model.Subject;
import pl.sidor.ManageUniversity.schedule.repository.SubjectRepo;

import java.util.function.Predicate;


@AllArgsConstructor
public class SubjectValidator implements Predicate<Subject> {

    private final SubjectRepo subjectRepo;

    @Override
    public boolean test(final Subject subject) {
        return subjectRepo.findByStartTimeAndEndTime(subject.getStartTime(), subject.getEndTime()).isEmpty();
    }
}
